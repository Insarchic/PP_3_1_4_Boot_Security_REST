package ru.kata.spring.boot_security.demo.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public DataInitializer(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;

        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                    Role role = new Role("ROLE_ADMIN");
                    return roleRepository.save(role);
                });

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role role = new Role("ROLE_USER");
                    return roleRepository.save(role);
                });

        User adminUser = userRepository.findByUsername("admin")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("admin");
                    user.setAge(39);
                    user.setPassword(passwordEncoder.encode("admin"));
                    user.setRoles(Set.of(adminRole));
                    return userRepository.save(user);
                });

        User regularUser = userRepository.findByUsername("user")
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername("user");
                    user.setAge(25);
                    user.setPassword(passwordEncoder.encode("user"));
                    user.getRoles().add(userRole);
                    return userRepository.save(user);
                });
    }
}