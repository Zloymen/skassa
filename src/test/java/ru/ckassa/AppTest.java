package ru.ckassa;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.ckassa.dto.CreateUserDto;
import ru.ckassa.dto.UpdateUserDto;
import ru.ckassa.entity.User;
import ru.ckassa.serialize.LocalDateDeserializer;
import ru.ckassa.serialize.LocalDateSerializer;
import ru.ckassa.serialize.LocalDateTimeDeserializer;
import ru.ckassa.serialize.LocalDateTimeSerializer;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = App.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class AppTest {

    private ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Value("${test-task.date-format}")
    private String dateFormat;

    @PostConstruct
    public void postLoad(){
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
        this.mapper = objectMapper;
    }

    @Test
    public void testError() throws Exception{
        mockMvc.perform(get("/user/error/")).andDo(print()).andExpect(status().is5xxServerError())
                .andDo(document("error", responseFields(
                        fieldWithPath("message").description("message error."),
                        fieldWithPath("uuid").description("uuid for logging backend.")
                        )));
    }

    @Test
    public void getUser() throws Exception {
        this.mockMvc.perform(get("/user/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(document("get-user", responseFields(
                        fieldWithPath("[].id").description("id"),
                        fieldWithPath("[].login").description("username"),
                        fieldWithPath("[].dob").description("date of birth(" + dateFormat +")"),
                        fieldWithPath("[].key").description("sesurity key"),
                        fieldWithPath("[].name").description("name"),
                        fieldWithPath("[].email").description("email"),
                        fieldWithPath("[].phone").description("phone"),
                        fieldWithPath("[].created").description("date created(Timestamp)"),
                        fieldWithPath("[].deleted").description("date deleted(Timestamp)").optional()
                )));
    }

    @Test
    public void createUser() throws Exception {
        CreateUserDto dto = new CreateUserDto();

        dto.setDob(LocalDate.now());
        dto.setEmail("test@test.com");
        dto.setKey(UUID.randomUUID());
        dto.setLogin("login" + System.currentTimeMillis());
        dto.setName(dto.getLogin());
        dto.setPhone(String.valueOf(System.currentTimeMillis()));

        String param = mapper.writeValueAsString(dto);
        this.mockMvc.perform(post("/user/").contentType(MediaType.APPLICATION_JSON).content(param)).andExpect(status().isCreated())
                .andDo(document("create-user",  requestFields(
                        fieldWithPath("login").description("username"),
                        fieldWithPath("dob").description("date of birth(" + dateFormat +")"),
                        fieldWithPath("key").description("sesurity key"),
                        fieldWithPath("name").description("name"),
                        fieldWithPath("email").description("email"),
                        fieldWithPath("phone").description("phone")
                )));
    }

    @Test
    public void updateUser() throws Exception {

        CreateUserDto dto = new CreateUserDto();

        dto.setDob(LocalDate.now());
        dto.setEmail("test@test.com");
        dto.setKey(UUID.randomUUID());
        dto.setLogin("login" + System.currentTimeMillis());
        dto.setName(dto.getLogin());
        dto.setPhone(String.valueOf(System.currentTimeMillis()));

        String param = mapper.writeValueAsString(dto);
        MvcResult result = this.mockMvc.perform(post("/user/").contentType(MediaType.APPLICATION_JSON).content(param)).andExpect(status().isCreated()).andReturn();
        String content = result.getResponse().getContentAsString();

        UpdateUserDto updateDto = new UpdateUserDto();

        updateDto.setId(Long.valueOf(content));
        updateDto.setDob(LocalDate.now());
        updateDto.setEmail("test2@test2.com");
        updateDto.setLogin("login" + System.currentTimeMillis());
        updateDto.setName(dto.getLogin());
        updateDto.setPhone(String.valueOf(System.currentTimeMillis()));

        param = mapper.writeValueAsString(updateDto);
        this.mockMvc.perform(put("/user/").contentType(MediaType.APPLICATION_JSON).content(param)).andExpect(status().isOk())
                .andDo(document("update-user",
                        requestFields(
                                fieldWithPath("id").description("user id"),
                                fieldWithPath("login").description("username"),
                                fieldWithPath("dob").description("date of birth(" + dateFormat +")"),
                                fieldWithPath("name").description("name"),
                                fieldWithPath("email").description("email"),
                                fieldWithPath("phone").description("phone")
                        )));
    }

    @Test
    public void deleteUser() throws Exception {

        CreateUserDto dto = new CreateUserDto();

        dto.setDob(LocalDate.now());
        dto.setEmail("test@test.com");
        dto.setKey(UUID.randomUUID());
        dto.setLogin("login" + System.currentTimeMillis());
        dto.setName(dto.getLogin());
        dto.setPhone(String.valueOf(System.currentTimeMillis()));

        String param = mapper.writeValueAsString(dto);
        MvcResult result = this.mockMvc.perform(post("/user/").contentType(MediaType.APPLICATION_JSON).content(param)).andExpect(status().isCreated()).andReturn();
        String content = result.getResponse().getContentAsString();

        this.mockMvc.perform(delete("/user/{id}" , Long.valueOf(content)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(document("deleted-user", pathParameters(
                        parameterWithName("id").description("User id")
                )));
    }

    @Test
    public void getUserById() throws Exception {

        CreateUserDto dto = new CreateUserDto();

        dto.setDob(LocalDate.now());
        dto.setEmail("test@test.com");
        dto.setKey(UUID.randomUUID());
        dto.setLogin("login" + System.currentTimeMillis());
        dto.setName(dto.getLogin());
        dto.setPhone(String.valueOf(System.currentTimeMillis()));

        String param = mapper.writeValueAsString(dto);
        MvcResult result = this.mockMvc.perform(post("/user/").contentType(MediaType.APPLICATION_JSON).content(param)).andExpect(status().isCreated()).andReturn();
        String content = result.getResponse().getContentAsString();

        MvcResult resultGet =  this.mockMvc.perform(get("/user/{id}", Long.valueOf(content)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andDo(document("get-user-by-id",
                        pathParameters(
                                parameterWithName("id").description("User id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("id"),
                                fieldWithPath("login").description("username"),
                                fieldWithPath("dob").description("date of birth(" + dateFormat +")"),
                                fieldWithPath("key").description("sesurity key"),
                                fieldWithPath("name").description("name"),
                                fieldWithPath("email").description("email"),
                                fieldWithPath("phone").description("phone"),
                                fieldWithPath("created").description("date created(Timestamp)"),
                                fieldWithPath("deleted").description("date deleted(Timestamp)").optional()
                        ))).andReturn();

        String strUser = resultGet.getResponse().getContentAsString();

        User user = mapper.readValue(strUser, User.class);

        Assert.assertEquals(user.getEmail(), dto.getEmail());
        Assert.assertEquals(user.getLogin(), dto.getLogin());
    }
}
