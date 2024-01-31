package org.example.model.dto;

import com.google.gson.annotations.Expose;
import org.example.model.entity.Car;
import org.example.model.entity.Customer;

public class SaleSeedDto {
    @Expose
    private Car car;
    @Expose
    private Customer customer;
    @Expose
    private Double discountPercentage;

    public SaleSeedDto() {
    }

    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getDiscountPercentage() {
        return this.discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
