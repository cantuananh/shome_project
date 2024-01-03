package com.shopme.admin.service;

import com.shopme.admin.model.Role;
import com.shopme.admin.model.User;
import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public void save(User user) {
        if (user.getId() != null) {
            User existingUser = userRepository.findById(user.getId()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }

        userRepository.save(user);
    }


    private void encodePassword(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);

        // Nếu không có người dùng nào có cùng email, thì email là duy nhất
        if (userByEmail == null) {
            return true;
        }

        // Nếu đang tạo mới, và đã tồn tại người dùng với email này, thì email không duy nhất
        if (id == null) {
            return false;
        }

        // Nếu đang cập nhật và email đã được sử dụng bởi người dùng khác, thì email không duy nhất
        if (!userByEmail.getId().equals(id)) {
            return false;
        }

        // Trường hợp còn lại, tức là đang cập nhật và email không thay đổi,
        // email chưa được sử dụng, hoặc đang tạo mới và email chưa được sử dụng, thì email là duy nhất
        return true;
    }



    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not found user ID :" + id);
        }
    }
}
