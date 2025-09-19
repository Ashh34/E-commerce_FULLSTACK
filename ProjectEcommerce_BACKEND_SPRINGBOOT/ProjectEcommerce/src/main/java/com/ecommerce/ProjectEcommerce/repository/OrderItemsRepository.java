package com.ecommerce.ProjectEcommerce.repository;

import com.ecommerce.ProjectEcommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem,Integer> {
}