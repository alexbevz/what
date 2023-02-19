package ru.tinkoff.vogorode.rancher;

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
class RancherApplicationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setUp() {
        // set a value RANCHER_PORT to 8093
        System.setProperty("RANCHER_PORT", "8093");
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
                {"RancherService": "OK"}""";

        response.andExpect(status().isOk())
                .andExpect(content().json(expectedContentResult));
    }

}
