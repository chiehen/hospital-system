package com.example.hospitalsystem;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface HospitalRepository extends CrudRepository<Hospital, Long> {


  List<Hospital> findAllByRegisteredPatients(Patient patient);
}