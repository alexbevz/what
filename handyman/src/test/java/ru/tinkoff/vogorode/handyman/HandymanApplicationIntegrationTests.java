package ru.tinkoff.vogorode.handyman;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HandymanApplicationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setUp() {
        // set a value HANDYMAN_PORT to 8091
        System.setProperty("HANDYMAN_PORT", "8091");

        // set a value GRPC_PORT to 9101
        System.setProperty("GRPC_PORT", "9101");
    }

    @Test
    void testGetLiveness() throws Exception {
        final String livenessUrl = "/system/liveness";

        ResultActions response = mockMvc.perform(get(livenessUrl));

        response.andExpect(status().isOk());
    }

    @Test
    void getReadiness() throws Exception {
        final String readinessUrl = "/system/readiness";

        ResultActions response = mockMvc.perform(get(readinessUrl));

        final String expectedContentResult = """
                {"handymanService": "OK"}""";

        response.andExpect(status().isOk())
                .andExpect(content().json(expectedContentResult));
    }

}
