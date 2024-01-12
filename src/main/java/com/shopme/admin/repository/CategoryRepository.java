package com.shopme.admin.repository;

import com.shopme.admin.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {
    @Query("select c from Category as c where c.parent is null")
    public List<Category> findRootCategories(Sort sort);

    public Category findByName(String name);
    public Category findByAlias(String alias);

    @Query("update Category c set c.enabled = ?2 where c.id = ?1")
    @Modifying
    @Transactional
    public void updateCategoryEnabledStatus(Integer id, boolean status);

}
