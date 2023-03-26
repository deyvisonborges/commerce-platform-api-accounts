package com.commerceplatform.api.accounts.models.redis;

import jakarta.persistence.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@RedisHash("recovery_code")
public class RecoveryPasswordModel {

    @Id
    private String id;
    @Indexed
    private String email;
    private String code;
    private LocalDateTime createdAt = LocalDateTime.now();

    public RecoveryPasswordModel() {
    }

    public RecoveryPasswordModel(String id, String email, String code, LocalDateTime createdAt) {
        this.id = id;
        this.email = email;
        this.code = code;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
