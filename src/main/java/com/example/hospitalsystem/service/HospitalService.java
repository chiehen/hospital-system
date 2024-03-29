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
import com.example.hospitalsystem.Hospital;
import com.example.hospitalsystem.HospitalRepository;
import com.example.hospitalsystem.Patient;
import com.example.hospitalsystem.PatientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@GrpcService
public class HospitalService extends HospitalServiceGrpc.HospitalServiceImplBase {

    @Autowired
    private HospitalRepository hRepository;

    @Autowired
    private PatientRepository pRepository;

    private static final Logger logger = LoggerFactory.getLogger(HospitalService.class);

    @Override
    public void createHospital(HospitalProto.HospitalData request,
            io.grpc.stub.StreamObserver<HospitalProto.ID> responseObserver) {

        logger.info(String.format("Create Hospital %1s %1s", request.getName(), request.getAddress()));

        Hospital h = new Hospital(request.getName(), request.getAddress());
        hRepository.save(h);

        HospitalProto.ID resultResponse = HospitalProto.ID.newBuilder()
                .setId(h.getId())
                .build();
        responseObserver.onNext(resultResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void modifyHospital(HospitalProto.Hospital request,
            io.grpc.stub.StreamObserver<HospitalProto.ID> responseObserver) {
        logger.info(String.format("Modify hospital %1s: %1s", request.getName(), request.getAddress()));
        Optional<Hospital> result = hRepository.findById(request.getId());

        result.ifPresent(h -> {
            h.setName(request.getName());
            h.setAddress(request.getAddress());
            hRepository.save(h);
        });
        HospitalProto.ID resultResponse = HospitalProto.ID.newBuilder()
                .setId(request.getId())
                .build();
        responseObserver.onNext(resultResponse);
        responseObserver.onCompleted();
    }

    /**
     */
    @Override
    public void deleteHospital(HospitalProto.ID request,
            io.grpc.stub.StreamObserver<HospitalProto.ID> responseObserver) {
        logger.info(String.format("Delete hospital %1s", request.getId()));

        hRepository.deleteById(request.getId());

        HospitalProto.ID resultResponse = HospitalProto.ID.newBuilder()
                .setId(request.getId())
                .build();
        responseObserver.onNext(resultResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void fakeOp(Int64Value request,
            io.grpc.stub.StreamObserver<Int64Value> responseObserver) {
        logger.info(String.format("Fake Op"));

        Int64Value a = Int64Value.newBuilder().setValue(123456789L).build();
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

    @Override
    public void listHospital(Empty request,
            io.grpc.stub.StreamObserver<HospitalProto.Hospitals> responseObserver) {
        logger.info(String.format("List Hospitals"));

        Iterable<Hospital> found = hRepository.findAll();
        Stream<Hospital> found_stream = StreamSupport.stream(found.spliterator(), false);

        List<HospitalProto.Hospital> hospitals = found_stream
                .map(h -> HospitalProto.Hospital.newBuilder()
                        .setId(h.getId()).setName(h.getName()).setAddress(h.getAddress()).build())
                .collect(Collectors.toList()); // Collecting into a new list
        HospitalProto.Hospitals a = HospitalProto.Hospitals.newBuilder().addAllHospitals(hospitals).build();
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

    @Override
    public void createPatient(HospitalProto.PatientData request,
            io.grpc.stub.StreamObserver<HospitalProto.ID> responseObserver) {
        logger.info(String.format("Create Patient %1s %1s", request.getFirstName(), request.getLastName()));

        Patient p = new Patient(request.getFirstName(), request.getLastName(), request.getAge(), request.getSex());
        pRepository.save(p);

        HospitalProto.ID resultResponse = HospitalProto.ID.newBuilder()
                .setId(p.getId())
                .build();
        responseObserver.onNext(resultResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void modifyPatient(HospitalProto.Patient request,
            io.grpc.stub.StreamObserver<HospitalProto.ID> responseObserver) {
        logger.info(String.format("Modify Patient %1s %1s", request.getFirstName(), request.getLastName()));

        Optional<Patient> result = pRepository.findById(request.getId());

        result.ifPresent(p -> {
            p.setFirstName(request.getFirstName());
            p.setLastName(request.getLastName());
            p.setSex(request.getSex());
            p.setAge(request.getAge());
            pRepository.save(p);
        });

        HospitalProto.ID resultResponse = HospitalProto.ID.newBuilder()
                .setId(request.getId())
                .build();
        responseObserver.onNext(resultResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void deletePatient(HospitalProto.ID request,
            io.grpc.stub.StreamObserver<HospitalProto.ID> responseObserver) {
        logger.info(String.format("Delete Patient %1s", request.getId()));

        pRepository.deleteById(request.getId());

        HospitalProto.ID resultResponse = HospitalProto.ID.newBuilder()
                .setId(request.getId())
                .build();
        responseObserver.onNext(resultResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void registerPatientInHospital(HospitalProto.RegisterPatientRequest request,
            io.grpc.stub.StreamObserver<Empty> responseObserver) {
        Optional<Hospital> hospital = hRepository.findById(request.getHospitalId());
        Optional<Patient> patient = pRepository.findById(request.getPatientId());

        if (hospital.isPresent() && patient.isPresent()) {
            logger.info(String.format("register Patient %1s %1s", hospital.get().toString(), patient.get().toString()));
            
            hospital.get().addPatient(patient.get());
            hRepository.save(hospital.get());
        }

        Empty resultResponse = Empty.newBuilder().build();
        responseObserver.onNext(resultResponse);
        responseObserver.onCompleted();

    }

    @Override
    public void listPatientsInHospital(HospitalProto.ID request,
            io.grpc.stub.StreamObserver<HospitalProto.Patients> responseObserver) {
        logger.info(String.format("List Hospitals"));
        List<Patient> registedP = pRepository.findAllByRegisteredHospitals_Id(request.getId());
        List<HospitalProto.Patient> patients = registedP.stream()
                .map(p -> HospitalProto.Patient.newBuilder()
                        .setId(p.getId()).setFirstName(p.getFirstName())
                        .setLastName(p.getLastName()).setSex(p.getSex()).setAge(p.getAge())
                        .build())
                .collect(Collectors.toList());
        HospitalProto.Patients a = HospitalProto.Patients.newBuilder().addAllPatients(patients).build();
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

    @Override
    public void listHospitalsForPatient(HospitalProto.ID request,
            io.grpc.stub.StreamObserver<com.example.grpc.HospitalProto.Hospitals> responseObserver) {
        logger.info(String.format("List Hospitals for "));
        List<Hospital> registedH = hRepository.findAllByRegisteredPatients_Id(request.getId());

        List<HospitalProto.Hospital> hospitals = registedH.stream()
                .map(h -> HospitalProto.Hospital.newBuilder()
                        .setId(h.getId()).setName(h.getName()).setAddress(h.getAddress()).build())
                .collect(Collectors.toList()); // Collecting into a new list
        HospitalProto.Hospitals a = HospitalProto.Hospitals.newBuilder().addAllHospitals(hospitals).build();
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

}