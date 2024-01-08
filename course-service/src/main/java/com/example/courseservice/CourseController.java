import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }


    @GetMapping("/student/{id}")
    public List<Course> getCoursesForStudent(@PathVariable Long id) {
        // Logic to retrieve courses for a specific student
        // You may use the CourseRepository or any other service

        // For demonstration purposes, let's assume you have a method in CourseRepository
        // to retrieve courses for a student by their ID
        return courseRepository.findCoursesByStudentId(id);
    }

    // Other CRUD operations
}
