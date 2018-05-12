package ru.ckassa;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import ru.ckassa.filter.LogFilter;
import ru.ckassa.serialize.LocalDateDeserializer;
import ru.ckassa.serialize.LocalDateSerializer;
import ru.ckassa.serialize.LocalDateTimeDeserializer;
import ru.ckassa.serialize.LocalDateTimeSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.ckassa.controller", "ru.ckassa.service" })
@EnableJpaRepositories("ru.ckassa.dao")
@EnableAutoConfiguration
@EntityScan(
        basePackageClasses = { App.class, Jsr310JpaConverters.class }
)
@PropertySource( "classpath:application.properties")
public class App {

    @Value("${test-task.date-format}")
    private String dateFormat;

    @Value("${test-task.log-id}")
    private String logId;


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public FilterRegistrationBean commonFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/*");
        registration.setName("CommonFilter");
        registration.setOrder(2);
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeHeaders(false);
        registration.setFilter(loggingFilter);
        return registration;
    }

    @Bean
    public FilterRegistrationBean logFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.addUrlPatterns("/*");
        registration.setName("LogFilter");
        registration.setOrder(1);
        LogFilter logFilter = new LogFilter(logId);
        registration.setFilter(logFilter);
        return registration;
    }

    @Bean
    @Primary
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(formatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(formatter));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}