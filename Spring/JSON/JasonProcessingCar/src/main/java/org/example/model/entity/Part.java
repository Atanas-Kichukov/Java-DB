package org.example.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity {
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private Supplier supplier;


    public Part() {

    }

    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    @ManyToOne
    public Supplier getSupplier() {
        return this.supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Column
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(name, part.name) && Objects.equals(price, part.price) && Objects.equals(quantity, part.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, quantity);
    }
    //това се прави при случай че се поддат две инстанции с еднакви стойности да каже че са равни за да не се чупи програмата
}
