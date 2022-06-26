package fr.bnf.batchTp2.service.suiviBatch;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.management.RuntimeErrorException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.bnf.batchTp2.common.Etat_demande;
import fr.bnf.batchTp2.common.Loggable;
import fr.bnf.batchTp2.entities.SuiviGalrapport;
import fr.bnf.batchTp2.repositories.SuiviGalrapportRepository;
import fr.bnf.batchTp2.rest.param.ParamSuivi;
import fr.bnf.batchTp2.websocket.service.WebsocketService;

@Service
public class BatchServiceLogiqueWriteService extends Loggable {

	@Autowired
	public SuiviGalrapportRepository suiviRepo;
	
	@Autowired
	public WebsocketService webSocket;
	
	 public void write() throws Exception {
		List<SuiviGalrapport> items = suiviRepo.getNextTodoDemande(3L);
		logger.debug("Nombre demande a faire est {} ",items.size());
		if(items.size() > 0) {
			List<SuiviGalrapport> itemsEncours = suiviRepo.getDemandeEncours();
			logger.debug("Nombre demande a encours est {} ",itemsEncours.size());
			if(itemsEncours.size() == 0) {
				ExecutorService executor = Executors.newFixedThreadPool(items.size());
				for(SuiviGalrapport r: items) {
					logger.debug("Thread : {}", Thread.currentThread().getName());
					executor.execute(() -> {
						try {
							this.processOneExport(r);
						}catch(Exception e) {
							logger.error("ERROR ON DEMANDE");
						}
					});
					if(((ThreadPoolExecutor) executor).getActiveCount() == 0) {
						executor.shutdown();
					}
				}
			}else {
				logger.info(" Maximum Export is 3 report");
			}
		} else {
			logger.info("Pas de demande a faire");
		}


	}
	
	public void processOneExport(SuiviGalrapport suivi)  {
		logger.debug("*****************************************************");
		logger.debug("START [GALRAPPORT EXPORT] : rapport [" + suivi.getIdSuivigalrapport() + "] is started !");
		suivi.setEtat(Etat_demande.ENCOURS);
		suivi.setDateUpdate(new Date());
		suivi.setVersion(suivi.getVersion() + 1);
		this.updateSuivi(suivi,"ENCOURS");
		logger.debug("sleep" + suivi.getSleep());
		try {
			Thread.sleep(suivi.getSleep());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.debug("PROCESS [" +suivi.getIdSuivigalrapport() + "]");
		logger.debug("Create rapport [" +suivi.getIdSuivigalrapport() + "]");
		logger.debug("Send Email [" +suivi.getIdSuivigalrapport() + "]");
		logger.debug("Validee report : [" +suivi.getIdSuivigalrapport() + "]");
		suivi.setDateUpdate(new Date());
		suivi.setEtat(Etat_demande.VALIDEE);
		this.updateSuivi(suivi,"VALIDEE");
		logger.debug("END [" +suivi.getIdSuivigalrapport() + "] ***************************************");
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
		}else {
			webSocket.sendMessage("update");
		}
	}

	
	public List<SuiviGalrapport> listeDemande(){
		return (List<SuiviGalrapport>) suiviRepo.findAll();
	}

	public SuiviGalrapport newSuivi(ParamSuivi suivi) {
		SuiviGalrapport s = new SuiviGalrapport();
		s.setEmail(suivi.getEmail());
		s.setEtat(Etat_demande.AFAIRE);
		s.setSleep(2000);
		return this.suiviRepo.save(s);
	}
}


