package com.eraykalkan.server;

import com.eraykalkan.model.BankServiceGrpc;
import com.eraykalkan.model.Money;
import com.eraykalkan.model.WithDrawRequest;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void withdraw(WithDrawRequest request, StreamObserver<Money> responseObserver) {

        int amount = request.getAmount();
        int balance = 150;

        // for amount value of 50, the server will stream 5 responses
        // if balance is not enough, error is thrown

        if(balance < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("Not enough money. You have only " + balance);
            responseObserver.onError(status.asRuntimeException());
            return;
        }

        for (int i = 0; i < (amount / 10); i++) {
            Money money = Money.newBuilder().setValue(10).build();
            responseObserver.onNext(money);
            balance = balance - 10;
        }

        responseObserver.onCompleted();
    }
}
