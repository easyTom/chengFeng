package com.tom.cf;

import com.tom.cf.core.dao.config.SpringContextProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
@Import(SpringContextProvider.class)
public class FengChengApp {

    public static void main(String[] args) {
        SpringApplication.run(FengChengApp.class,args);
    }
}
