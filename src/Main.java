public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        GraduateStudent test = new GraduateStudent("112", "billy", "SE", 2.8, "some stuff");

        //test 1
        manager.saveToFile(new Student("101", "Bob", "CS", 3.8));
        manager.saveToFile(new Student("102", "dod", "SE", 3.2));
        manager.saveToFile(new Student("103", "sos", "SE", 2.9));

        IO.println("Initially");
        manager.displayStudents();

        //test updating dod's GPA from 3.2 to 3.7
        manager.updateStudentGPA("102", 3.7);

        //test deleting sos.
        manager.deleteStudent("103");

        //print final list
        IO.println("Final output in File");
        manager.displayStudents();
        //calculator
        manager.generateReport();

        manager.fileProperty();

        //test 2
        Student s1 = new Student("101", "Bob", "CS", 3.8);
        Student s2 = new Student("102", "dod", "SE", 3.2);

        IO.println("running the test");
        //add to database
        manager.addStudentToDB(s1);
        manager.addStudentToDB(s2);

        //display database content
        manager.displayStudentsFromDB();

        //update database
        manager.updateStudentGPAInDB("102", 3.7);

        //delete database entry
        manager.deleteStudentFromDB("101");

        //display the final database
        manager.displayStudentsFromDB();
    }

}
