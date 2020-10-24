package com.udacity.jdnd.course3.critter.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.udacity.jdnd.course3.critter.data.entity.ScheduleEntity;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeSkillRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeSkillRepository employeeSkillRepository;

    @Transactional
    public ScheduleEntity createSchedule(LocalDate date, List<Long> employeeIds, List<Long> petIds, Set<EmployeeSkill> skills) {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setDate(date);

        schedule.setEmployees(
                Lists.newArrayList(employeeRepository.findAllById(employeeIds))
        );

        schedule.setPetIds(
                Lists.newArrayList(petRepository.findAllById(petIds))
        );

        Iterable skillIds = employeeSkillRepository.findAllById(
                skills.stream().map(s -> (long) s.ordinal()).collect(Collectors.toList())
        );
        schedule.setActivities(
                Sets.newHashSet(skillIds)
        );

        return scheduleRepository.save(schedule);
    }

    public List<ScheduleEntity> findAll() {
        return Lists.newArrayList(scheduleRepository.findAll());
    }

    public List<ScheduleEntity> findByEmployeeId(long employeeId) {
        return scheduleRepository.findByEmployeesId(employeeId);
    }
}
