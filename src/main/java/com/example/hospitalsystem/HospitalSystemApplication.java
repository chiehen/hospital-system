package com.example.hospitalsystem;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HospitalSystemApplication {

  private static final Logger log = LoggerFactory.getLogger(HospitalSystemApplication.class);

  public static void main(String[] args) {
	SpringApplication.run(HospitalSystemApplication.class, args);
	}

  @Bean
  public CommandLineRunner demo(PatientRepository pRepository, HospitalRepository hRepository) {
    return (args) -> {
      // save a few customers
	  Patient jack =  new Patient("Jack", "Bauer", 23, "male");
	  pRepository.save(jack);
	  Patient chloe = new Patient("Chloe", "O'Brian", 34, "f");
      pRepository.save(chloe);
      pRepository.save(new Patient("Kim", "Bauer", 60, "m"));
      pRepository.save(new Patient("David", "Palmer", 49, "m"));
      pRepository.save(new Patient("Michelle", "Dessler", 8, "f"));

      // fetch all customers
      log.info("Customers found with findAll():");
      log.info("-------------------------------");
      pRepository.findAll().forEach(customer -> {
        log.info(customer.toString());
      });
      log.info("");

	  Hospital cityHospital = new Hospital("City", "Apple Street");
	  hRepository.save(cityHospital);
	  hRepository.save(new Hospital("State", "Banana Street"));

	  log.info("Hospital found with findAll():");
      log.info("-------------------------------");
      hRepository.findAll().forEach(hospital -> {
        log.info(hospital.toString());
      });
      log.info("");


	  cityHospital.addPatient(jack);
	  cityHospital.addPatient(chloe);
	  hRepository.save(cityHospital);

	  log.info("Hospital has patients:");
      log.info("-------------------------------");
      pRepository.findAllByRegisteredHospitals_Id(cityHospital.getId()).forEach(patient -> {
        log.info(patient.toString());
      });
	  

      log.info("");

	  log.info("Jack has hospital:");
      log.info("-------------------------------");
      hRepository.findAllByRegisteredPatients_Id(jack.getId()).forEach(h -> {
        log.info(h.toString());
      });
      log.info("");

      log.info("end");
    };
  }

}