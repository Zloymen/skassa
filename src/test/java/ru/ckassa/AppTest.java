package ru.ckassa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testError() throws Exception{
        mockMvc.perform(get("/user/error/")).andDo(print()).andExpect(status().isOk())
                .andDo(document("error", responseFields(
                        fieldWithPath("message").description("message error."),
                        fieldWithPath("uuid").description("uuid for logging backend.")
                        )));
    }

    @Test
    public void getUser() throws Exception {
        this.mockMvc.perform(get("/user/")).andExpect(status().isOk())
                .andDo(document("get_user"));
    }

    @Test
    public void createUser() throws Exception {
        this.mockMvc.perform(post("/user/")).andExpect(status().isCreated())
                .andDo(document("create_user"));
    }
}
