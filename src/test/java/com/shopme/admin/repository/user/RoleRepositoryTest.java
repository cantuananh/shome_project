package com.shopme.admin.repository.user;

import static org.assertj.core.api.Assertions.assertThat;
import com.shopme.admin.model.Role;
import com.shopme.admin.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "Manager everything");

        Role saveRole = roleRepository.save(roleAdmin);
        assertThat(saveRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRole() {
        Role roleSalesperson = new Role("Salesperson", "Manager product price, " +
                "customer, shipping, order and sale report.");

        Role roleEditor = new Role("Editor", "Manager categories, branch" +
                ", product, articles and menu");

        Role roleShipper = new Role("Shipper", "View product, view order" +
                " and update order status");

        Role roleAssistant = new Role("Assistant", "Manager question and review");

        roleRepository.saveAll(List.of(roleSalesperson, roleEditor, roleShipper, roleAssistant));
    }
}
