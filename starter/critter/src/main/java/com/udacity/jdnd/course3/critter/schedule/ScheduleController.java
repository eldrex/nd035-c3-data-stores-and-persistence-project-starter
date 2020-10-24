package com.udacity.jdnd.course3.critter.schedule;

import com.google.common.collect.Lists;
import com.udacity.jdnd.course3.critter.data.entity.PetEntity;
import com.udacity.jdnd.course3.critter.data.entity.ScheduleEntity;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        return convert(
                scheduleService.createSchedule(
                        scheduleDTO.getDate(),
                        scheduleDTO.getEmployeeIds(),
                        scheduleDTO.getPetIds(),
                        scheduleDTO.getActivities())
        );

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAll().stream().map(s -> convert(s)).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findByEmployeeId(employeeId).stream().map(s -> convert(s)).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }

    private ScheduleDTO convert(ScheduleEntity schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(e -> e.getId()).collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPetIds().stream().map(p -> p.getId()).collect(Collectors.toList()));
        scheduleDTO.setActivities(schedule.getActivities().stream().map(a -> a.getSkill()).collect(Collectors.toSet()));
        return scheduleDTO;
    }

    private ScheduleEntity convert(ScheduleDTO scheduleDTO) {
        ScheduleEntity schedule = new ScheduleEntity();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }
}
