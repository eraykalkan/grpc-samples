package com.eraykalkan.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(3336)
                .addService(new TransferService())
                .build();

        server.start();

        server.awaitTermination();

    }

}
