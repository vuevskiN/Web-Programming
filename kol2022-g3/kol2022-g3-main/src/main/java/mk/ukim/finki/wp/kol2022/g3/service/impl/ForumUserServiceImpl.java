package mk.ukim.finki.wp.kol2022.g3.service.impl;

import mk.ukim.finki.wp.kol2022.g3.model.ForumUser;
import mk.ukim.finki.wp.kol2022.g3.model.ForumUserType;
import mk.ukim.finki.wp.kol2022.g3.model.Interest;
import mk.ukim.finki.wp.kol2022.g3.model.exceptions.InvalidForumUserIdException;
import mk.ukim.finki.wp.kol2022.g3.model.exceptions.InvalidInterestIdException;
import mk.ukim.finki.wp.kol2022.g3.repository.ForumUserRepository;
import mk.ukim.finki.wp.kol2022.g3.repository.InterestRepository;
import mk.ukim.finki.wp.kol2022.g3.service.ForumUserService;
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
public class ForumUserServiceImpl implements ForumUserService, UserDetailsService {
    private final ForumUserRepository forumUserRepository;
    private final InterestRepository interestRepository;
    private final PasswordEncoder passwordEncoder;

    public ForumUserServiceImpl(ForumUserRepository forumUserRepository, InterestRepository interestRepository, PasswordEncoder passwordEncoder) {
        this.forumUserRepository = forumUserRepository;
        this.interestRepository = interestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<ForumUser> listAll() {
        return forumUserRepository.findAll();
    }

    @Override
    public ForumUser findById(Long id) {
        return forumUserRepository.findById(id).orElseThrow(InvalidForumUserIdException::new);
    }

    @Override
    public ForumUser create(String name, String email, String password, ForumUserType type, List<Long> interestId, LocalDate birthday) {
        List<Interest> interests = interestRepository.findAllById(interestId);

        return forumUserRepository.save(new ForumUser(name,email,passwordEncoder.encode(password),type,interests,birthday));
    }

    @Override
    public ForumUser update(Long id, String name, String email, String password, ForumUserType type, List<Long> interestId, LocalDate birthday) {
        ForumUser forumUser = forumUserRepository.findById(id).orElseThrow(InvalidForumUserIdException::new);
        List<Interest> interests = interestRepository.findAllById(interestId);
        forumUser.setType(type);
        forumUser.setEmail(email);
        forumUser.setBirthday(birthday);
        forumUser.setName(name);
        forumUser.setInterests(interests);
        forumUser.setPassword(passwordEncoder.encode(password));
        forumUserRepository.save(forumUser);
        return forumUser;
    }

    @Override
    public ForumUser delete(Long id) {
        ForumUser forumUser = forumUserRepository.findById(id).orElseThrow(InvalidForumUserIdException::new);
        forumUserRepository.delete(forumUser);
        return forumUser;
    }

    @Override
    public List<ForumUser> filter(Long interestId, Integer age) {
        if(interestId != null && age != null){
            return forumUserRepository.findAllByInterestsContainsAndBirthdayBefore(this.interestRepository.findById(interestId).orElseThrow(InvalidInterestIdException::new), LocalDate.now().minusYears(age));
        }

        else if(interestId != null){
            return forumUserRepository.findAllByInterestsContains(this.interestRepository.findById(interestId).orElseThrow(InvalidInterestIdException::new));
        }

        else if(age != null){
            return forumUserRepository.findAllByBirthdayBefore(LocalDate.now().minusYears(age));
        }

        else{
            return forumUserRepository.findAll();
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ForumUser forumUser = forumUserRepository.findAllByEmail(username).orElseThrow();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_"+forumUser.getType());
        List<GrantedAuthority> authorities = Collections.singletonList(authority);
        return new User(
                forumUser.getEmail(),
                forumUser.getPassword(),
                authorities
        );
    }
}
