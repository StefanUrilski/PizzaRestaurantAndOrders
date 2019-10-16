package com.pizzaapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.pizzaapp.domain.entities.User;
import com.pizzaapp.domain.entities.UserRole;
import com.pizzaapp.domain.models.service.UserServiceModel;
import com.pizzaapp.errors.IdNotFoundException;
import com.pizzaapp.repository.RoleRepository;
import com.pizzaapp.repository.UserRepository;

import static com.pizzaapp.common.Constants.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }


    private void seedRolesInDb() {
        if (roleRepository.count() == 0) {
            roleRepository.save(new UserRole("ROLE_ROOT"));
            roleRepository.save(new UserRole("ROLE_ADMIN"));
            roleRepository.save(new UserRole("ROLE_MODERATOR"));
            roleRepository.save(new UserRole("ROLE_COURIER"));
            roleRepository.save(new UserRole("ROLE_USER"));
        }
    }

    private void setUserRole(User userEntity) {
        if (userRepository.count() == 0) {
            userEntity.setAuthorities(new HashSet<>(roleRepository.findAll()));
        } else {
            UserRole roleUser = roleRepository.findByAuthority("ROLE_USER").orElse(null);

            if (roleUser == null) {
                throw new IllegalArgumentException(NON_EXISTENT_ROLE);
            }

            userEntity.setAuthorities(new HashSet<>());
            userEntity.getAuthorities().add(roleUser);
        }
    }

    private void changeUserRole(User userEntity, String role) {
        userEntity.getAuthorities().clear();

        switch (role) {
            case "admin":
                userEntity.getAuthorities().add(roleRepository.findByAuthority("ROLE_ADMIN").orElse(null));
            case "moderator":
                userEntity.getAuthorities().add(roleRepository.findByAuthority("ROLE_MODERATOR").orElse(null));
            case "courier":
                userEntity.getAuthorities().add(roleRepository.findByAuthority("ROLE_COURIER").orElse(null));
            case "user":
                userEntity.getAuthorities().add(roleRepository.findByAuthority("ROLE_USER").orElse(null));
                break;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(email).orElse(null);

        if (userDetails == null) {
            throw new UsernameNotFoundException(WRONG_NON_EXISTENT_EMAIL);
        }

        return userDetails;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        seedRolesInDb();

        User userEntity = modelMapper.map(userServiceModel, User.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setUsername(userServiceModel.getEmail());

        setUserRole(userEntity);

        userRepository.save(userEntity);
        return true;
    }

    @Override
    public UserServiceModel extractUserByEmail(String email) {
        User userEntity = userRepository.findByUsername(email).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException(WRONG_NON_EXISTENT_EMAIL);
        }

        UserServiceModel userServiceModel = modelMapper.map(userEntity, UserServiceModel.class);
        userServiceModel.setEmail(userEntity.getUsername());

        return userServiceModel;
    }

    @Override
    public UserServiceModel extractUserById(String id) {
        User userEntity = userRepository.findById(id).orElse(null);

        if (userEntity == null) {
            throw new IdNotFoundException(WRONG_NON_EXISTENT_ID);
        }

        UserServiceModel userServiceModel = modelMapper.map(userEntity, UserServiceModel.class);
        userServiceModel.setEmail(userEntity.getUsername());

        return userServiceModel;
    }

    @Override
    public boolean editUser(UserServiceModel userServiceModel) {
        User userEntity = userRepository.findByUsername(userServiceModel.getEmail()).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException(WRONG_NON_EXISTENT_EMAIL);
        }

        userEntity = modelMapper.map(userServiceModel, User.class);
        userEntity.setId(userServiceModel.getId());
        userEntity.setUsername(userServiceModel.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);

        return true;
    }

    @Override
    public List<UserServiceModel> extractAllUsersOrderedAlphabetically() {
        List<User> userEntities = userRepository.findAllOrderedAlphabetically();

        return userEntities.stream()
                .map(u -> {
                    UserServiceModel userServiceModel = modelMapper.map(u, UserServiceModel.class);
                    userServiceModel.setEmail(u.getUsername());

                    return userServiceModel;
                }).collect(Collectors.toList());
    }

    @Override
    public boolean editUserRole(String email, String role) {
        User userEntity = userRepository.findByUsername(email).orElse(null);

        if (userEntity == null) {
            throw new UsernameNotFoundException(WRONG_NON_EXISTENT_EMAIL);
        }

        changeUserRole(userEntity, role);

        userRepository.save(userEntity);

        return true;
    }

    @Override
    public boolean addAddress(UserServiceModel userServiceModel) {
        User userEntity = modelMapper.map(userServiceModel, User.class);

        userRepository.save(userEntity);

        return true;
    }
}
