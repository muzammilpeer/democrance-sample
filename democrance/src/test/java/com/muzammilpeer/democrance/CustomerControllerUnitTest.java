package com.muzammilpeer.democrance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.muzammilpeer.democrance.controller.CustomerController;
import com.muzammilpeer.democrance.dto.CustomerDTO;
import com.muzammilpeer.democrance.entity.CustomerEntity;
import com.muzammilpeer.democrance.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class CustomerControllerUnitTest
{
    @InjectMocks
    CustomerController employeeController;

    @Mock
    CustomerRepository employeeRepository;

    @Test
    public void testAddEmployee()
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        //create sample customer to check db insertion get success or fail
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFirstName("firstname2");
        customerEntity.setLastName("lastname");
        customerEntity.setDob("25-06-1993");
        when(employeeRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        CustomerDTO employeeToAdd = employeeController.convertToDto(customerEntity);
        ResponseEntity<Object> responseEntity = null;
        responseEntity = employeeController.createCustomer(employeeToAdd);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        //if 500 then it failed to insert record
//        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
    }


}