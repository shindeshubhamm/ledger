package com.zigartiq.ledger.service.impl;

import com.zigartiq.ledger.service.UserService;
import com.zigartiq.ledger.entity.User;
import com.zigartiq.ledger.payload.response.UserResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        String profileImageUrl = user.getProfileImageUrl() != null ? user.getProfileImageUrl()
                : "https://api.dicebear.com/9.x/glass/png?seed=" + user.getUsername();

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName() != null ? user.getLastName() : "",
                profileImageUrl);
    }
}
