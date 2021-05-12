package com.muzammilpeer.democrance.repository;

import com.muzammilpeer.democrance.entity.PolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<PolicyEntity, Long> {
    List<PolicyEntity> findByCustomerId(Long customerId);
    List<PolicyEntity> findByType(String type);
    List<PolicyEntity> findByPremium(String premium);
    List<PolicyEntity> findByCover(String cover);
}
