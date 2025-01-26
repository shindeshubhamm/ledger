package com.zigartiq.ledger.controller;

import com.zigartiq.ledger.payload.StandardResponse;
import com.zigartiq.ledger.payload.response.UserResponse;
import com.zigartiq.ledger.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<StandardResponse<UserResponse>> getCurrentUser() {

        UserResponse userResponse = userService.getCurrentUser();

        return new ResponseEntity<>(new StandardResponse<>("success", "", userResponse), HttpStatus.OK);
    }
}
