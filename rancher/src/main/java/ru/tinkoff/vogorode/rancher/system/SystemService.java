package ru.tinkoff.vogorode.rancher.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import ru.tinkoff.vogorode.rancher.CommonConstant;

import java.util.Map;

@Service
public class SystemService {

    @Autowired
    private BuildProperties buildProperties;


    /**
     * Getting readiness
     *
     * @return Map with meta-information about a readiness of service
     */
    public Map<String, String> getReadiness() {
        String nameService = buildProperties.getName() + CommonConstant.SERVICE;
        return Map.of(nameService, CommonConstant.STATUS_APPLICATION);
    }
}
