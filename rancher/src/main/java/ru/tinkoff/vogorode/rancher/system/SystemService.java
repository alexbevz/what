package ru.tinkoff.vogorode.rancher.system;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SystemService {

    private final BuildProperties buildProperties;


    /**
     * Getting readiness
     *
     * @return Map.Entry with meta-information about a readiness of service
     */
    public Map.Entry<String, String> getReadiness() {
        String nameService = buildProperties.getName();
        String statusService = StatusService.OK.name();
        return Map.entry(nameService, statusService);
    }
}
