package ru.mycreation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mycreation.dto.UserDto;
import ru.mycreation.entities.Role;
import ru.mycreation.entities.User;
import ru.mycreation.repository.RoleRepository;
import ru.mycreation.repository.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User findByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    public User findByEmail(String email) { return userRepository.findOneByEmail(email);}

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findOneByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Incorrect login or password");
        }
        if (user.isEnabled() == true){
            throw new UsernameNotFoundException("User is not verified");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public List<User> findAll() {return (List<User>) userRepository.findAll();}

    @Transactional
    public void delete(Long id) { userRepository.deleteById(id);
    }

    @Transactional
    public void save(User user) { userRepository.save(user);
    }

    @Transactional
    public void registrationUser(UserDto userDto) {
        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setToken(userDto.getToken());
        user.setEnabled(true);
        user.setRoles(Collections.singletonList(roleRepository.findOneByName("ROLE_USER")));
        userRepository.save(user);
    }

    public User findOneByToken(String token) { return userRepository.findOneByToken(token);
    }
}
