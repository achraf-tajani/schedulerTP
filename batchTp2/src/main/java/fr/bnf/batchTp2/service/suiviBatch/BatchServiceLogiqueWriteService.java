package fr.bnf.batchTp2.service.suiviBatch;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bnf.batchTp2.common.Etat_demande;
import fr.bnf.batchTp2.common.Loggable;
import fr.bnf.batchTp2.entities.SuiviGalrapport;
import fr.bnf.batchTp2.repositories.SuiviGalrapportRepository;

@Service
public class BatchServiceLogiqueWriteService extends Loggable {

	@Autowired
	public SuiviGalrapportRepository suiviRepo;
	
	public void write() throws Exception {
		List<SuiviGalrapport> items = suiviRepo.getNextTodoDemande(3L);
		if(items.isEmpty()) {
			logger.debug("no demande");
		}else {
			for(SuiviGalrapport r: items) {
				this.concurentExecution(r,items.size());
			}
		}

	}
	
	public void processOneExport(SuiviGalrapport suivi) throws InterruptedException {
		logger.debug("*****************************************************");
		logger.debug("START [GALRAPPORT EXPORT] : rapport [" + suivi.getIdSuivigalrapport() + "] is started !");
		suivi.setEtat(Etat_demande.ENCOURS);
		suivi.setVersion(suivi.getVersion() + 1);
		this.updateSuivi(suivi,"ENCOURS");
		logger.debug("sleep" + suivi.getSleep());
		Thread.sleep(suivi.getSleep());
		logger.debug("PROCESS [" +suivi.getIdSuivigalrapport() + "]");
		logger.debug("Create rapport [" +suivi.getIdSuivigalrapport() + "]");
		logger.debug("Send Email [" +suivi.getIdSuivigalrapport() + "]");
		logger.debug("Validee report : [" +suivi.getIdSuivigalrapport() + "]");
		suivi.setEtat(Etat_demande.VALIDEE);
		this.updateSuivi(suivi,"VALIDEE");
		logger.debug("END [" +suivi.getIdSuivigalrapport() + "] ***************************************");
	}
	
	private void concurentExecution(SuiviGalrapport suivi,int count) {
		Executor executor = Executors.newFixedThreadPool(count);
		executor.execute(() -> {
			try {
				this.processOneExport(suivi);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		});
	}
	
	@Transactional
	public void updateSuivi(SuiviGalrapport suivi,String typeSave) {
		Integer updated = 0;
		switch (typeSave) {
		case "ENCOURS":
			 updated = suiviRepo.updateSuiviEncours(suivi.getVersion(), suivi.getIdSuivigalrapport());
			break;
		case "VALIDEE":
			 updated = suiviRepo.updateSuiviValider(suivi.getVersion(), suivi.getIdSuivigalrapport());
			break;

		}
		if(updated == 0) {
			logger.error("Data has been changed ["+suivi.getIdSuivigalrapport() +"]");
		}
	}

}


