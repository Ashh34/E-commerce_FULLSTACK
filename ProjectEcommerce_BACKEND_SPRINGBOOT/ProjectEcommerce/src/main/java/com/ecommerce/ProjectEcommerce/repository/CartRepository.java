package com.ecommerce.ProjectEcommerce.repository;

import com.ecommerce.ProjectEcommerce.model.Cart;
import com.ecommerce.ProjectEcommerce.model.Product;
import com.ecommerce.ProjectEcommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

    Cart findByUserAndProduct(User user, Product product);

    void deleteByUser(User user);
}
