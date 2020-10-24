package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.entity.EmployeeSkillEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.data.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.data.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.repository.EmployeeSkillRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    PetRepository petRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    EmployeeRepository employeeRepo;

    @Autowired
    EmployeeSkillRepository employeeSkillRepo;

    @Transactional
    public CustomerEntity createCustomer(String name, String notes, String phoneNumber, List<Long> petIds) {
        CustomerEntity customer = new CustomerEntity();
        customer.setName(name);
        customer.setNotes(notes);
        customer.setPhoneNumber(phoneNumber);
        if (petIds != null) {
            customer.setPets(Lists.newArrayList(petRepo.findAllById(petIds)));
        }


        return customerRepo.save(customer);
    }

    public CustomerEntity saveCustomer(CustomerEntity customer) {
        return customerRepo.save(customer);
    }

    public Iterable<CustomerEntity> findAllCustomers() {
        return customerRepo.findAll();
    }

    public Optional<CustomerEntity> findCustomerByPetId(Long petId) {
        return customerRepo.findCustomerByPetsId(petId);
    }

    public Optional<CustomerEntity> findCustomerById(long ownerId) {
        return customerRepo.findById(ownerId);
    }

    @Transactional
    public EmployeeEntity createEmployee(String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        EmployeeEntity emp = new EmployeeEntity();
        emp.setName(name);

        if (skills != null && !skills.isEmpty()) {
            Iterable<EmployeeSkillEntity> employeeSkills = employeeSkillRepo.findAllById(skills.stream().map(s -> (long) s.ordinal()).collect(Collectors.toList()));

            emp.setSkills(
                    Lists.newArrayList(employeeSkills)
            );
        }

        if (daysAvailable != null && !daysAvailable.isEmpty()) {
            emp.setDaysAvailable(daysAvailable);
        }


        return employeeRepo.save(emp);
    }

    public Optional<EmployeeEntity> findEmployee(long employeeId) {
        return employeeRepo.findById(employeeId);
    }


}
