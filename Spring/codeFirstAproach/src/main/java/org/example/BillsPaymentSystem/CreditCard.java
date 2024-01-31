package org.example.BillsPaymentSystem;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends BillingDetail {

    private String type;
    private Integer expirationMonth;
    private Integer expirationYear;

    public CreditCard() {

    }


    @Column(name = "type")
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "expiration_month")
    public Integer getExpirationMonth() {
        return this.expirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year")
    public Integer getExpirationYear() {
        return this.expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }
}
