public class GraduateStudent extends Student {
    private String Topic;

    // Constructor super()
    public GraduateStudent(String StudentID, String Name, String Department, double GPA, String Topic) {
        super(StudentID, Name, Department, GPA);
        this.Topic = Topic;
    }

    public String getTopic() { return Topic; }
    public void setTopic(String Topic) { this.Topic = Topic; }
}
