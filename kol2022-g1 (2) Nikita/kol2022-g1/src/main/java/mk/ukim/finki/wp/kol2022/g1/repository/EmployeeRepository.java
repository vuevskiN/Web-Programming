package mk.ukim.finki.wp.kol2022.g1.repository;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEmail(String username);

    List<Employee> findBySkillsContains(Skill skill);


    List<Employee> findByEmploymentDateBefore(LocalDate years);

    List<Employee> findBySkillsContainsAndEmploymentDateBefore(Skill skill, LocalDate years);
}
