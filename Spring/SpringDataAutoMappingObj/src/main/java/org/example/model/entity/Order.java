package org.example.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    private User buyer;
    private Set<Game> games;

    public Order() {
    }
    @OneToOne
    public User getBuyer() {
        return this.buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
    @ManyToMany(fetch = FetchType.EAGER)
    public Set<Game> getGames() {
        return this.games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}
