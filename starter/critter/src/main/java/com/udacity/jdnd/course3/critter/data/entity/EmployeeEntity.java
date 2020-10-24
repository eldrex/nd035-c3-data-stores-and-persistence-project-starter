package com.udacity.jdnd.course3.critter.data.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@NamedQuery(
        name = "EmployeeEntity.findAvailable",
        query = "SELECT DISTINCT ee FROM EmployeeEntity ee " +
                " INNER JOIN ee.skills s " +
                " INNER JOIN ee.daysAvailable da " +
                " WHERE da IN (:daysAvailable) AND s IN (:skills) "
)
public class EmployeeEntity extends PersonEntity {

    @ManyToMany
    @JoinTable(
            name = "employee_employee_skill",
            joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id")
    )
    private List<EmployeeSkillEntity> skills = new ArrayList<>();

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "day", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "day_of_week", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    public List<EmployeeSkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(List<EmployeeSkillEntity> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
