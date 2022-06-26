package fr.bnf.batchTp2;

import java.util.Date;
import java.util.Random;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.bnf.batchTp2.common.Etat_demande;
import fr.bnf.batchTp2.entities.SuiviGalrapport;
import fr.bnf.batchTp2.repositories.SuiviGalrapportRepository;

@SpringBootApplication
public class BatchTp2Application implements CommandLineRunner {

	@Autowired
	public SuiviGalrapportRepository suiviRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BatchTp2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SuiviGalrapport s ;
		int[] intArray = {12000, 7000, 5000,3000,20000,50000};
		for(int valeur = 1;valeur<11;valeur ++) {
		   s = new SuiviGalrapport();
		   s.setArks(String.valueOf(valeur));
		   s.setEmail("achraf email"+valeur);
		   s.setDateCreation(new Date());
		   s.setDateCreation(new Date());
		   s.setDateUpdate(new Date());
		   s.setEtat(Etat_demande.AFAIRE);
		   s.setSleep(intArray[new Random().nextInt(intArray.length)]);
		   suiviRepo.save(s);
		}

		
	}

}
