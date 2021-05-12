package com.muzammilpeer.democrance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(
        name="policy_state_entity",
        uniqueConstraints=
@UniqueConstraint(columnNames={ "state_name"})

)

public class PolicyStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_state_id", columnDefinition = "BIGINT NOT NULL AUTO_INCREMENT")
    private Long policyStateId;

    @Column(name = "state_name", nullable = false)
    private String stateName;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }


}
