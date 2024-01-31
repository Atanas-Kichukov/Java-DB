package org.example.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {
    private Car car;
    private Customer customer;
    private Double discountPercentage;

    public Sale() {
    }
    @OneToOne
    public Car getCar() {
        return this.car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    @ManyToOne
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    @Column(name = "discount")
    public Double getDiscountPercentage() {
        return this.discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
