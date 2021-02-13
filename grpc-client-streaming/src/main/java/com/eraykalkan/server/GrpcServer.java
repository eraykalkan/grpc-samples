package com.eraykalkan.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        Server server = ServerBuilder.forPort(3335)
                .addService(new BankService())
                .build();

        server.start();

        server.awaitTermination();

    }

}
