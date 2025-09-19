package com.ecommerce.ProjectEcommerce.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.ecommerce.ProjectEcommerce.common.ApiResponse;
import com.ecommerce.ProjectEcommerce.dto.checkout.CheckoutItemDto;
import com.ecommerce.ProjectEcommerce.dto.checkout.StripeResponse;
import com.ecommerce.ProjectEcommerce.exceptions.AuthenticationFailException;
import com.ecommerce.ProjectEcommerce.exceptions.OrderNotFoundException;
import com.ecommerce.ProjectEcommerce.model.Order;
import com.ecommerce.ProjectEcommerce.model.User;
import com.ecommerce.ProjectEcommerce.service.AuthenticationService;
import com.ecommerce.ProjectEcommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthenticationService authenticationService;

    // stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<?> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) {
        try {
            if (checkoutItemDtoList == null || checkoutItemDtoList.isEmpty()) {
                return ResponseEntity.badRequest().body("Checkout items cannot be empty.");
            }
            Session session = orderService.createSession(checkoutItemDtoList);
            return new ResponseEntity<>(new StripeResponse(session.getId()), HttpStatus.OK);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create Stripe session.");
        }
    }

    // place order after checkout (optional if you later use webhooks)
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam("token") String token,
                                                  @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token)
            throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        List<Order> orderDtoList = orderService.listOrders(user);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    // get order by id
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token)
            throws AuthenticationFailException {
        authenticationService.authenticate(token);
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
