package com.lesson.memo.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

@Service
public class AdminDetailService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepository.findByEmail(email);

        if (adminOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        Admin admin = adminOptional.get();

        return new User(
            admin.getEmail(),
            admin.getPassword(),
            new ArrayList<>()
        );
    }
}