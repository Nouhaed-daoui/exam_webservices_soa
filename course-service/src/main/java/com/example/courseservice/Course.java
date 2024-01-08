import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ElementCollection
    @CollectionTable(name = "students_courses", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "student_id")
    private List<Long> students;

    // Getters and setters
}
