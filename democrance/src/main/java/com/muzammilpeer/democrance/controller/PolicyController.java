package com.muzammilpeer.democrance.controller;

import com.muzammilpeer.democrance.dto.QuoteDTO;
import com.muzammilpeer.democrance.entity.CustomerEntity;
import com.muzammilpeer.democrance.entity.PolicyEntity;
import com.muzammilpeer.democrance.entity.PolicyHistoryEntity;
import com.muzammilpeer.democrance.entity.PolicyStateEntity;
import com.muzammilpeer.democrance.exception.ResourceNotFoundException;
import com.muzammilpeer.democrance.repository.CustomerRepository;
import com.muzammilpeer.democrance.repository.PolicyRepository;
import com.muzammilpeer.democrance.repository.PolicyStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PolicyController {
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PolicyStateRepository policyStateRepository;


    private QuoteDTO convertToDto(PolicyEntity policy) {
        PolicyStateEntity policyStateEntity = policyStateRepository.findById(policy.getPolicyStateId()).
                orElseThrow(() -> new ResourceNotFoundException("Not valid policy state in workflow with id :" + policy.getPolicyStateId()));
        QuoteDTO quoteDto = new QuoteDTO();
        quoteDto.setPolicyId(policy.getPolicyId());
        quoteDto.setCover(policy.getCover());
        quoteDto.setCustomerId(policy.getCustomerId());
        quoteDto.setPremium(policy.getPremium());
        quoteDto.setType(policy.getType());
        quoteDto.setState(policyStateEntity.getStateName());
        return quoteDto;
    }
    private PolicyEntity convertToEntity(QuoteDTO quoteDto) throws ParseException {
        PolicyEntity policy = new PolicyEntity();
        policy.setPolicyId(quoteDto.getPolicyId());
        policy.setCover(quoteDto.getCover());
        policy.setCustomerId(quoteDto.getCustomerId());
        policy.setPremium(quoteDto.getPremium());
        policy.setType(quoteDto.getType());

        PolicyStateEntity policyStateEntity = policyStateRepository.findByStateName(quoteDto.getState());
        if (policyStateEntity == null) {
            throw new ResourceNotFoundException("Not valid policy state in workflow with name :" + quoteDto.getState());
        }
        policy.setPolicyStateId(policyStateEntity.getPolicyStateId());
        return policy;
    }

    @PostMapping("/quote")
    public QuoteDTO savePolicy(@RequestBody QuoteDTO request) throws ParseException {
        CustomerEntity existingCustomer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + request.getCustomerId()));
        if (existingCustomer != null) {
            //fetch the id of 'new' and set it for further process as hardcoded for savepolicy
            request.setState("new");
            PolicyStateEntity policyStateEntity = policyStateRepository.findByStateName(request.getState());
            if (policyStateEntity == null) {
                throw new ResourceNotFoundException("Not valid policy state in workflow with name :" + "new");
            }


            PolicyEntity policyEntity = new PolicyEntity();
            existingCustomer.getPolicies().add(this.convertToEntity(request));

            List<PolicyEntity> allPolicies = customerRepository.save(existingCustomer).getPolicies();
            //if policy is inserted successfully
            PolicyEntity lastPolicyEntity = allPolicies.get(allPolicies.size()-1);

            PolicyHistoryEntity policyHistoryEntity = new PolicyHistoryEntity();
            policyHistoryEntity.setPolicyId(lastPolicyEntity.getPolicyId());
            policyHistoryEntity.setPolicyStateName(policyStateEntity.getStateName());
            policyHistoryEntity.setQuoteLogs(this.convertToDto(lastPolicyEntity));

            lastPolicyEntity.getPolicyHistories().add(policyHistoryEntity);
            customerRepository.save(existingCustomer);

            return this.convertToDto(lastPolicyEntity);
        }
        throw new ResourceNotFoundException("Last policy not inserted not found with id :" + request.getPolicyId());
    }

    @PutMapping("/quote")
    public QuoteDTO updatePolicy(@RequestBody QuoteDTO request) {
        PolicyStateEntity policyStateEntity = policyStateRepository.findByStateName(request.getState());
        if (policyStateEntity == null) {
            throw new ResourceNotFoundException("Not valid policy state in workflow with name :" + request.getState());
        }
        if (request.getState().equalsIgnoreCase("new")) {
            throw new ResourceNotFoundException("User state can not reverted to new for policy id:" + request.getPolicyId());
        }

        PolicyEntity existingPolicyEntity = policyRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id :" + request.getPolicyId()));
        if (existingPolicyEntity != null) {
            existingPolicyEntity.setPolicyStateId(policyStateEntity.getPolicyStateId());

            PolicyHistoryEntity policyHistoryEntity = new PolicyHistoryEntity();
            policyHistoryEntity.setPolicyId(existingPolicyEntity.getPolicyId());
            policyHistoryEntity.setPolicyStateName(policyStateEntity.getStateName());
            policyHistoryEntity.setQuoteLogs(this.convertToDto(existingPolicyEntity));

            existingPolicyEntity.getPolicyHistories().add(policyHistoryEntity);
            policyRepository.save(existingPolicyEntity);
        }
        return convertToDto(existingPolicyEntity);
    }

}
