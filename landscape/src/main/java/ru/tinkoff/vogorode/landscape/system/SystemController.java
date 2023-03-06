package ru.tinkoff.vogorode.landscape.system;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.vogorode.landscape.system.response.StatusServiceResponse;

import java.util.List;
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
     * @return ResponseEntity with meta-information about a readiness of service
     */
    @GetMapping("/readiness")
    public Map.Entry<String, String> getReadiness() {
        return systemService.getReadiness();
    }

    /**
     * Checks readiness
     *
     * @return ResponseEntity with information about general status of service
     */
    @GetMapping("/general-status")
    public Map<String, List<StatusServiceResponse>> getGeneralStatus() {
        return systemService.getGeneralStatus();
    }
}
