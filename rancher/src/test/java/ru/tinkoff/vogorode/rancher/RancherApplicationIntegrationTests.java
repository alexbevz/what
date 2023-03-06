package ru.tinkoff.vogorode.rancher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.net.ServerSocket;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RancherApplicationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setUp() throws IOException {
        // set a value SERVICE_PORT to random port
        System.setProperty("SERVICE_PORT", String.valueOf(getFreePort()));

        // set a value GRPC_PORT to random port
        System.setProperty("GRPC_PORT", String.valueOf(getFreePort()));
    }

    private static int getFreePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            // get a random available port
            return serverSocket.getLocalPort();
        }

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
