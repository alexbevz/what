package ru.tinkoff.vogorode.rancher.service;

import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.boot.info.BuildProperties;
import ru.tinkoff.vogorode.common.proto.StatusServiceGrpc;
import ru.tinkoff.vogorode.common.proto.message.ReadinessResponse;
import ru.tinkoff.vogorode.common.proto.message.VersionResponse;

@GrpcService
@RequiredArgsConstructor
public class StatusServiceImpl extends StatusServiceGrpc.StatusServiceImplBase {

    private final BuildProperties buildProperties;

    private final ManagedChannel managedChannel;


    /**
     * gRPC method to share information about current service
     */
    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {
        VersionResponse versionResponse = getVersionResponse();

        responseObserver.onNext(versionResponse);
        responseObserver.onCompleted();
    }

    /**
     * gRPC method to check a readiness of service
     */
    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        String statusService = managedChannel.getState(true)
                .name();
        ReadinessResponse readinessResponse = ReadinessResponse.newBuilder()
                .setStatus(statusService)
                .build();

        responseObserver.onNext(readinessResponse);
        responseObserver.onCompleted();
    }

    private VersionResponse getVersionResponse() {
        return VersionResponse.newBuilder()
                .setArtifact(buildProperties.getArtifact())
                .setName(buildProperties.getName())
                .setGroup(buildProperties.getGroup())
                .setVersion(buildProperties.getVersion())
                .build();
    }
}
