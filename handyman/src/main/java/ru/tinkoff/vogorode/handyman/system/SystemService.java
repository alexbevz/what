package ru.tinkoff.vogorode.handyman.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import ru.tinkoff.vogorode.handyman.CommonConstant;

import java.util.Map;

@Service
public class SystemService {

    @Autowired
    private BuildProperties buildProperties;


    /**
     * Getting readiness
     *
     * @return Map with meta-information about a readiness of system
     */
    public Map<String, String> getReadiness() {
        String nameService = buildProperties.getName() + CommonConstant.SERVICE;
        return Map.of(nameService, CommonConstant.STATUS_APPLICATION);
    }
}
