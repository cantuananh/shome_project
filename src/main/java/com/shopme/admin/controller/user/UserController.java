package com.shopme.admin.controller.user;

import com.shopme.admin.model.Role;
import com.shopme.admin.model.User;
import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/shopme/admin/users")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("listUsers", userService.listAll());

        return "/admin/user/users";
    }

    @RequestMapping("/new")
    public String newUser(Model model) {
        List<Role> listRoles = userService.listRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);

        return "/admin/user/user_form";
    }

    @PostMapping("/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        System.out.println(user);

        userService.save(user);
        redirectAttributes.addFlashAttribute("message", "Create user successfully");


        return "redirect:/shopme/admin/users";
    }


}
