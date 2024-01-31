package org.example.model.dto;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class CarWithPartsDto {
    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Long travelledDistance;
    @Expose
    private Set<PartNameAndPriceDto> parts;

    public CarWithPartsDto() {
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return this.travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public Set<PartNameAndPriceDto> getParts() {
        return this.parts;
    }

    public void setParts(Set<PartNameAndPriceDto> parts) {
        this.parts = parts;
    }
}
