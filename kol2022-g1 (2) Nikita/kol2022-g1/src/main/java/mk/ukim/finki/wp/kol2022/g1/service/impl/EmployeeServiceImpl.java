package mk.ukim.finki.wp.kol2022.g1.service.impl;

import mk.ukim.finki.wp.kol2022.g1.model.Employee;
import mk.ukim.finki.wp.kol2022.g1.model.EmployeeType;
import mk.ukim.finki.wp.kol2022.g1.model.Skill;
import mk.ukim.finki.wp.kol2022.g1.model.exceptions.InvalidEmployeeIdException;
import mk.ukim.finki.wp.kol2022.g1.repository.EmployeeRepository;
import mk.ukim.finki.wp.kol2022.g1.repository.SkillRepository;
import mk.ukim.finki.wp.kol2022.g1.service.EmployeeService;
import mk.ukim.finki.wp.kol2022.g1.service.SkillService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {
    private final SkillRepository skillRepository;
    private final EmployeeRepository employeeRepository;
    private final SkillService skillService;
    private final PasswordEncoder passwordEncoder;


    public EmployeeServiceImpl(SkillRepository skillRepository, EmployeeRepository employeeRepository, SkillService skillService, PasswordEncoder passwordEncoder) {
        this.skillRepository = skillRepository;
        this.employeeRepository = employeeRepository;
        this.skillService = skillService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Employee> listAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
    }

    @Override
    public Employee create(String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        List<Skill> skills = skillRepository.findAllById(skillId);
        return employeeRepository.save(new Employee(name,email,passwordEncoder.encode(password),type,skills,employmentDate));
    }

    @Override
    public Employee update(Long id, String name, String email, String password, EmployeeType type, List<Long> skillId, LocalDate employmentDate) {
        List<Skill> skills = skillRepository.findAllById(skillId);
        Employee employee = employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
        employee.setName(name);
        employee.setEmail(email);
        employee.setPassword(passwordEncoder.encode(password));
        employee.setType(type);
        employee.setSkills(skills);
        employee.setEmploymentDate(employmentDate);
        employeeRepository.save(employee);
        return employee;
    }

    @Override
    public Employee delete(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(InvalidEmployeeIdException::new);
        employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public List<Employee> filter(Long skillId, Integer yearsOfService) {
        if(skillId==null && yearsOfService==null){
            return listAll();
        }else if(skillId!=null && yearsOfService!=null){
            Skill skill=this.skillService.findById(skillId);
            LocalDate years=LocalDate.now().minusYears(yearsOfService);
            return this.employeeRepository.findBySkillsContainsAndEmploymentDateBefore(skill,years);
        }else if(skillId!=null){
            Skill skill=this.skillService.findById(skillId);
            return this.employeeRepository.findBySkillsContains(skill);
        }else {
            LocalDate years=LocalDate.now().minusYears(yearsOfService);
            return this.employeeRepository.findByEmploymentDateBefore(years);
        }
    }

  //  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee=this.employeeRepository.findByEmail(username);
        GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_"+employee.getType());
        List<GrantedAuthority> authorities= Collections.singletonList(authority);
        return new User(
                employee.getEmail(),
                employee.getPassword(),
                authorities
        );
    }
}
