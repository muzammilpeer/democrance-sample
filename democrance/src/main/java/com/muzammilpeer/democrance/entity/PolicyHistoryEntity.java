package com.muzammilpeer.democrance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.muzammilpeer.democrance.dto.QuoteDTO;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity

@DynamicUpdate
@TypeDef(
        typeClass = JsonType.class,
        defaultForType = PolicyEntity.class
)
public class PolicyHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policy_history_id",columnDefinition = "BIGINT NOT NULL AUTO_INCREMENT")
    private Long policyHistoryId;

    @Column(name = "policy_id", nullable = false)
    private Long policyId;

    @Column(name = "policy_state_name", nullable = false)
    private String policyStateName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(columnDefinition = "json")
    @ToString.Exclude
    private String quoteLogs;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

    public String getQuoteLogs() {
        return this.quoteLogs;
//        return new Gson().fromJson(this.quoteLogs,QuoteDTO.class);
    }

    public void setQuoteLogs(QuoteDTO quoteLogs) {
        this.quoteLogs = new Gson().toJson(quoteLogs);
    }
    public void setQuoteLogs(String quoteLogs) {
        this.quoteLogs =  new Gson().toJson(new Gson().fromJson(quoteLogs,QuoteDTO.class));
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