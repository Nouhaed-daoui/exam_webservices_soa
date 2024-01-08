

import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @GetMapping("/{id}/courses")
    public List<Course> getCoursesForStudent(@PathVariable Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student != null) {
            // Construire l'URI pour l'appel au microservice Course
            URI uri = UriComponentsBuilder.fromUriString("http://course-service/course/student/{id}")
                    .buildAndExpand(id)
                    .toUri();

            // Configurer la requête HTTP Exchange
            RequestCallback requestCallback = restTemplate.httpEntityCallback(null, null, null);
            ResponseExtractor<ResponseEntity<List<Course>>> responseExtractor = restTemplate.responseEntityExtractor(List.class, Course.class);
            ResponseEntity<List<Course>> responseEntity = restTemplate.execute(uri, HttpMethod.GET, requestCallback, responseExtractor);

            // Récupérer la liste de cours depuis la réponse
            List<Course> courses = responseEntity.getBody();

            return courses;
        }
        retu
}
