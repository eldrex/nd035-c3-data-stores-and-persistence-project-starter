package com.udacity.jdnd.course3.critter.data.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
// I have chosen JOINED strategy because there is no requirement to select both employee and customer at once
@Inheritance(strategy = InheritanceType.JOINED)
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nationalized
    @Column
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
