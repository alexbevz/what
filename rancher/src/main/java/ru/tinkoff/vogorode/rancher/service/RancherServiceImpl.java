package ru.tinkoff.vogorode.rancher.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.info.BuildProperties;
import ru.tinkoff.vogorode.rancher.RancherServiceGrpc;
import ru.tinkoff.vogorode.rancher.ReadinessResponse;
import ru.tinkoff.vogorode.rancher.VersionResponse;
import ru.tinkoff.vogorode.rancher.system.StatusService;

@GrpcService
@RequiredArgsConstructor
public class RancherServiceImpl extends RancherServiceGrpc.RancherServiceImplBase {

    private final BuildProperties buildProperties;


    /**
     * gRPC method to share information about current service
     */
    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {

        String nameService = buildProperties.getName();

        VersionResponse versionResponse = VersionResponse.newBuilder().setArtifact(buildProperties.getArtifact()).setName(nameService).setGroup(buildProperties.getGroup()).setVersion(buildProperties.getVersion()).build();

        responseObserver.onNext(versionResponse);
        responseObserver.onCompleted();
    }

    /**
     * gRPC method to check a readiness of service
     */
    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        String statusService = StatusService.OK.name();
        ReadinessResponse readinessResponse = ReadinessResponse.newBuilder().setStatus(statusService).build();

        responseObserver.onNext(readinessResponse);
        responseObserver.onCompleted();
    }
}
