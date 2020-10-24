package com.udacity.jdnd.course3.critter.data.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Entity
public class CustomerEntity extends PersonEntity {

    private String phoneNumber;
    @Nationalized
    private String notes;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<PetEntity> pets = new ArrayList<>();

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Collection<PetEntity> getPets() {
        return pets;
    }

    public void setPets(Collection<PetEntity> pets) {
        this.pets = pets;
    }
}
