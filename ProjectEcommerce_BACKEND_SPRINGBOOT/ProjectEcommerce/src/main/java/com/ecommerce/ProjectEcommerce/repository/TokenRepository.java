package com.ecommerce.ProjectEcommerce.repository;

import com.ecommerce.ProjectEcommerce.model.AuthenticationToken;
import com.ecommerce.ProjectEcommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}