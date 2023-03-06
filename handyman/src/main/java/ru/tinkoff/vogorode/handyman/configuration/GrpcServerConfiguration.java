package ru.tinkoff.vogorode.handyman.configuration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GrpcServerConfiguration {

    private final GrpcServerProperties grpcServerProperties;

    @Bean
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder
                .forAddress(grpcServerProperties.getAddress(), grpcServerProperties.getPort())
                .usePlaintext()
                .build();
    }
}
