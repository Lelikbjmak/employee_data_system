package com.innowise.employeedatasystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EmployeeDataSystemApplication {

    public static void main(String[] args) {
        log.info("Starting application: {}", EmployeeDataSystemApplication.class.getName());
        SpringApplication.run(EmployeeDataSystemApplication.class);
        log.debug("Starting my application in debug with {} args", args.length);
        log.info("Starting my application with {} args.", args.length);
    }
}
