package ru.tinkoff.vogorode.landscape.system;

import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import ru.tinkoff.vogorode.common.proto.StatusServiceGrpc;
import ru.tinkoff.vogorode.common.proto.message.ReadinessResponse;
import ru.tinkoff.vogorode.common.proto.message.VersionResponse;
import ru.tinkoff.vogorode.landscape.system.response.StatusServiceResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SystemService {

    private final BuildProperties buildProperties;

    @GrpcClient("RancherService")
    private StatusServiceGrpc.StatusServiceBlockingStub rancherStatusServiceStub;

    @GrpcClient("HandymanService")
    private StatusServiceGrpc.StatusServiceBlockingStub handymanStatusServiceStub;


    /**
     * Getting readiness
     *
     * @return Map with meta-information about a readiness of service
     */
    public Map.Entry<String, String> getReadiness() {
        String nameService = buildProperties.getName();
        //TODO: to check by all dependency of services
        String statusService = ServiceStatus.OK.name();
        return Map.entry(nameService, statusService);
    }

    /**
     * Getting general status from dependency services
     *
     * @return Map with information about a general status of system
     */
    public Map<String, List<StatusServiceResponse>> getGeneralStatus() {
        Map<String, List<StatusServiceResponse>> generalStatus = new HashMap<>();
        addStatusServiceToGeneralStatus(generalStatus, rancherStatusServiceStub);
        addStatusServiceToGeneralStatus(generalStatus, handymanStatusServiceStub);
        return generalStatus;
    }

    private void addStatusServiceToGeneralStatus(
            Map<String, List<StatusServiceResponse>> generalStatus,
            StatusServiceGrpc.StatusServiceBlockingStub statusServiceStub
    ) {
        ReadinessResponse readinessResponse = statusServiceStub.getReadiness(Empty.getDefaultInstance());
        VersionResponse versionResponse = statusServiceStub.getVersion(Empty.getDefaultInstance());
        String serviceHost = getServiceHost(statusServiceStub);

        List<StatusServiceResponse> statusServiceResponse = List.of(
                getStatusServiceResponse(
                        serviceHost,
                        readinessResponse,
                        versionResponse
                )
        );

        generalStatus.put(versionResponse.getName(), statusServiceResponse);
    }

    private String getServiceHost(StatusServiceGrpc.StatusServiceBlockingStub statusServiceStub) {
        return statusServiceStub.getChannel()
                .authority();
    }

    private StatusServiceResponse getStatusServiceResponse(
            String host,
            ReadinessResponse readinessResponse,
            VersionResponse versionResponse
    ) {
        return StatusServiceResponse.builder()
                .host(host)
                .status(readinessResponse.getStatus())
                .artifact(versionResponse.getArtifact())
                .name(versionResponse.getName())
                .group(versionResponse.getGroup())
                .version(versionResponse.getVersion())
                .build();
    }
}
