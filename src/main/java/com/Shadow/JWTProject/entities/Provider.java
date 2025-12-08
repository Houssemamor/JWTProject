package com.Shadow.JWTProject.entities;

import jakarta.persistence.*;
import java.util.List;

import com.Shadow.JWTProject.models.User;

@Entity
public class Provider extends User {

    private String matricule;
    private String service;
    private String company;

    // Getters et Setters
    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
