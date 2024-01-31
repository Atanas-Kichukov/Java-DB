package org.example.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity{
    private String name;
    private boolean isImporter;
    private Set<Part> parts;


    public Supplier() {
    }
    @Column
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "is_importer")
    public boolean isImported() {
        return this.isImporter;
    }

    public void setImported(boolean imported) {
        isImporter = imported;
    }
    @OneToMany(mappedBy = "supplier",fetch = FetchType.EAGER)
    public Set<Part> getParts() {
        return this.parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }
}
