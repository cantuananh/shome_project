package com.shopme.admin.repository;

import com.shopme.admin.model.Category;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    @Query("select c from Category as c where c.parent is null")
    public List<Category> findRootCategories();

    public Category findByName(String name);
    public Category findByAlias(String alias);

}
