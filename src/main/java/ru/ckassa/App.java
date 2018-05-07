package ru.ckassa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.ckassa.filter.LogFilter;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.ckassa.controller", "ru.ckassa.service" })
@EnableJpaRepositories("ru.ckassa.dao")
@EnableAutoConfiguration
@EntityScan(
        basePackageClasses = { App.class, Jsr310JpaConverters.class }
)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    // Register Filter
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LogFilter());
        registration.addUrlPatterns("/service/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("LogFilter");
        registration.setOrder(1);
        return registration;
    }
}