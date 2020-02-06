package org.interlink.grouporder;

import org.interlink.grouporder.core.repository.GroupOrderRepository;
import org.interlink.grouporder.core.service.GroupOrderService;
import org.interlink.grouporder.core.service.MemberOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MainApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        final SpringApplication application = new SpringApplication(MainApplication.class);
        application.run(args);
    }
}