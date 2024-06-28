package mk.ukim.finki.wp.kol2022.g2.repository;

import mk.ukim.finki.wp.kol2022.g2.model.Course;
import mk.ukim.finki.wp.kol2022.g2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    //filter(Long courseId, Integer yearsOfStudying)
    Optional<Student> findByEmail(String Email);
    List<Student> findAllByCoursesContains(Course course);
    List<Student> findAllByEnrollmentDateBefore(LocalDate years);
    List<Student> findAllByCoursesContainsAndEnrollmentDateBefore(Course course, LocalDate years);
}
