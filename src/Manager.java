import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Manager {
    private String textFilename = "students.txt";
    public void saveToFile(Student student) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(textFilename, true))) {
            writer.println(student.getStudentID());
            writer.println(student.getName());
            writer.println(student.getDepartment());
            writer.println(student.getGPA());

            IO.println("Saved " + student.getName() + " to the file");

        } catch (IOException e) {
            IO.println("Could not write to the file.");
        }
    }

    public Manager(){
        File file = new File(textFilename);
        try {
            if (!file.exists()){
                file.createNewFile();
                IO.println("created file: " + textFilename);
            }
        }catch (IOException e){
            IO.println("erroe with file.");
        }
    }

    private String url = "jdbc:mysql://localhost:3306/student_db";
    private String dbUser = "root";
    private String dbPassword = "";

    //loading drivers & connections
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, dbUser, dbPassword);
    }

    public void addStudentToDB(Student student) {
        String sql = "INSERT INTO Student (studentID, name, department, gpa) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, student.getStudentID());
            pstmt.setString(2, student.getName());
            pstmt.setString(3, student.getDepartment());
            pstmt.setDouble(4, student.getGPA());

            pstmt.executeUpdate();
            IO.println("Saved " + student.getName() + " to database");

        } catch (Exception e) {
            IO.println("Database error during insert: " + e.getMessage());
        }
    }

    public void displayStudentsFromDB() {
        String sql = "SELECT * FROM Student";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            IO.println("\nRecords in database");
            while (rs.next()) {
                String id = rs.getString("studentID");
                String name = rs.getString("name");
                String dept = rs.getString("department");
                double gpa = rs.getDouble("gpa");

                IO.println("Student ID: " + id);
                IO.println("Name: " + name);
                IO.println("Department: " + dept);
                IO.println("GPA: " + gpa);
            }
        } catch (Exception e) {
            IO.println("Database error during display: " + e.getMessage());
        }
    }

    public void updateStudentGPAInDB(String targetID, double newGpa) {
        String sql = "UPDATE Student SET gpa = ? WHERE studentID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newGpa);
            pstmt.setString(2, targetID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                IO.println("Updated student " + targetID + " GPA successfully");
            } else {
                IO.println("Student ID not found in database");
            }
        } catch (Exception e) {
            IO.println("Database error during update: " + e.getMessage());
        }
    }

    public void deleteStudentFromDB(String targetID) {
        String sql = "DELETE FROM Student WHERE studentID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, targetID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                IO.println("Deleted student " + targetID + " from database");
            } else {
                IO.println("Student ID not found in database");
            }
        } catch (Exception e) {
            IO.println("Database error during delete: " + e.getMessage());
        }
    }

    public void displayStudents() {
        File file = new File(textFilename);

        if (!file.exists()) {
            IO.println("No records found.");
            return;
        }
        try (Scanner fileScanner = new Scanner(file)) {

            while (fileScanner.hasNext()) {
                String id = fileScanner.nextLine();
                String name = fileScanner.nextLine();
                String dept = fileScanner.nextLine();
                double gpa = fileScanner.nextDouble();

                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }
                IO.println("Student ID: " + id);
                IO.println("Name: " + name);
                IO.println("Department: " + dept);
                IO.println("GPA: " + gpa);
            }
        } catch (FileNotFoundException e) {
            IO.println("File not found");
        }
    }

    public void searchStudentByID(String searchID) {
        File file = new File(textFilename);

        if (!file.exists()) {
            IO.println("No records found.");
            return;
        }

        boolean found = false;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNext()) {
                String id = fileScanner.nextLine();
                String name = fileScanner.nextLine();
                String dept = fileScanner.nextLine();
                double gpa = fileScanner.nextDouble();

                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }

                if (id.equals(searchID)) {
                    IO.println("Student Found");
                    IO.println("ID: " + id);
                    IO.println("Name: " + name);
                    IO.println("Department: " + dept);
                    IO.println("GPA: " + gpa);

                    found = true;
                    break;
                }
            }

            if (!found) {
                IO.println("Student with ID " + searchID + " was not found");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public void updateStudentGPA(String targetID, double newGpa) {
        File originalFile = new File(textFilename);
        File tempFile = new File("temp.txt");

        if (!originalFile.exists()) {
            IO.println("No records to update");
            return;
        }

        boolean updated = false;

        try (Scanner fileScanner = new Scanner(originalFile);
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            while (fileScanner.hasNext()) {
                String id = fileScanner.nextLine();
                String name = fileScanner.nextLine();
                String dept = fileScanner.nextLine();
                double gpa = fileScanner.nextDouble();

                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }

                if (id.equals(targetID)) {
                    writer.println(id);
                    writer.println(name);
                    writer.println(dept);
                    writer.println(newGpa);
                    updated = true;
                } else {
                    writer.println(id);
                    writer.println(name);
                    writer.println(dept);
                    writer.println(gpa);
                }
            }
        } catch (IOException e) {
            IO.println("An error occurred during updating.");
        }

        if (updated) {
            originalFile.delete();
            tempFile.renameTo(originalFile);
            IO.println("Student ID " + targetID + " GPA updated successfully");
        } else {
            tempFile.delete(); // If we didn't find the student, throw away the temp file
            IO.println("Student with ID " + targetID + " not found");
        }
    }
    public void deleteStudent(String targetID) {
        File originalFile = new File(textFilename);
        File tempFile = new File("temp.txt");

        if (!originalFile.exists()) {
            IO.println("No file found.");
            return;
        }

        boolean deleted = false;
        Scanner fileScanner = null;
        PrintWriter writer = null;

        try {
            fileScanner = new Scanner(originalFile);
            FileWriter fw = new FileWriter(tempFile);
            writer = new PrintWriter(fw);

            while (fileScanner.hasNext()) {
                String id = fileScanner.nextLine();
                String name = fileScanner.nextLine();
                String dept = fileScanner.nextLine();
                double gpa = fileScanner.nextDouble();

                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }

                if (id.equals(targetID)) {
                    deleted = true;
                } else {
                    writer.println(id);
                    writer.println(name);
                    writer.println(dept);
                    writer.println(gpa);
                }
            }
        } catch (IOException e) {
            IO.println("Error happened during delete");
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
            if (writer != null) {
                writer.close();
            }
        }

        if (deleted) {
            originalFile.delete();
            tempFile.renameTo(originalFile);
            IO.println("Deleted successfully");
        } else {
            tempFile.delete();
            IO.println("Not found.");
        }
    }
    public void generateReport() {
        File file = new File(textFilename);
        if (!file.exists()) {
            System.out.println("No records found");
            return;
        }

        int totalStudents = 0;
        double highestGpa = -1.0;
        double lowestGpa = 5.0;
        double gpaSum = 0.0;

        Scanner fileScanner = null;

        try {
            fileScanner = new Scanner(file);

            while (fileScanner.hasNext()) {
                String id = fileScanner.nextLine();
                String name = fileScanner.nextLine();
                String dept = fileScanner.nextLine();
                double gpa = fileScanner.nextDouble();

                if (fileScanner.hasNextLine()) {
                    fileScanner.nextLine();
                }

                totalStudents++;
                gpaSum += gpa;

                if (gpa > highestGpa) {
                    highestGpa = gpa;
                }
                if (gpa < lowestGpa) {
                    lowestGpa = gpa;
                }
            }

            if (totalStudents > 0) {
                double averageGpa = gpaSum / totalStudents;

                IO.println("REPORT");
                IO.println("Total Students: " + totalStudents);
                IO.println("Highest GPA: " + highestGpa);
                IO.println("Lowest GPA: " + lowestGpa);
                IO.println("Average GPA: " + averageGpa);
            } else {
                IO.println("File is empty.");
            }
        } catch (IOException e) {
            IO.println("Error reading file for report.");
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }
    }
    public void fileProperty(){
        File file = new File(textFilename);

        if (file.exists()){
            IO.println("File name: " + file.getName());
            IO.println("File path: " + file.getAbsolutePath());
            IO.println("size: " + file.length());
            IO.println("last modified: " + file.lastModified());
        }else{
            IO.println("file doesnt exist.");
        }
    }
}
