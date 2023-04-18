package com.commerceplatform.api.accounts.controllers;

import com.commerceplatform.api.accounts.dtos.inputs.CreateRoleInput;
import com.commerceplatform.api.accounts.models.jpa.RoleModel;
import com.commerceplatform.api.accounts.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleModel> create(@RequestBody CreateRoleInput input) {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.create(input));
    }

    @GetMapping
    public ResponseEntity<List<RoleModel>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.findAll());
    }
}
