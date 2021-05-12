package com.muzammilpeer.democrance.repository;
import com.muzammilpeer.democrance.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);
}