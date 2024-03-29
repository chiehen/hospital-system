package com.example.hospitalsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.grpc.HospitalServiceGrpc;
import com.example.grpc.HospitalProto;
import com.example.hospitalsystem.HospitalRepository;

import java.util.List;

@GrpcService
public class HospitalService extends HospitalServiceGrpc.HospitalServiceImplBase {


    @Override
    public void createHospital(HospitalProto.Hospital request,
        StreamObserver<HospitalProto.Hospital> responseObserver) {

            HospitalProto.Hospital resultResponse = HospitalProto.Hospital.newBuilder()
                    .setId(request.getId())
                    .setName(request.getName())
                    .setAddress(request.getAddress())
                    .build();
            responseObserver.onNext(resultResponse);
            responseObserver.onCompleted();
    }

    public void fakeOp(Int64Value request,
    io.grpc.stub.StreamObserver<Int64Value> responseObserver) {
        
        Int64Value a = Int64Value.newBuilder().setValue(123456789L).build();
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }


    // @Override
    // public void createHospital(StringValue request, StreamObserver<AccountProto.Account> responseObserver) {
    //     AccountProto.Account a = repository.findByNumber(request.getValue());
    //     responseObserver.onNext(a);
    //     responseObserver.onCompleted();
    // }

    // @Override
    // public void findByCustomer(Int32Value request, StreamObserver<AccountProto.Accounts> responseObserver) {
    //     List<AccountProto.Account> accounts = repository.findByCustomer(request.getValue());
    //     AccountProto.Accounts a = AccountProto.Accounts.newBuilder().addAllAccount(accounts).build();
    //     responseObserver.onNext(a);
    //     responseObserver.onCompleted();
    // }

    // @Override
    // public void findAll(Empty request, StreamObserver<AccountProto.Accounts> responseObserver) {
    //     List<AccountProto.Account> accounts = repository.findAll();
    //     AccountProto.Accounts a = AccountProto.Accounts.newBuilder().addAllAccount(accounts).build();
    //     responseObserver.onNext(a);
    //     responseObserver.onCompleted();
    // }

    // @Override
    // public void addAccount(AccountProto.Account request, StreamObserver<AccountProto.Account> responseObserver) {
    //     AccountProto.Account a = repository.add(request.getCustomerId(), request.getNumber());
    //     responseObserver.onNext(a);
    //     responseObserver.onCompleted();
    // }
}