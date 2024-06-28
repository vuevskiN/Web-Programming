package mk.ukim.finki.wp.kol2022.g2.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
public class Student {

    public Student() {
    }

    public Student(String name, String email, String password, StudentType type, List<Course> courses, LocalDate enrollmentDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.courses = courses;
        this.enrollmentDate = enrollmentDate;
    }
@Id
@GeneratedValue
    private Long id;

    private LocalDate enrollmentDate;

    private String name;

    private String email;

    private String password;
@Enumerated(EnumType.STRING)
    private StudentType type;

@ManyToMany(fetch = FetchType.EAGER)
    private List<Course> courses;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(StudentType type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
