package com.eraykalkan.server;

import com.eraykalkan.model.TransferRequest;
import com.eraykalkan.model.TransferResponse;
import com.eraykalkan.model.TransferServiceGrpc;
import io.grpc.stub.StreamObserver;

public class TransferService  extends TransferServiceGrpc.TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferStreamingRequest(responseObserver);
    }
}
