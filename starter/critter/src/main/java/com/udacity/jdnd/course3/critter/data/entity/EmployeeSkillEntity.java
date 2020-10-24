package com.udacity.jdnd.course3.critter.data.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;

@Entity
@Table(name = "employee_skill")
public class EmployeeSkillEntity {

    @Id
    @Column
    Long id;

    @Column
    @Enumerated(value = EnumType.STRING)
    EmployeeSkill skill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmployeeSkill getSkill() {
        return skill;
    }

    public void setSkill(EmployeeSkill skill) {
        this.skill = skill;
    }
}
