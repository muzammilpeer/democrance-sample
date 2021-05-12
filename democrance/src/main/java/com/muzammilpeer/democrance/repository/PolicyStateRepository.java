package com.muzammilpeer.democrance.repository;

import com.muzammilpeer.democrance.entity.PolicyStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyStateRepository extends JpaRepository<PolicyStateEntity, Long> {
    PolicyStateEntity findByStateName(String stateName);
}
