package com.shopme.admin.controller.user;

import com.shopme.admin.controller.FileUploadUtil;
import com.shopme.admin.model.User;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/shopme/admin")
public class AccountController {
    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String viewDetails(@AuthenticationPrincipal ShopmeUserDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        User user = userService.getUserByEmail(email);
        model.addAttribute("user", user);

        return "/admin/user/account_form";
    }

    @PostMapping("/account/update")
    public String saveDetails(User user,
                              RedirectAttributes redirectAttributes,
                              @RequestParam("image") MultipartFile multipartFile,
                              @AuthenticationPrincipal ShopmeUserDetails loggedUser
    ) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            System.out.println(fileName);
            user.setPhotos(fileName);
            User savedUser = userService.updateAccountInformation(user);
            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty()) {
                user.setPhotos(null);
            }

            userService.updateAccountInformation(user);
        }

        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());
        redirectAttributes.addFlashAttribute("message", "Your account details has been updated successfully");

        return "redirect:/shopme/admin/account";
    }
}
