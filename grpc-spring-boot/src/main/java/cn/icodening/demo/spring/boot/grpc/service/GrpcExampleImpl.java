package cn.icodening.demo.spring.boot.grpc.service;

import cn.icodening.demo.spring.boot.grpc.GrpcExampleGrpc;
import cn.icodening.demo.spring.boot.grpc.Message;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

/**
 * @author icodening
 * @date 2025.04.23
 */
@Service
public class GrpcExampleImpl extends GrpcExampleGrpc.GrpcExampleImplBase {

    @Override
    public void unary(Message request, StreamObserver<Message> responseObserver) {
        responseObserver.onNext(Message.newBuilder().setMessage("hello response servlet grpc").build());
        responseObserver.onCompleted();
    }
}
