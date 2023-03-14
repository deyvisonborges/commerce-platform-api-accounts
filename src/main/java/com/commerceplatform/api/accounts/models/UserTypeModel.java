package com.commerceplatform.api.accounts.models;

import com.commerceplatform.api.accounts.enums.UserTypeEnum;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "roles")
public class UserTypeModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private UserTypeEnum type;

    private String description;

    public UserTypeModel() {
    }

    public UserTypeModel(Long id, UserTypeEnum type, String description) {
        this.id = id;
        this.type = type;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserTypeEnum getType() {
        return type;
    }

    public void setType(UserTypeEnum type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
