package com.shopme.admin.controller.user;

import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shopme/admin")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("listUsers", userService.listAll());

        return "/admin/users";
    }


}
