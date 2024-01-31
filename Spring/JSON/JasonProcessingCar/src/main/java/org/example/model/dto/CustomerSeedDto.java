package org.example.model.dto;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;

public class CustomerSeedDto {
    @Expose
    private String name;
    @Expose
    private String dateOfBirth;
    @Expose
    private boolean isYoungDriver;

    public CustomerSeedDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isYoungDriver() {
        return this.isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
