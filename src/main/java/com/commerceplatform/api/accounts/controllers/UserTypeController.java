package com.commerceplatform.api.accounts.controllers;

import com.commerceplatform.api.accounts.models.jpa.UserTypeModel;
import com.commerceplatform.api.accounts.services.UserTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user-type")
public class UserTypeController {
    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @GetMapping
    public ResponseEntity<List<UserTypeModel>> findAll() {
        return ResponseEntity.status(200).body(userTypeService.findAll());
    }
}
