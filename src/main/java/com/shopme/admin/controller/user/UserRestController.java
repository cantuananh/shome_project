package com.shopme.admin.controller.user;

import com.shopme.admin.service.UserNotFoundException;
import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopme/admin/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping("/check-email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email) {
        System.out.println(id);
        return userService.isEmailUnique(id, email) ? "Ok" : "Duplicated";
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Integer id) {
        try {
            userService.delete(id);
            String message = "Delete successfully user ID: " + id;
            return ResponseEntity.ok(message);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
