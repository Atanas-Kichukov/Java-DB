package org.example.GringottsDatabase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wizard_deposits")
public class WizardDeposit extends BaseEntity{
    private String firstName;
    private String lastName;
    private String notes;
    private Integer age;
    private String magicWandCreator;
    private short magicWandSize;
    private String depositGroup;
    private BigDecimal depositAmount;
    private float depositInterest;
    private float depositCharge;
    private LocalDateTime depositExpirationDate;
    private boolean isDepositExpired;

    public WizardDeposit() {
    }
@Column(name = "first_name", length = 50)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
@Column(name = "last_name", length = 60, nullable = false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
@Column(name = "notes", columnDefinition = "TEXT",length = 1000)
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
@Column(name = "age", nullable = false)
    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
@Column(name = "magic_wand_creator",length = 100)
    public String getMagicWandCreator() {
        return this.magicWandCreator;
    }

    public void setMagicWandCreator(String magicWandCreator) {
        this.magicWandCreator = magicWandCreator;
    }
@Column(name = "magic_wand_size")
    public short getMagicWandSize() {
        return this.magicWandSize;
    }

    public void setMagicWandSize(short magicWandSize) {
        this.magicWandSize = magicWandSize;
    }
@Column(name = "deposit_group",length = 20)
    public String getDepositGroup() {
        return this.depositGroup;
    }

    public void setDepositGroup(String depositGroup) {
        this.depositGroup = depositGroup;
    }
@Column(name = "deposit_amount",precision = 10,scale = 3)
    public BigDecimal getDepositAmount() {
        return this.depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }
@Column(name = "deposit_interest")
    public float getDepositInterest() {
        return this.depositInterest;
    }

    public void setDepositInterest(float depositInterest) {
        this.depositInterest = depositInterest;
    }
@Column(name = "deposit_charge")
    public float getDepositCharge() {
        return this.depositCharge;
    }

    public void setDepositCharge(float depositCharge) {
        this.depositCharge = depositCharge;
    }
@Column(name = "deposit_expiration_date")
    public LocalDateTime getDepositExpirationDate() {
        return this.depositExpirationDate;
    }

    public void setDepositExpirationDate(LocalDateTime depositExpirationDate) {
        this.depositExpirationDate = depositExpirationDate;
    }
@Column(name = "is_deposit_expired")
    public boolean isDepositExpired() {
        return this.isDepositExpired;
    }

    public void setDepositExpired(boolean depositExpired) {
        isDepositExpired = depositExpired;
    }
}
