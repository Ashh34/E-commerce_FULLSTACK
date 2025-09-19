package com.ecommerce.ProjectEcommerce.controller;

import com.ecommerce.ProjectEcommerce.dto.ResponseDto;
import com.ecommerce.ProjectEcommerce.dto.user.SignInDto;
import com.ecommerce.ProjectEcommerce.dto.user.SignInResponseDto;
import com.ecommerce.ProjectEcommerce.dto.user.SignupDto;
import com.ecommerce.ProjectEcommerce.exceptions.AuthenticationFailException;
import com.ecommerce.ProjectEcommerce.exceptions.CustomException;
import com.ecommerce.ProjectEcommerce.model.User;
import com.ecommerce.ProjectEcommerce.repository.UserRepository;
import com.ecommerce.ProjectEcommerce.service.AuthenticationService;
import com.ecommerce.ProjectEcommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        return userRepository.findAll();
    }

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    @PostMapping("/signIn")
    public SignInResponseDto signin(@RequestBody SignInDto signInDto) throws CustomException {
        return userService.signIn(signInDto);
    }

    // Example for updating user (commented out)
    /*
    @PostMapping("/updateUser")
    public ResponseDto updateUser(@RequestParam("token") String token, @RequestBody UserUpdateDto userUpdateDto) {
        authenticationService.authenticate(token);
        return userService.updateUser(token, userUpdateDto);
    }
    */

    // Example for creating user (commented out)
    /*
    @PostMapping("/createUser")
    public ResponseDto createUser(@RequestParam("token") String token, @RequestBody UserCreateDto userCreateDto)
            throws CustomException, AuthenticationFailException {
        authenticationService.authenticate(token);
        return userService.createUser(token, userCreateDto);
    }
    */
}
