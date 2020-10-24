package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.data.entity.PersonEntity;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.data.entity.PetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;


    public PetEntity save(PetEntity pet) {
        return petRepository.save(pet);
    }

    public Optional<PetEntity> findById(Long id) {
        return petRepository.findById(id);
    }

    public Iterable<PetEntity> findAllPets() {
        return petRepository.findAll();
    }

    public Iterable<PetEntity> findAllPetsByOwnerId(Long id) {
        return petRepository.findAllByOwnerId(id);
    }
}
