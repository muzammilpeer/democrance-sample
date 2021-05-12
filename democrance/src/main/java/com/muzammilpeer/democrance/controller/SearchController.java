package com.muzammilpeer.democrance.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.muzammilpeer.democrance.dto.QuoteDTO;
import com.muzammilpeer.democrance.dto.QuoteHistoryDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;

@RestController
@RequestMapping("/api/v1")
public class SearchController {
    @Autowired
    private PolicyRepository policyRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PolicyStateRepository policyStateRepository;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private Long policyHistoryId;
    private Long policyId;
    private String policyStateName;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String quoteLogs;


    private QuoteHistoryDTO convertToDto(PolicyHistoryEntity policy) {
        PolicyStateEntity policyStateEntity = policyStateRepository.findByStateName(policy.getPolicyStateName());
        if (policyStateEntity == null) {
            throw new ResourceNotFoundException("Not valid policy state in workflow with name :" + policy.getPolicyStateName());
        }

        QuoteHistoryDTO quoteDto = new QuoteHistoryDTO();
        quoteDto.setPolicyId(policy.getPolicyId());
        quoteDto.setPolicyHistoryId(policy.getPolicyHistoryId());
        quoteDto.setQuoteLogs(policy.getQuoteLogs());
        quoteDto.setPolicyStateName(policy.getPolicyStateName());
        return quoteDto;
    }
    private PolicyHistoryEntity convertToEntity(QuoteHistoryDTO quoteDto) throws ParseException {
        PolicyHistoryEntity policy = new PolicyHistoryEntity();
        policy.setPolicyId(quoteDto.getPolicyId());
        policy.setPolicyHistoryId(quoteDto.getPolicyHistoryId());
        policy.setQuoteLogs(quoteDto.getQuoteLogs());
        policy.setPolicyStateName(quoteDto.getPolicyStateName());
        return policy;
    }

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

    @GetMapping("/policies")
    @ResponseBody
//    public String searchByCustomerID(@RequestParam(name = "customer_id") Optional<String> customerId) throws ParseException {
    public List<QuoteDTO> searchByCustomerAndType(@RequestParam(name = "customer_id") Optional<String>  customerId,@RequestParam(name = "dob") Optional<String>  dob,@RequestParam(name = "type") Optional<String>  type,@RequestParam(name = "firstName") Optional<String>  firstName,@RequestParam(name = "lastName") Optional<String>  lastName) throws ParseException {
        if (customerId.isPresent()) {
            List<PolicyEntity> listOfQuotes = policyRepository.findByCustomerId(Long.parseLong(customerId.get()));
            List<QuoteDTO> allQuotes = new ArrayList<QuoteDTO>();
            for (PolicyEntity item:listOfQuotes) {
                allQuotes.add(this.convertToDto(item));
            }
            return allQuotes;
        }
        if (firstName.isPresent())
        {
            List<CustomerEntity> listOfCustomers = customerRepository.findAllByFirstName(firstName.get());
            List<QuoteDTO> allQuotes = new ArrayList<QuoteDTO>();
            for (CustomerEntity customer:listOfCustomers) {
                for (PolicyEntity item:customer.getPolicies()) {
                    allQuotes.add(this.convertToDto(item));
                }
            }
            return allQuotes;
        }

        if (lastName.isPresent()) {
            List<CustomerEntity> listOfCustomers = customerRepository.findAllByLastName(lastName.get());
            List<QuoteDTO> allQuotes = new ArrayList<QuoteDTO>();
            for (CustomerEntity customer:listOfCustomers) {
                for (PolicyEntity item:customer.getPolicies()) {
                    allQuotes.add(this.convertToDto(item));
                }
            }
            return allQuotes;
        }

        if (dob.isPresent()) {
            List<CustomerEntity> listOfCustomers = customerRepository.findAllByDob(dob.get());
            List<QuoteDTO> allQuotes = new ArrayList<QuoteDTO>();
            for (CustomerEntity customer:listOfCustomers) {
                for (PolicyEntity item:customer.getPolicies()) {
                    allQuotes.add(this.convertToDto(item));
                }
            }
            return allQuotes;
        }

        if (type.isPresent()) {
            List<PolicyEntity> listOfQuotes = policyRepository.findByType(type.get());
            List<QuoteDTO> allQuotes = new ArrayList<QuoteDTO>();
            for (PolicyEntity item:listOfQuotes) {
                allQuotes.add(this.convertToDto(item));
            }
            return allQuotes;
        }
//        return String.valueOf("customerId=" + customerId);
        return new ArrayList<>();
    }


    @GetMapping("/policies/{policy_id}")
    @ResponseBody
    public QuoteDTO searchByPolicyId(@PathVariable("policy_id") String policyId) throws ParseException {
//        return String.valueOf("policyId=" + policyId);
        Optional<PolicyEntity> quoteEntity = policyRepository.findById(Long.parseLong(policyId));
        if (quoteEntity.isPresent()) {
            return this.convertToDto(quoteEntity.get());
        }
        throw new ResourceNotFoundException("Quote not exist with id:" + policyId);
    }


    @GetMapping("/policies/{policy_id}/{history}")
    @ResponseBody
    public List<QuoteHistoryDTO> searchByPolicyIdForHistory(@PathVariable("policy_id") String policyId,@PathVariable("history") String history) throws ParseException {
//        return String.valueOf("policy_id=" +policyId+":"+ "history="+history);
        Optional<PolicyEntity> quoteEntity = policyRepository.findById(Long.parseLong(policyId));
        if (quoteEntity.isPresent()) {
            List<QuoteHistoryDTO> allQuotes = new ArrayList<QuoteHistoryDTO>();
            for (PolicyHistoryEntity historyModel:quoteEntity.get().getPolicyHistories()) {
                allQuotes.add(this.convertToDto(historyModel));
            }
            return allQuotes;
        }else {
            throw new ResourceNotFoundException("Quote not exist with id:" + policyId);
        }
    }
}