package com.muzammilpeer.democrance.controller;

import com.muzammilpeer.democrance.dto.CustomerDTO;
import com.muzammilpeer.democrance.dto.QuoteDTO;
import com.muzammilpeer.democrance.entity.CustomerEntity;
import com.muzammilpeer.democrance.entity.PolicyEntity;
import com.muzammilpeer.democrance.exception.ErrorDetails;
import com.muzammilpeer.democrance.exception.ResourceNotFoundException;
import com.muzammilpeer.democrance.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDTO convertToDto(CustomerEntity customer) {
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setDob(customer.getDob());

        return customerDto;
    }
    public CustomerEntity convertToEntity(CustomerDTO customerDto) {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setDob(customerDto.getDob());
        return customer;
    }

    @PostMapping("/customer")
//    public CustomerEntity createCustomer(@RequestBody CustomerDTO request) throws ParseException {
//        return customerRepository.save(this.convertToEntity(request));
//    }
    public ResponseEntity<Object> createCustomer(@RequestBody CustomerDTO request) {
        CustomerEntity customerEntity = customerRepository.save(this.convertToEntity(request));
        return ResponseEntity.ok().body(customerEntity);
//        try {
//            CustomerEntity customerEntity = customerRepository.save(this.convertToEntity(request));
//            if (customerEntity.getFirstName() != null) {
//                return ResponseEntity.ok().body(customerEntity);
//            }else {
//                return ResponseEntity.badRequest().body(request);
//            }
//        }catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
////            ErrorDetails errorDetails =
////                    new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
////            return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @PutMapping("/customer/{id}")
    public CustomerEntity updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerRequest) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + id));
//        customer.setId(customerRequest.getCustomer().getId());
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setDob(customerRequest.getDob());
       return customerRepository.save(customer);
    }
//    public List<CustomerDTO> findByName(@RequestParam Optional<String> firstName){
//    @ResponseBody

    @PostMapping("/customer/search")
    public List<CustomerDTO> findByName(@RequestBody CustomerDTO request){
        List<CustomerDTO> allCustomers = new ArrayList<CustomerDTO>();
        List<CustomerEntity> allFilteredEntities = new ArrayList<CustomerEntity>();

        List<CustomerEntity> byFirstName = customerRepository.findAllByFirstName(request.getFirstName());
        allFilteredEntities.addAll(byFirstName);

        List<CustomerEntity> byLastName = customerRepository.findAllByFirstName(request.getLastName());
        allFilteredEntities.addAll(byLastName);

        List<CustomerEntity> byDOB = customerRepository.findAllByFirstName(request.getDob());
        allFilteredEntities.addAll(byDOB);


        for (CustomerEntity item:allFilteredEntities) {
            allCustomers.add(this.convertToDto(item));
        }
        return  allCustomers;
    }

}
