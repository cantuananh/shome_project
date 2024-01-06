package com.shopme.admin.repository;

import com.shopme.admin.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    @Query("SELECT u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    public Long countById(Integer id);

    @Query("SELECT u from User u where u.firstName like %?1% OR u.lastName LIKE %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);

    @Query("UPDATE User u set u.enabled = ?2 where u.id = ?1")
    @Modifying
    public void updateUserEnabledStatus(Integer id, boolean enabled);
}
