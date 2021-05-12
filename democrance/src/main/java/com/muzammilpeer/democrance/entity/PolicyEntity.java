package com.muzammilpeer.democrance.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(
        name="policy_entity",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"customer_id", "type", "premium", "cover"})
)
public class PolicyEntity extends Object {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_id",columnDefinition = "BIGINT NOT NULL AUTO_INCREMENT")
    private Long policyId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "premium", nullable = false)
    private long premium;

    @Column(name = "cover", nullable = false)
    private long cover;

//    private String state;
//
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "policy_id",referencedColumnName = "policy_id")
    private List<PolicyHistoryEntity> policyHistories  = new ArrayList<>();

    @Column(name = "policy_state_id", nullable = false)
    private Long policyStateId;

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