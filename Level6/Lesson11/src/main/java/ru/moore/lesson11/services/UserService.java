package ru.moore.lesson11.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.moore.lesson11.model.Role;
import ru.moore.lesson11.model.User;
import ru.moore.lesson11.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public Optional<User> findUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = findUserByName(s).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found ", s)));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), mapRoles(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRoles(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    public void saveUser(User user) {
        userRepository.saveAndFlush(user);
    }

    public long totalValues() {
        return userRepository.count();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Not found user by id - " + id));
    }

}
