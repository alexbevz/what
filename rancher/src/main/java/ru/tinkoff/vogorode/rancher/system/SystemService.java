package ru.tinkoff.vogorode.rancher.system;

import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class SystemService {

    private final BuildProperties buildProperties;

    private final ManagedChannel managedChannel;


    /**
     * Getting readiness
     *
     * @return Map.Entry with meta-information about a readiness of service
     */
    public Map.Entry<String, String> getReadiness() {
        String nameService = buildProperties.getName();
        String statusService = managedChannel.getState(true)
                .name();
        return Map.entry(nameService, statusService);
    }
}
