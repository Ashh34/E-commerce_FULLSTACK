package com.ecommerce.ProjectEcommerce.repository;

import com.ecommerce.ProjectEcommerce.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

    List<WishList> findAllByUserIdOrderByCreatedDateDesc(Integer userId);

    // NEW: delete wishlist item by user + product
    void deleteByUserIdAndProductId(Integer userId, Integer productId);
}
