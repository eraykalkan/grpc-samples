package com.eraykalkan.server;

import com.eraykalkan.model.Money;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

// Async client response object
public class MoneyStreamingResponse implements StreamObserver<Money> {

    private CountDownLatch countDownLatch;

    public MoneyStreamingResponse(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onNext(Money money) {
        System.out.println(
                "Received async: " + money.getValue()
        );
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println(
                throwable.getMessage()
        );
        countDownLatch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println(
                "Server has completed the request"
        );
        countDownLatch.countDown();
    }
}
