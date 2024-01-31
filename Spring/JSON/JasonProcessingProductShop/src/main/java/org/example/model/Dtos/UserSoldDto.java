package org.example.model.Dtos;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserSoldDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<ProductWIthBuyerDto> soldProducts;

    public UserSoldDto() {
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<ProductWIthBuyerDto> getSoldProducts() {
        return this.soldProducts;
    }

    public void setSoldProducts(Set<ProductWIthBuyerDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
