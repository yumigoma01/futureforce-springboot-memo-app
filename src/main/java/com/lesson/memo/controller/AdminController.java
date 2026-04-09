package com.lesson.memo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lesson.memo.model.Admin;
import com.lesson.memo.repository.AdminRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/memo")
class AdminController {
	
    @Autowired
    private AdminRepository adminRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;

	@GetMapping("/admin/signup")
	public String showSignupPage(Model model) {
	    model.addAttribute("admin", new Admin());
	    return "admin/signup";
	}
	
	@PostMapping("/admin/signup")
	public String signup(@ModelAttribute @Valid  Admin admin,
	        BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        return "admin/signup";
	    }
	    admin.setCreatedAt(LocalDateTime.now());
	    admin.setUpdatedAt(LocalDateTime.now());
	    String hashedPassword =passwordEncoder.encode(admin.getPassword());
	    admin.setPassword(hashedPassword);
	    
	    adminRepository.save(admin);
	
	    return "redirect:/memo/admin/signin";
	}  
	
	
	@GetMapping("/admin/signin")
	public String signin() {
		return "admin/signin";
	}
	
}