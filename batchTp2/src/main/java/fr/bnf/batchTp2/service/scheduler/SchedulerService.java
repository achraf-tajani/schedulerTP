package fr.bnf.batchTp2.service.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import fr.bnf.batchTp2.common.Loggable;
import fr.bnf.batchTp2.common.SchedulerType;
import fr.bnf.batchTp2.scheduler.Test1Schedule;
import fr.bnf.batchTp2.service.system.SystemSettings;

@Service
public class SchedulerService  extends Loggable {

	@Autowired
	private Test1Schedule btaScheduler;

	
    private SystemSettings settings; 
    
    public SchedulerService(@NonNull @Lazy SystemSettings settings) {    
        this.settings = settings;  
    }
	
	public void start(SchedulerType type,String message) {
		settings.enableBtaScheduler(message);
	}
	
	public void stop(SchedulerType type,String message) {
		settings.disableBtaScheduler(message);
	}
	
	  /**
	   * Execute specific scheduler action
	   *
	   * @param type {@link SchedulerType}
	   * @param message
	 * @throws Exception 
	   */
	  public void run() throws Exception {
		  this.btaScheduler.WriteLogique();
	  }
	  
	  
	public void stopGLobalScheduler(String message) {
		if (isGLobalSchedulerRunning()) {
			logger.info("Disable global scheduler - All scheduler will be disabled");
			settings.setGlobalSchedulerStatusReason(message);
			settings.setGlobalSchedulerEnabled(false);
			// No action on scheduler itself, only on status
		}
	}

	public void startGlobalScheduler(String message) {
		if (!isGLobalSchedulerRunning()) {
			logger.info("Starting global scheduler");
			settings.setGlobalSchedulerStatusReason(message);
			settings.setGlobalSchedulerEnabled(true);
			// No action on scheduler itself, only on status
		}
	}

	public boolean isGLobalSchedulerRunning() {
		return settings.isGlobalSchedulerEnabled();
	}
}
