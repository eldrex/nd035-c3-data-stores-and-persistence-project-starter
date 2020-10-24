package com.udacity.jdnd.course3.critter.pet;

import com.google.common.collect.Lists;
import com.udacity.jdnd.course3.critter.data.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.data.entity.PetEntity;
import com.udacity.jdnd.course3.critter.service.PersonService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @Autowired
    PersonService personService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        PetEntity petEntity = petService.save(convert(petDTO));
        if (petDTO.getOwnerId() > 0L) {
            Optional<CustomerEntity> customer = personService.findCustomerById(petDTO.getOwnerId());
            if (!customer.isPresent()) {
                // TODO hadnle
            } else {
                petEntity.setOwner(customer.get());
                customer.get().getPets().add(petEntity);
                personService.saveCustomer(customer.get());
            }
        }
        return convert(petEntity);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Optional<PetEntity> pet = petService.findById(petId);
        if (!pet.isPresent()) {
            // TODO handle
        }
        return convert(pet.get());
    }

    @GetMapping
    public List<PetDTO> getPets() {
        return Lists.newArrayList(petService.findAllPets()).stream().map(p -> convert(p)).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return Lists.newArrayList(petService.findAllPetsByOwnerId(ownerId)).stream().map(p -> convert(p)).collect(Collectors.toList());
    }

    private PetDTO convert(PetEntity pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getOwner() != null) {
            petDTO.setOwnerId(pet.getOwner().getId());
        }

        return petDTO;
    }

    private PetEntity convert(PetDTO petDTO) {
        PetEntity pet = new PetEntity();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }

}
