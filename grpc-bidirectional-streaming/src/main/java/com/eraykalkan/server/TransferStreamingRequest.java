package com.eraykalkan.server;

import com.eraykalkan.model.Account;
import com.eraykalkan.model.TransferRequest;
import com.eraykalkan.model.TransferResponse;
import com.eraykalkan.model.TransferStatus;
import io.grpc.stub.StreamObserver;

public class TransferStreamingRequest implements StreamObserver<TransferRequest> {

    private StreamObserver<TransferResponse> transferResponseStreamObserver;

    public TransferStreamingRequest(StreamObserver<TransferResponse> transferResponseStreamObserver) {
        this.transferResponseStreamObserver = transferResponseStreamObserver;
    }

    @Override
    public void onNext(TransferRequest transferRequest) {

        int fromAccount = transferRequest.getFromAccount();
        int toAccount = transferRequest.getToAccount();
        int amount = transferRequest.getAmount();
        int fromAccountBalance = 200;
        int toAccountBalance = 30;
        TransferStatus status = TransferStatus.FAILED;

        if(fromAccountBalance >= amount && fromAccount!=toAccount) {
            fromAccountBalance -= amount;
            toAccountBalance += amount;
            status = TransferStatus.SUCCESS;
        }


        Account fromAccountInfo = Account.newBuilder().setAccountNumber(fromAccount).setAmount(fromAccountBalance).build();
        Account toAccountInfo = Account.newBuilder().setAccountNumber(toAccount).setAmount(toAccountBalance).build();

        TransferResponse transferResponse = TransferResponse.newBuilder()
                .setStatus(status)
                .addAccounts(fromAccountInfo)
                .addAccounts(toAccountInfo)
                .build();

        this.transferResponseStreamObserver.onNext(transferResponse);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        this.transferResponseStreamObserver.onCompleted();
    }
}
