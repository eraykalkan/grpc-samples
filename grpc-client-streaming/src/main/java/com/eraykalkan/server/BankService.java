package com.eraykalkan.server;

import com.eraykalkan.model.Balance;
import com.eraykalkan.model.BankServiceGrpc;
import com.eraykalkan.model.DepositRequest;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}
