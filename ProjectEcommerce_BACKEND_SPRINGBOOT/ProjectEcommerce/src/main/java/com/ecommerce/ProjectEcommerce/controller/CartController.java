package com.ecommerce.ProjectEcommerce.controller;

import com.ecommerce.ProjectEcommerce.common.ApiResponse;
import com.ecommerce.ProjectEcommerce.dto.cart.AddToCartDto;
import com.ecommerce.ProjectEcommerce.dto.cart.CartDto;
import com.ecommerce.ProjectEcommerce.exceptions.AuthenticationFailException;
import com.ecommerce.ProjectEcommerce.exceptions.CartItemNotExistException;
import com.ecommerce.ProjectEcommerce.exceptions.ProductNotExistException;
import com.ecommerce.ProjectEcommerce.model.Product;
import com.ecommerce.ProjectEcommerce.model.User;
import com.ecommerce.ProjectEcommerce.service.AuthenticationService;
import com.ecommerce.ProjectEcommerce.service.CartService;
import com.ecommerce.ProjectEcommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticationService authenticationService;

    /** Add to cart */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody @Valid AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token)
            throws AuthenticationFailException, ProductNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Product product = productService.getProductById(addToCartDto.getProductId());
        cartService.addToCart(addToCartDto, product, user);
        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    /** List cart items */
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token)
            throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    /** Update cart item quantity */
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
                                                      @RequestParam("token") String token)
            throws AuthenticationFailException, ProductNotExistException, CartItemNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Product product = productService.getProductById(cartDto.getProductId());
        cartService.updateCartItem(cartDto, user, product);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }

    /** Delete cart item */
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteCartItem(@RequestParam("cartItemId") int itemId,
                                                      @RequestParam("token") String token)
            throws AuthenticationFailException, CartItemNotExistException {
        authenticationService.authenticate(token);
        int userId = authenticationService.getUser(token).getId();
        cartService.deleteCartItem(itemId, userId);
        return new ResponseEntity<>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }
}
