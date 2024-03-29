package com.example.hospitalsystem;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String firstName;
  private String lastName;
  private String sex;
  private int age;

  @ManyToMany(mappedBy = "registeredPatients", fetch = FetchType.EAGER)
  Set<Hospital> registeredHospitals = new HashSet<>();;

  protected Patient() {
  }

  public Patient(String firstName, String lastName, int age, String sex) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.sex = sex;
  }

  @Override
  public String toString() {
    return String.format(
        "Customer[id=%d, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getAge() {
    return age;
  }
  public String getSex() {
    return sex;
  }

  public Set<Hospital> getHospitals() {
    return registeredHospitals;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setFirstName(String firString) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setSex(String sex) {

    this.sex = sex;
  }

  // public void registerHospital(Hospital hospital) {
  //   this.registeredHospitals.add(hospital);
  // }
}