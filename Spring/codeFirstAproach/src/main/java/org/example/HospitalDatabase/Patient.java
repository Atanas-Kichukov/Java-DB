package org.example.HospitalDatabase;

import org.example.BillsPaymentSystem.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends BaseEntity {
    private String fistName;
    private String lastName;
    private String address;
    private String email;
    private Date dateOfBirth;
    private String picture;
    private boolean isInsured;
    private Set<Visitation>visitations;
    private Set<Diagnose>diagnoses;
    private Set<Medicament>medicaments;


    public Patient() {
        visitations = new HashSet<>();
        diagnoses = new HashSet<>();
        medicaments = new HashSet<>();
    }
    @Column(name = "first_name")
    public String getFistName() {
        return this.fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }
    @Column(name = "last_name")
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Column(name = "addresses")
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Column(name = "emails")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name = "date_of_birth")
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    @Column(name = "pictures")
    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    @Column(name = "is_insured")
    public boolean isInsured() {
        return this.isInsured;
    }

    public void setInsured(boolean insured) {
        isInsured = insured;
    }
    @OneToMany(mappedBy = "patient")
    public Set<Visitation> getVisitations() {
        return this.visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }
    @ManyToMany
    public Set<Diagnose> getDiagnoses() {
        return this.diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }
    @ManyToMany
    public Set<Medicament> getMedicaments() {
        return this.medicaments;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
    }
}
