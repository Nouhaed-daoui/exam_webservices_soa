package com.example.studentservice;

import com.example.courseservice.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private WebClientAutoConfiguration webClient;

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
            // Build the URI for the Course microservice call
            URI uri = UriComponentsBuilder.fromUriString("http://course-service/course/student/{id}")
                    .buildAndExpand(id)
                    .toUri();

            // Configure the HTTP Exchange request
            RequestCallback requestCallback = restTemplate.httpEntityCallback(null, null, null);
            ResponseExtractor<ResponseEntity<List<Course>>> responseExtractor = restTemplate.responseEntityExtractor(List.class, Course.class);
            ResponseEntity<List<Course>> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, responseExtractor);

            // Retrieve the list of courses from the response
            List<Course> courses = responseEntity.getBody();

            return courses;
        }
        return null; // Add a return statement here if needed


    }
}
