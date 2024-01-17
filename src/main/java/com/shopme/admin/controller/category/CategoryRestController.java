package com.shopme.admin.controller.category;

import com.shopme.admin.controller.FileUploadUtil;
import com.shopme.admin.service.UserNotFoundException;
import com.shopme.admin.service.category.CategoryNotFoundException;
import com.shopme.admin.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopme/admin/categories")
public class CategoryRestController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/check_unique")
    public String checkUnique(@Param("id") Integer id,
                              @Param("name") String name,
                              @Param("alias") String alias) {


        return categoryService.checkUnique(id, name, alias);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") Integer id) {
        try {
            categoryService.delete(id);
            String categoryDir = "category-images/" + id;
            FileUploadUtil.removeDir(categoryDir);

            return ResponseEntity.ok("The category ID " + id + " has been deleted successfully");
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
