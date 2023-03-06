package ru.tinkoff.vogorode.landscape.system;

import com.google.protobuf.Empty;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.tinkoff.vogorode.handyman.HandymanServiceGrpc;
import ru.tinkoff.vogorode.landscape.ReadinessResponse;
import ru.tinkoff.vogorode.landscape.VersionResponse;
import ru.tinkoff.vogorode.landscape.system.response.StatusServiceResponse;
import ru.tinkoff.vogorode.rancher.RancherServiceGrpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SystemService {

    private final BuildProperties buildProperties;
    private final Environment environment;
    @GrpcClient("RancherService")
    private RancherServiceGrpc.RancherServiceBlockingStub rancherStub;
    @GrpcClient("HandymanService")
    private HandymanServiceGrpc.HandymanServiceBlockingStub handymanStub;

    /**
     * Getting readiness
     *
     * @return Map with meta-information about a readiness of service
     */
    public Map.Entry<String, String> getReadiness() {
        String nameService = buildProperties.getName();
        String statusService = StatusService.OK.name();

        return Map.entry(nameService, statusService);
    }

    //TODO: change the architecture of proto files

    /**
     * Getting general status from dependency services
     *
     * @return Map with information about a general status of system
     */
    public Map<String, List<StatusServiceResponse>> getGeneralStatus() {
        Map<String, List<StatusServiceResponse>> generalStatus = new HashMap<>();
        addStatusRancherServiceResponse(generalStatus);
        addStatusHandymanServiceResponse(generalStatus);
        return generalStatus;
    }

    private void addStatusRancherServiceResponse(Map<String, List<StatusServiceResponse>> generalStatus) {
        ReadinessResponse rancherReadiness = rancherStub.getReadiness(Empty.newBuilder().build());
        VersionResponse rancherVersion = rancherStub.getVersion(Empty.newBuilder().build());

        String rancherHost = getGrpcHost(rancherVersion.getArtifact());
        List<StatusServiceResponse> statusRancherServiceResponse = List.of(toStatusServiceResponse(
                rancherHost,
                rancherReadiness,
                rancherVersion
        ));
        generalStatus.put(rancherVersion.getName(), statusRancherServiceResponse);
    }

    private void addStatusHandymanServiceResponse(Map<String, List<StatusServiceResponse>> generalStatus) {
        ReadinessResponse handymanReadiness = rancherStub.getReadiness(Empty.newBuilder().build());
        VersionResponse handymanVersion = handymanStub.getVersion(Empty.newBuilder().build());
        String handymanHost = getGrpcHost(handymanVersion.getArtifact());
        List<StatusServiceResponse> statusHandymanResponse = List.of(toStatusServiceResponse(
                handymanHost,
                handymanReadiness,
                handymanVersion
        ));
        generalStatus.put(handymanVersion.getName(), statusHandymanResponse);
    }

    private String getGrpcHost(String nameService) {
        return environment.getProperty("grpc.client." + nameService + ".address");
    }

    private StatusServiceResponse toStatusServiceResponse(
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
