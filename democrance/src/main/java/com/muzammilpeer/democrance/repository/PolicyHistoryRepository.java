package com.muzammilpeer.democrance.repository;

import com.muzammilpeer.democrance.entity.PolicyHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyHistoryRepository extends JpaRepository<PolicyHistoryEntity, Long> {
}
