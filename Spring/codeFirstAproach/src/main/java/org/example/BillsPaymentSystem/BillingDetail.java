package org.example.BillsPaymentSystem;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.JOINED)
public class BillingDetail extends BaseEntity {

    private String number;
    private BankUser bankUser;

    public BillingDetail() {

    }


    @Column(name = "numbers", nullable = false, unique = true)
    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    public BankUser getBankUser() {
        return this.bankUser;
    }

    public void setBankUser(BankUser bankUser) {
        this.bankUser = bankUser;
    }
}
