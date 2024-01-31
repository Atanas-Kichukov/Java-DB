package org.example.SalesDatabase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "store_location")
public class StoreLocation extends BaseEntity {
    private String name;
    private Set<Sale> sales;

    public StoreLocation() {
        sales = new HashSet<>();
    }

    @OneToMany(mappedBy = "storeLocation")
    public Set<Sale> getSales() {
        return this.sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


