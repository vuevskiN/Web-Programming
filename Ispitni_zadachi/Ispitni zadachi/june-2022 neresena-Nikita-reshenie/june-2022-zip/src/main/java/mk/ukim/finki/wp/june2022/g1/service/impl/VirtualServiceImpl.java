package mk.ukim.finki.wp.june2022.g1.service.impl;

import mk.ukim.finki.wp.june2022.g1.model.OSType;
import mk.ukim.finki.wp.june2022.g1.model.User;
import mk.ukim.finki.wp.june2022.g1.model.VirtualServer;
import mk.ukim.finki.wp.june2022.g1.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.wp.june2022.g1.model.exceptions.InvalidUsernameException;
import mk.ukim.finki.wp.june2022.g1.model.exceptions.InvalidVirtualMachineIdException;
import mk.ukim.finki.wp.june2022.g1.repository.UserRepository;
import mk.ukim.finki.wp.june2022.g1.repository.VirtualServerRepository;
import mk.ukim.finki.wp.june2022.g1.service.UserService;
import mk.ukim.finki.wp.june2022.g1.service.VirtualServerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class VirtualServiceImpl implements VirtualServerService {
    private final VirtualServerRepository virtualServerRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public VirtualServiceImpl(VirtualServerRepository virtualServerRepository, UserRepository userRepository, UserService userService) {
        this.virtualServerRepository = virtualServerRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public List<VirtualServer> listAll() {
        return virtualServerRepository.findAll();
    }

    @Override
    public VirtualServer findById(Long id) {
        return virtualServerRepository.findById(id).orElseThrow(InvalidVirtualMachineIdException::new);
    }

    @Override
    public VirtualServer create(String name, String ipAddress, OSType osType, List<Long> owners, LocalDate launchDate) {
        List<User> owners_objs = userRepository.findAllById(owners);
        return virtualServerRepository.save(new VirtualServer(name, ipAddress, osType, owners_objs, launchDate));

    }


    @Override
    public VirtualServer update(Long id, String name, String ipAddress, OSType osType, List<Long> owners) {
    VirtualServer virtualServer = virtualServerRepository.findById(id).orElseThrow(InvalidVirtualMachineIdException::new);
    List<User> users = userRepository.findAllById(owners);
        virtualServer.setInstanceName(name);
        virtualServer.setIpAddress(ipAddress);
        virtualServer.setOSType(osType);
        virtualServer.setOwners(users);
        return virtualServerRepository.save(virtualServer);
    }

    @Override
    public VirtualServer delete(Long id) {
        VirtualServer virtualServer = virtualServerRepository.findById(id).orElseThrow((InvalidVirtualMachineIdException::new));
        virtualServerRepository.delete(virtualServer);
        return virtualServer;
    }

    @Override
    public VirtualServer markTerminated(Long id) {

        VirtualServer virtualServer = virtualServerRepository.findById(id).orElseThrow((InvalidVirtualMachineIdException::new));
        virtualServer.setTerminated(true);
        return virtualServerRepository.save(virtualServer);
    }

    @Override
    public List<VirtualServer> filter(Long ownerId, Integer activeMoreThanDays) {
        if(ownerId == null && activeMoreThanDays == null){
            return virtualServerRepository.findAll();
        }
        if(ownerId == null){
            LocalDate before_date = LocalDate.now().minusDays(activeMoreThanDays);
            return virtualServerRepository.findAllByLaunchDateBefore(before_date);
        }
        User owner = userRepository.findById(ownerId).orElseThrow(InvalidUserIdException::new);
        if(activeMoreThanDays == null){
            return virtualServerRepository.findAllByOwnersContains(owner);
        }
        LocalDate before_date = LocalDate.now().minusDays(activeMoreThanDays);
        return virtualServerRepository.findAllByOwnersContainsAndLaunchDateBefore(owner, before_date);
    }
    }

