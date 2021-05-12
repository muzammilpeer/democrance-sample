package com.muzammilpeer.democrance;

import com.muzammilpeer.democrance.entity.PolicyStateEntity;
import com.muzammilpeer.democrance.repository.PolicyStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.List;

public class ServletInitializer extends SpringBootServletInitializer {



    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemocranceApplication.class);
    }

}
