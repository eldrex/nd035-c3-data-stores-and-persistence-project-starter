package com.udacity.jdnd.course3.critter.data.repository;

import com.udacity.jdnd.course3.critter.data.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findCustomerByPetsId(Long petId);
}
