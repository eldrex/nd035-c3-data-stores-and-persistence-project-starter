package com.udacity.jdnd.course3.critter.data.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "schedule")
@NamedQueries(
        {
                @NamedQuery(
                        name = "ScheduleEntity.findByCustomerId",
                        query = "SELECT se FROM ScheduleEntity se INNER JOIN se.pets p" +
                                " WHERE p.owner.id = :customerId"
                ),
                @NamedQuery(
                        name = "ScheduleEntity.findByPetId",
                        query = "SELECT se FROM ScheduleEntity se INNER JOIN se.pets p" +
                                " WHERE p.id = :petId"
                )
        }
)

public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "schedule_employee",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id")
    )
    private List<EmployeeEntity> employees = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "schedule_pet",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id", referencedColumnName = "id")
    )
    private List<PetEntity> pets = new ArrayList<>();

    @Column
    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name = "schedule_employee_skill",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id")
    )
    private Set<EmployeeSkillEntity> activities = new HashSet<>();

    public List<EmployeeEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeEntity> employees) {
        this.employees = employees;
    }

    public List<PetEntity> getPetIds() {
        return pets;
    }

    public void setPetIds(List<PetEntity> petIds) {
        this.pets = petIds;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkillEntity> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkillEntity> activities) {
        this.activities = activities;
    }
}
