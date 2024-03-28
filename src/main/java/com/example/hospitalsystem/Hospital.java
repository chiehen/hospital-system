package com.example.hospitalsystem;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;

@Entity
public class Hospital {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  private String name;
  private String address;

  @ManyToMany
  @JoinTable(
    name = "registration", 
    joinColumns = @JoinColumn(name = "hospital_id"), 
    inverseJoinColumns = @JoinColumn(name = "patient_id"))
  private Set<Patient> registeredPatients = new HashSet<>();

  protected Hospital() {}

  public Hospital(String name, String address) {
    this.name = name;
    this.address = address;
  }

  @Override
  public String toString() {
    return String.format(
        "Hospital[id=%d, name='%s', address='%s']",
        id, name, address);
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public Set<Patient> getPatients() {
    return registeredPatients;
  }

  public void addPatient(Patient patient) {
    this.registeredPatients.add(patient);
    patient.registerHospital(this);
  }
}