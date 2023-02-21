package ru.tinkoff.vogorode.landscape.system;

import com.google.protobuf.Empty;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.tinkoff.vogorode.handyman.HandymanServiceGrpc;
import ru.tinkoff.vogorode.landscape.CommonConstant;
import ru.tinkoff.vogorode.landscape.ReadinessResponse;
import ru.tinkoff.vogorode.landscape.VersionResponse;
import ru.tinkoff.vogorode.landscape.system.dto.StatusServiceDto;
import ru.tinkoff.vogorode.rancher.RancherServiceGrpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemService {

    @GrpcClient("rancher")
    private RancherServiceGrpc.RancherServiceBlockingStub rancherStub;

    @GrpcClient("handyman")
    private HandymanServiceGrpc.HandymanServiceBlockingStub handymanStub;

    @Autowired
    private BuildProperties buildProperties;

    @Autowired
    private Environment environment;


    /**
     * Getting readiness
     *
     * @return Map with meta-information about a readiness of service
     */
    public Map<String, String> getReadiness() {
        String nameService = buildProperties.getName() + CommonConstant.SERVICE;
        return Map.of(nameService, CommonConstant.STATUS_APPLICATION);
    }

    //TODO: change the architecture of proto files

    /**
     * Getting general status from dependency services
     *
     * @return Map with information about a general status of system
     */
    public Map<String, List<StatusServiceDto>> getGeneralStatus() {
        Map<String, List<StatusServiceDto>> generalStatus = new HashMap<>();
        addStatusRancherServiceDto(generalStatus);
        addStatusHandymanServiceDto(generalStatus);
        return generalStatus;
    }

    private void addStatusRancherServiceDto(Map<String, List<StatusServiceDto>> generalStatus) {
        ReadinessResponse rancherReadiness = rancherStub.getReadiness(Empty.newBuilder().build());
        VersionResponse rancherVersion = rancherStub.getVersion(Empty.newBuilder().build());
        String rancherHost = getGrpcHost(rancherVersion.getArtifact());
        List<StatusServiceDto> statusRancherServiceDtos = List.of(toStatusServiceDto(
                rancherHost,
                rancherReadiness,
                rancherVersion
        ));
        generalStatus.put(rancherVersion.getName(), statusRancherServiceDtos);
    }

    private void addStatusHandymanServiceDto(Map<String, List<StatusServiceDto>> generalStatus) {
        ReadinessResponse handymanReadiness = rancherStub.getReadiness(Empty.newBuilder().build());
        VersionResponse handymanVersion = handymanStub.getVersion(Empty.newBuilder().build());
        String handymanHost = getGrpcHost(handymanVersion.getArtifact());
        List<StatusServiceDto> statusHandymanDtos = List.of(toStatusServiceDto(
                handymanHost,
                handymanReadiness,
                handymanVersion
        ));
        generalStatus.put(handymanVersion.getName(), statusHandymanDtos);
    }

    private String getGrpcHost(String nameService) {
        return environment.getProperty("grpc.client." + nameService + ".address");
    }

    private StatusServiceDto toStatusServiceDto(
            String host,
            ReadinessResponse readinessResponse,
            VersionResponse versionResponse
    ) {
        return StatusServiceDto.builder()
                .host(host)
                .status(readinessResponse.getStatus())
                .artifact(versionResponse.getArtifact())
                .name(versionResponse.getName())
                .group(versionResponse.getGroup())
                .version(versionResponse.getVersion())
                .build();
    }
}
