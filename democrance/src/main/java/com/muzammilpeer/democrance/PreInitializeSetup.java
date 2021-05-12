package com.muzammilpeer.democrance;

import com.muzammilpeer.democrance.entity.PolicyStateEntity;
import com.muzammilpeer.democrance.repository.PolicyStateRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PreInitializeSetup {
    @Autowired
    private PolicyStateRepository policyStateRepository;

    public void run()
    {
        //new/quoted/active
        PolicyStateEntity policyStateEntity1 = new PolicyStateEntity();
        policyStateEntity1.setStateName("new");

        PolicyStateEntity policyStateEntity2 = new PolicyStateEntity();
        policyStateEntity2.setStateName("quoted");

        PolicyStateEntity policyStateEntity3 = new PolicyStateEntity();
        policyStateEntity3.setStateName("active");

        policyStateRepository.saveAll(List.of(policyStateEntity1,policyStateEntity2,policyStateEntity3));
    }
}
