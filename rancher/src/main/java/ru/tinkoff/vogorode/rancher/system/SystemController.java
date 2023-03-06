package ru.tinkoff.vogorode.rancher.system;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {

    private final SystemService systemService;

    /**
     * Checks liveness
     */
    @GetMapping("/liveness")
    public void getLiveness() {
    }

    /**
     * Checks readiness
     *
     * @return response with meta-information about a readiness of service
     */
    @GetMapping("/readiness")
    public Map.Entry<String, String> getReadiness() {
        return systemService.getReadiness();
    }
}
