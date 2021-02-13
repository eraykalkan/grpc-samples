package com.eraykalkan.client;

import com.eraykalkan.model.Balance;
import com.eraykalkan.model.BalanceCheckRequest;
import com.eraykalkan.model.BankServiceGrpc;
import com.eraykalkan.model.WithDrawRequest;
import com.eraykalkan.server.MoneyStreamingResponse;
import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankServiceTest {

    private BankServiceGrpc.BankServiceBlockingStub bankServiceBlockingStub;
    private BankServiceGrpc.BankServiceStub bankServiceStub;

    private MoneyStreamingResponse moneyStreamingResponse;
    CountDownLatch latch;

    @BeforeAll
    public void setup() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost",3334)
                .usePlaintext()
                .build();
        this.bankServiceBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
        this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);

        latch = new CountDownLatch(1);
        this.moneyStreamingResponse = new MoneyStreamingResponse(latch);
    }

    // Blocking Client
    @Test
    void withdrawTest() {
        WithDrawRequest withDrawRequest = WithDrawRequest.newBuilder()
                .setAccountNumber(1).setAmonunt(50).build();
        this.bankServiceBlockingStub.withdraw(withDrawRequest)
                .forEachRemaining(money -> System.out.println("Received : " + money.getValue()));
    }

    // Async Client
    @Test
    void withDrawAsyncTest() throws InterruptedException {

        WithDrawRequest withDrawRequest = WithDrawRequest.newBuilder()
                .setAccountNumber(1).setAmonunt(50).build();
        this.bankServiceStub.withdraw(withDrawRequest,moneyStreamingResponse);
        //Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        latch.await();
    }

}
