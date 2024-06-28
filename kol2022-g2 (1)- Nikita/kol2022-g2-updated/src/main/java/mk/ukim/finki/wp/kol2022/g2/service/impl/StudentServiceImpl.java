package mk.ukim.finki.wp.kol2022.g2.service.impl;

import mk.ukim.finki.wp.kol2022.g2.model.Course;
import mk.ukim.finki.wp.kol2022.g2.model.Student;
import mk.ukim.finki.wp.kol2022.g2.model.StudentType;
import mk.ukim.finki.wp.kol2022.g2.model.exceptions.InvalidStudentIdException;
import mk.ukim.finki.wp.kol2022.g2.repository.CourseRepository;
import mk.ukim.finki.wp.kol2022.g2.repository.StudentRepository;
import mk.ukim.finki.wp.kol2022.g2.service.CourseService;
import mk.ukim.finki.wp.kol2022.g2.service.StudentService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService, UserDetailsService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseService courseService;


    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository, PasswordEncoder passwordEncoder, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.passwordEncoder = passwordEncoder;
        this.courseService = courseService;
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
    }

    @Override
    public Student create(String name, String email, String password, StudentType type, List<Long> courseId, LocalDate enrollmentDate) {
        List<Course> courses = courseRepository.findAllById(courseId);
        return studentRepository.save(new Student(name,email,passwordEncoder.encode(password),type,courses,enrollmentDate));
    }

    @Override
    public Student update(Long id, String name, String email, String password, StudentType type, List<Long> coursesId, LocalDate enrollmentDate) {
        List<Course> courses = courseRepository.findAllById(coursesId);
        Student student = studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
        student.setName(name);
        student.setEmail(email);
        student.setPassword(passwordEncoder.encode(password));
        student.setType(type);
        student.setCourses(courses);
        student.setEnrollmentDate(enrollmentDate);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student delete(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(InvalidStudentIdException::new);
        studentRepository.delete(student);
        return student;
    }

    @Override
    public List<Student> filter(Long courseId, Integer yearsOfStudying) {
        if (courseId != null && yearsOfStudying != null){
            LocalDate date = LocalDate.now().minusYears(yearsOfStudying);
            Course course = courseService.findById(courseId);
           return studentRepository.findAllByCoursesContainsAndEnrollmentDateBefore(course,date);
        }
        else if(courseId == null && yearsOfStudying == null){
            return studentRepository.findAll();
        }

        else if(courseId != null){
            Course course = courseService.findById(courseId);
            return studentRepository.findAllByCoursesContains(course);
        }

        else{
            LocalDate date = LocalDate.now().minusYears(yearsOfStudying);
            return studentRepository.findAllByEnrollmentDateBefore(date);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student=this.studentRepository.findByEmail(username).orElseThrow();
        GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+student.getType());
        List<GrantedAuthority> authorities= Collections.singletonList(authority);
        return new User(
                student.getEmail(),
                student.getPassword(),
                authorities
        );
    }
}
