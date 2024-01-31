package org.example.model.dto;

import com.google.gson.annotations.Expose;

public class SupplierSeedDto {
    @Expose
    private String name;
    @Expose
    private boolean isImporter;

    public SupplierSeedDto() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImporter() {
        return this.isImporter;
    }

    public void setImporter(boolean importer) {
        isImporter = importer;
    }
}
