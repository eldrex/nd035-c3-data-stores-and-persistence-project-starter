package com.udacity.jdnd.course3.critter.user;

import com.google.common.collect.Lists;
import com.udacity.jdnd.course3.critter.data.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.data.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.data.entity.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.PersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    PersonService personService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return convert(
                personService.createCustomer(
                        customerDTO.getName(),
                        customerDTO.getNotes(),
                        customerDTO.getPhoneNumber(),
                        customerDTO.getPetIds())
        );
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return Lists.newArrayList(personService.findAllCustomers()).stream().map(c -> convert(c)).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Optional<CustomerEntity> customer = personService.findCustomerByPetId(petId);
        if (!customer.isPresent()) {
            // TODO handle
        }

        return convert(customer.get());
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return convert(
                personService.createEmployee(
                        employeeDTO.getName(),
                        employeeDTO.getSkills(),
                        employeeDTO.getDaysAvailable()
                )
        );
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Optional<EmployeeEntity> employee = personService.findEmployee(employeeId);
        if (!employee.isPresent()) {
            // TODO handle
        }
        return convert(employee.get());
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        personService.setAvailability(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return personService.findAvailableEmployees(
                employeeDTO.getDate(),
                employeeDTO.getSkills()
        ).stream().map(this::convert).collect(Collectors.toList());
    }

    private CustomerDTO convert(CustomerEntity customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer.getPets() != null) {
            customerDTO.setPetIds(customer.getPets().stream().map(c -> c.getId()).collect(Collectors.toList()));
        }

        return customerDTO;
    }

    private CustomerEntity convert(CustomerDTO customerDTO) {
        CustomerEntity customer = new CustomerEntity();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private EmployeeDTO convert(EmployeeEntity employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        if (employee.getSkills() != null) {
            employeeDTO.setSkills(employee.getSkills().stream().map(s -> s.getSkill()).collect(Collectors.toSet()));
        }

        return employeeDTO;
    }

    private EmployeeEntity convert(EmployeeDTO employeeDTO) {
        EmployeeEntity employee = new EmployeeEntity();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

}
