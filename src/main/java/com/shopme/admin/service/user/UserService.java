package com.shopme.admin.service;

import com.shopme.admin.model.Role;
import com.shopme.admin.model.User;
import com.shopme.admin.repository.RoleRepository;
import com.shopme.admin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    public static final int USER_PER_PAGE = 4;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public List<User> listAll() {
        return (List<User>) userRepository.findAll(Sort.by("firstName").ascending());
    }

    public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE, sort);

        if (keyword != null) {
            return userRepository.findAll(keyword, pageable);
        }

        return userRepository.findAll(pageable);

    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public User save(User user) {
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

        return userRepository.save(user);
    }


    private void encodePassword(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userByEmail = userRepository.getUserByEmail(email);

        if (userByEmail == null) {
            return true;
        }

        if (id == null) {
            return false;
        }

        if (!userByEmail.getId().equals(id)) {
            return false;
        }

        return true;
    }

    public User updateAccountInformation(User userInform) {
        Optional<User> optionalUser = userRepository.findById(userInform.getId());

        User userInDB = optionalUser.get();

        if (!userInform.getPassword().isEmpty()) {
            userInDB.setPassword(userInform.getPassword());
            encodePassword(userInDB);
        }

        if (userInform.getPhotos() != null) {
            userInDB.setPhotos(userInform.getPhotos());
        }

        userInDB.setFirstName(userInform.getFirstName());
        userInDB.setLastName(userInform.getLastName());

        return userRepository.save(userInDB);
    }

    public User get(Integer id) throws com.shopme.admin.service.UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new com.shopme.admin.service.UserNotFoundException("Could not found user ID :" + id);
        }
    }

    public void delete(Integer id) throws com.shopme.admin.service.UserNotFoundException {
        Long countById = userRepository.countById(id);

        if (countById == null || countById == 0) {
            throw new com.shopme.admin.service.UserNotFoundException("Could not found user ID: " + id);
        }

        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled) {
        userRepository.updateUserEnabledStatus(id, enabled);
    }
}
