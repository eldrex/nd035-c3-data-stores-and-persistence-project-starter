package com.udacity.jdnd.course3.critter.data.repository;

import com.udacity.jdnd.course3.critter.data.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.data.entity.EmployeeSkillEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeEntity, Long> {

    List<EmployeeEntity> findAvailable(Set<DayOfWeek> daysAvailable, List<EmployeeSkillEntity> skills);

}
