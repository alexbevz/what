package ru.tinkoff.vogorode.rancher.service;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import ru.tinkoff.vogorode.rancher.CommonConstant;
import ru.tinkoff.vogorode.rancher.RancherServiceGrpc;
import ru.tinkoff.vogorode.rancher.ReadinessResponse;
import ru.tinkoff.vogorode.rancher.VersionResponse;

@GrpcService
public class RancherServiceImpl extends RancherServiceGrpc.RancherServiceImplBase {

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
