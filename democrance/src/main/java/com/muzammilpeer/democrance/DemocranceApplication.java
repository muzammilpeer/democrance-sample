package com.muzammilpeer.democrance;

import com.muzammilpeer.democrance.entity.PolicyStateEntity;
import com.muzammilpeer.democrance.repository.PolicyStateRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DemocranceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemocranceApplication.class, args);
//        PreInitializeSetup setup = new PreInitializeSetup();
//        setup.run();
    }

    @Override
    public void run(String... args) throws Exception {

    }

    @Bean
    public ApplicationRunner initializer(PolicyStateRepository repository) {
        if (repository.findByStateName("new") == null) {
            //new/quoted/active
            PolicyStateEntity policyStateEntity1 = new PolicyStateEntity();
            policyStateEntity1.setStateName("new");

            PolicyStateEntity policyStateEntity2 = new PolicyStateEntity();
            policyStateEntity2.setStateName("quoted");

            PolicyStateEntity policyStateEntity3 = new PolicyStateEntity();
            policyStateEntity3.setStateName("active");

            return args -> repository.saveAll(Arrays.asList(
                    policyStateEntity1,policyStateEntity2,policyStateEntity3
            ));
        }else {
            return args -> repository.saveAll(Arrays.asList(

            ));
        }
   }
}
