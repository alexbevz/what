package ru.tinkoff.vogorode.landscape.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.vogorode.landscape.system.dto.StatusServiceDto;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;


    /**
     * Checks liveness
     *
     * @return ResponseEntity with HttpStatus OK
     */
    @GetMapping("/liveness")
    public ResponseEntity<Void> getLiveness() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Checks readiness
     *
     * @return ResponseEntity with meta-information about a readiness of service
     */
    @GetMapping("/readiness")
    public ResponseEntity<Map<String, String>> getReadiness() {
        Map<String, String> readiness = systemService.getReadiness();
        return new ResponseEntity<>(readiness, HttpStatus.OK);
    }

    /**
     * Checks readiness
     *
     * @return ResponseEntity with information about general status of service
     */
    @GetMapping("/general-status")
    public ResponseEntity<Map<String, List<StatusServiceDto>>> getGeneralStatus() {
        Map<String, List<StatusServiceDto>> generalStatus = systemService.getGeneralStatus();
        return new ResponseEntity<>(generalStatus, HttpStatus.OK);
    }
}
