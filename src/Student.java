public class Student {
    private String StudentID;
    private String Name;
    private String Department;
    private double GPA;
    //constructor
    public Student(String StudentID, String Name, String Department, Double GPA){
        this.StudentID = StudentID;
        this.Name = Name;
        this.Department = Department;
        setGPA(GPA);
    }
    // getter
    public String getStudentID() {return StudentID;}
    public String getName() {return Name;}
    public String getDepartment() {return Department;}
    public double getGPA() {return GPA;}
    //setter
    public void setStudentID(String studentID) {StudentID = studentID;}
    public void setName(String name) {Name = name;}
    public void setDepartment(String department) {Department = department;}
    public void setGPA(double GPA) {
        if (this.GPA > 4){
            this.GPA = 4;
        }else if(this.GPA < 0){
            this.GPA = 0;
        }else{
            this.GPA = GPA;
        }
    }
    @Override
    public String toString(){
        return "student[" + "ID=" + StudentID + ", Name=" + Name + ", Dept=" + Department + ", GPA=" + GPA + "]";
    }
}