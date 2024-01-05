package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.shopme.admin.model.Role;
import com.shopme.admin.model.User;
import com.shopme.admin.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 2);
        User userCan = new User("anh.ct@codegym.vn", "nam 1997", "Can", "Tuan Anh");
        userCan.addRole(roleAdmin);

        User saveUser = userRepository.save(userCan);
        assertThat(saveUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRole() {
        User userLinh = new User("phuonglinhpham@gmail.com", "nam 1997", "Pham", "Phuong Linh");
        Role roleEditor = new Role(4);
        Role roleAssistant = new Role(6);

        userLinh.addRole(roleEditor);
        userLinh.addRole(roleAssistant);
        User saveUser = userRepository.save(userLinh);

        assertThat(saveUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUser() {
        Iterable<User> listsUser = userRepository.findAll();
        listsUser.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User userFound = userRepository.findById(1).get();
        System.out.println("User found: " + userFound);
        assertThat(userFound).isNotNull();
    }

    @Test
    public void testUpdateUserDetail() {
        User userEdit = userRepository.findById(1).get();

        userEdit.setEnabled(true);
        userEdit.setEmail("tuananhthemen391997@gmail.com");

        userRepository.save(userEdit);
    }

    @Test
    public void testUpdateUserRole() {
        User userLinh = userRepository.findById(2).get();

        Role roleAdmin = new Role(2);
        Role roleSalesperson = new Role(3);

//        userLinh.getRoles().remove(roleEditor);
        userLinh.removeRole(roleAdmin);
        userLinh.addRole(roleSalesperson);

        userRepository.save(userLinh);
    }

    @Test
    public void testDeleteUser() {
        User userDelete = userRepository.findById(3).get();

        userRepository.deleteById(userDelete.getId());
    }

    @Test
    public void testGetUserByEmail() {
        String email = "tuananhthemen391997@gmail.com";
        User user = userRepository.getUserByEmail(email);
        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;

        Long countById = userRepository.countById(id);
        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisabledUser() {
        Integer id = 1;

        userRepository.updateUserEnabledStatus(id, false);
    }

    @Test
    public void testListFirstPage() {
        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);
        List<User> listUsers = page.getContent();
        listUsers.forEach(user -> System.out.println(user));
        assertThat(listUsers.size()).isEqualTo(pageSize);
    }
}
