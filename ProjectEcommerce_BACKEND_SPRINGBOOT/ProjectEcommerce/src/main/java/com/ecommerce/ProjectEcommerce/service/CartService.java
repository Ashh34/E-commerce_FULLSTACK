package com.ecommerce.ProjectEcommerce.service;

import com.ecommerce.ProjectEcommerce.dto.cart.AddToCartDto;
import com.ecommerce.ProjectEcommerce.dto.cart.CartDto;
import com.ecommerce.ProjectEcommerce.dto.cart.CartItemDto;
import com.ecommerce.ProjectEcommerce.exceptions.CartItemNotExistException;
import com.ecommerce.ProjectEcommerce.model.Cart;
import com.ecommerce.ProjectEcommerce.model.Product;
import com.ecommerce.ProjectEcommerce.model.User;
import com.ecommerce.ProjectEcommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    /** Add to cart with quantity merge */
    public void addToCart(AddToCartDto addToCartDto, Product product, User user) {
        Cart existingCart = cartRepository.findByUserAndProduct(user, product);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + addToCartDto.getQuantity());
            existingCart.setCreatedDate(new Date());
            cartRepository.save(existingCart);
        } else {
            Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
            cart.setCreatedDate(new Date());
            cartRepository.save(cart);
        }
    }

    /** List cart items */
    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);
        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;

        for (Cart cart : cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            cartItems.add(cartItemDto);
            totalCost += cart.getProduct().getPrice() * cart.getQuantity();
        }
        return new CartDto(cartItems, totalCost);
    }

    /** Update cart item quantity */
    public void updateCartItem(AddToCartDto cartDto, User user, Product product) throws CartItemNotExistException {
        Cart cart = cartRepository.findById(cartDto.getId())
                .orElseThrow(() -> new CartItemNotExistException("Cart item not found"));

        if (!cart.getUser().getId().equals(user.getId())) {
            throw new CartItemNotExistException("Cart item does not belong to user");
        }

        cart.setQuantity(cartDto.getQuantity());
        cart.setCreatedDate(new Date());
        cartRepository.save(cart);
    }

    /** Delete single cart item */
    public void deleteCartItem(int id, int userId) throws CartItemNotExistException {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartItemNotExistException("Cart id is invalid: " + id));

        if (cart.getUser().getId() != userId) {
            throw new CartItemNotExistException("Cart item does not belong to user");
        }

        cartRepository.delete(cart);
    }

    /** Delete all cart items of a user */
    public void deleteCartItems(int userId) {
        User user = new User();
        user.setId(userId);
        cartRepository.deleteByUser(user);
    }
}
