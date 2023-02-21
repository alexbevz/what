package ru.tinkoff.vogorode.handyman.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import ru.tinkoff.vogorode.handyman.CommonConstant;
import ru.tinkoff.vogorode.handyman.HandymanServiceGrpc;
import ru.tinkoff.vogorode.handyman.ReadinessResponse;
import ru.tinkoff.vogorode.handyman.VersionResponse;


@GrpcService
public class HandymanServiceImpl extends HandymanServiceGrpc.HandymanServiceImplBase {

    @Autowired
    private BuildProperties buildProperties;

    /**
     * gRPC method to share information about current service
     */
    @Override
    public void getVersion(Empty request, StreamObserver<VersionResponse> responseObserver) {

        String nameApplication = buildProperties.getName() + CommonConstant.SERVICE;

        VersionResponse versionResponse = VersionResponse.newBuilder()
                .setArtifact(buildProperties.getArtifact())
                .setName(nameApplication)
                .setGroup(buildProperties.getGroup())
                .setVersion(buildProperties.getVersion())
                .build();

        responseObserver.onNext(versionResponse);
        responseObserver.onCompleted();
    }

    /**
     * gRPC method to check a readiness of service
     */
    @Override
    public void getReadiness(Empty request, StreamObserver<ReadinessResponse> responseObserver) {
        ReadinessResponse readinessResponse = ReadinessResponse.newBuilder()
                .setStatus(CommonConstant.STATUS_APPLICATION)
                .build();

        responseObserver.onNext(readinessResponse);
        responseObserver.onCompleted();
    }
}
