package com.shopme.admin.service;

import com.shopme.admin.model.Category;
import com.shopme.admin.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> listAll() {
        return (List<Category>) categoryRepository.findAll();
    }
}
