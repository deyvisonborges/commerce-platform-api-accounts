package com.commerceplatform.api.accounts.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public class BaseController<T> {
    private final JpaRepository<T, Long> repo;

    public BaseController(JpaRepository<T, Long> repo) {
        this.repo = repo;
    }

    protected List<T> findAll() {
        return repo.findAll();
    }
}
