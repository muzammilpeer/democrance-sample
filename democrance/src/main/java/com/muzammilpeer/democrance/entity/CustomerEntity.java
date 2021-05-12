package com.muzammilpeer.democrance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(
        name="customer_entity",
        uniqueConstraints=
        @UniqueConstraint(columnNames={"first_name", "last_name", "dob"})
)

public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id",columnDefinition = "BIGINT NOT NULL AUTO_INCREMENT")
    private Long customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "dob", nullable = false)
    private String dob;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id",referencedColumnName = "customer_id")
    private List<PolicyEntity> policies = new ArrayList<>();

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime updatedOn;

//    //for temp work sessionToken stored in db in same tablet, for load balancer, shift it to proper table with session manager handling from single place
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String sessionToken;

    public List<PolicyEntity> getPolicies() {
        return policies;
    }

    public void setPolicies(List<PolicyEntity> policies) {
        this.policies = policies;
    }

    @PreUpdate
    public void preUpdate() {
        updatedOn = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }
}
