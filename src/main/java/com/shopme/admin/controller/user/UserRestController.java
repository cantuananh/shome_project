package com.shopme.admin.controller.user;

import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @RequestMapping("/shopme/admin/users/check-email")
    public String checkDuplicateEmail(@Param("email") String email) {
        return userService.isEmailUnique(email) ? "Ok" : "Duplicated";
    }
}
