package ru.tinkoff.vogorode.rancher.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import ru.tinkoff.vogorode.rancher.RancherServiceGrpc;
import ru.tinkoff.vogorode.rancher.ReadinessResponse;
import ru.tinkoff.vogorode.rancher.VersionResponse;

@GrpcService
public class RancherServiceImpl extends RancherServiceGrpc.RancherServiceImplBase {

    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {
        VersionResponse versionResponse = VersionResponse.newBuilder()
                .setArtifact("eqwe")
                .setName("13123")
                .setGroup("qeqwe")
                .setVersion("123")
                .build();

        responseObserver.onNext(versionResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        ReadinessResponse readinessResponse = ReadinessResponse.newBuilder()
                .setStatus("OK")
                .build();

        responseObserver.onNext(readinessResponse);
        responseObserver.onCompleted();
    }
}
