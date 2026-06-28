public class GraduateStudent extends Student {
    private String Topic;

    // Constructor super()
    public GraduateStudent(String studentID, String name, String department, double gpa, String Topic) {
        super(studentID, name, department, gpa);
        this.Topic = Topic;
    }

    public String getTopic() { return Topic; }
    public void setTopic(String thesisTopic) { this.Topic = thesisTopic; }
}
