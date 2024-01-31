package org.example.model.dto;

import com.google.gson.annotations.Expose;
import org.example.model.entity.Part;

import java.util.Set;

public class LocalSupplierDto {
    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private Integer partsCount;

    public LocalSupplierDto() {
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


    public Integer getPartsCount() {
        return this.partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }
}
