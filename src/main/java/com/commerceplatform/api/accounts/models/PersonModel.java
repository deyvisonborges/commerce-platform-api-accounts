package com.commerceplatform.api.accounts.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "persons")
public class PersonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private LocalDate birthdate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "phone_area")
    private String phoneArea;

    public PersonModel() {
    }

    public PersonModel(Long id, String firstName, String lastName, LocalDate birthdate, String phoneNumber, String phoneArea) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.phoneArea = phoneArea;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneArea() {
        return phoneArea;
    }

    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }
}
