package com.muzammilpeer.democrance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.muzammilpeer.democrance.entity.CustomerEntity;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    List<CustomerEntity> findAllByFirstName(String firstName);
    List<CustomerEntity> findAllByLastName(String lastName);
    List<CustomerEntity> findAllByDob(String dob);
}
