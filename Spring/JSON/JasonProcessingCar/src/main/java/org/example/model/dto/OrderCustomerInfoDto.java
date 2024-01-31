package org.example.model.dto;

import com.google.gson.annotations.Expose;
import org.example.model.entity.Sale;

import java.time.LocalDate;
import java.util.Set;

public class OrderCustomerInfoDto {
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private String birthDate;
    @Expose
    private boolean isYoungerDriver;
    @Expose
    private Set<Sale> sales;

    public OrderCustomerInfoDto() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public boolean isYoungerDriver() {
        return this.isYoungerDriver;
    }

    public void setYoungerDriver(boolean youngerDriver) {
        isYoungerDriver = youngerDriver;
    }

    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
