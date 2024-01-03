package com.shopme.admin.controller.user;

import com.shopme.admin.model.Role;
import com.shopme.admin.model.User;
import com.shopme.admin.service.UserNotFoundException;
import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("pageTitle", "Create new user");

        return "/admin/user/user_form";
    }

    @PostMapping("/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        if (user.getId() == null) {
            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "Create user successfully");
        } else {
            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "Update user successfully");
        }

        return "redirect:/shopme/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String showFormUpdateUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) throws UserNotFoundException {
        try {
            List<Role> listRoles = userService.listRoles();

            User user = userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("listRoles", listRoles);
            model.addAttribute("pageTitle", "Edit user ID: " + user.getId());

            return "/admin/user/user_form";
        } catch (UserNotFoundException exception) {
            redirectAttributes.addFlashAttribute("message", exception.getMessage());
            return "redirect:/shopme/admin/users";
        }
    }

}
