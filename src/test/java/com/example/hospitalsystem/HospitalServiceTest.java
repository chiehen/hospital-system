package com.example.hospitalsystem;


import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import com.example.grpc.HospitalServiceGrpc;
import com.example.grpc.HospitalProto;
import net.devh.boot.grpc.client.inject.GrpcClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

// @SpringBootTest(properties = {
//         "grpc.server.inProcessName=test",
//         "grpc.server.port=-1",
//         "grpc.client.inProcess.address=in-process:test"
// })
@RunWith(SpringRunner.class)
@DirtiesContext
public class HospitalServiceTest {

    // @GrpcClient("inProcess")
    // private HospitalServiceGrpc.HospitalServiceBlockingStub hospitalService;
    @Autowired
    private HospitalServiceGrpc.HospitalServiceBlockingStub hospitalService;
    


    @Test
    void shouldCreatePatient() {
        HospitalProto.PatientData a = HospitalProto.PatientData.newBuilder()
                .setLastName("Taylor")
                .setFirstName("Jack")
                .setAge(10)
                .setSex("F")
                .build();

        HospitalProto.ID id = hospitalService.createPatient(a);
        assertNotNull(id);
        assertNotEquals(0, id.getId());
        System.out.println("This is the preClass() method that runs one time before the clas");
    }

    @Test
    void shouldCreateHospital() {
        HospitalProto.HospitalData hData = HospitalProto.HospitalData.newBuilder()
                .setName("City")
                .setAddress("Apple Street")
                .build();

        HospitalProto.ID id = hospitalService.createHospital(hData);
        assertNotNull(id);
        assertNotEquals(0, id.getId());
    }

    @Test
    void shouldRegister() {
        HospitalProto.HospitalData hData = HospitalProto.HospitalData.newBuilder()
                .setName("City")
                .setAddress("Apple Street")
                .build();

        HospitalProto.PatientData pData = HospitalProto.PatientData.newBuilder()
                .setLastName("Taylor")
                .setFirstName("Jack")
                .setAge(10)
                .setSex("F")
                .build();

        
        HospitalProto.ID hospital_id = hospitalService.createHospital(hData);
        HospitalProto.ID patient_id = hospitalService.createPatient(pData);
        HospitalProto.RegisterPatientRequest reg = HospitalProto.RegisterPatientRequest.newBuilder()
                .setPatientId(patient_id.getId()).setHospitalId(hospital_id.getId())
                .build();
        hospitalService.registerPatientInHospital(reg);


        HospitalProto.Hospitals hospitals  = hospitalService.listHospitalsForPatient(patient_id);
        
        assertEquals(hospitals.getHospitalsCount(), 1);

        assertEquals(hospitals.getHospitals(0).getId(), hospital_id);


    }



}