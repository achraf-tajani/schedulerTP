package fr.bnf.batchTp2.service.system;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import fr.bnf.batchTp2.service.scheduler.SchedulerService;

@Component
public class SystemSettings {

	
	   private SchedulerService schedulerService;
	   public SystemSettings(@NonNull @Lazy SchedulerService schedulerService) {
		   this.schedulerService = schedulerService;
	   }
	    @Value("true")
	    private boolean globalSchedulerEnabled;
	    
	    @Value("Running")
	    private String globalSchedulerStatusReason;
	    
	    
	    @Value("${schedule.bta.cron.enable:false}")
	    private boolean btaSchedulerEnabledInitBool;
	    private AtomicBoolean btaSchedulerEnabled;
	    
	    @Value("Started")
	    private String btaSchedulerStatusReason;
	    
		@Value("false")
	    private boolean btaInProgress;
		



		@PostConstruct
	    public void init() {
	    	this.btaSchedulerEnabled = new AtomicBoolean(btaSchedulerEnabledInitBool);
	    }
	    
		public void enableBtaScheduler(String message) {
			if (!this.btaSchedulerEnabled.get()) {
				this.btaSchedulerEnabled.set(true);
				this.btaSchedulerStatusReason = message;
			}

		}

	    public void disableBtaScheduler(String message) {
			if (this.btaSchedulerEnabled.get()) {
				this.btaSchedulerEnabled.set(false);
				this.btaSchedulerStatusReason = message;
			}
	    }
	    
	    
	    /**
	     * @param globalSchedulerStatusReason the globalSchedulerStatusReason to set
	     */
	    public void setGlobalSchedulerStatusReason(String globalSchedulerStatusReason) {
	        this.globalSchedulerStatusReason = globalSchedulerStatusReason;
	    }
	    
	    /**
	     * @param globalSchedulerEnabled the globalSchedulerEnabled to set
	     */
	    public void setGlobalSchedulerEnabled(boolean globalSchedulerEnabled) {
	        this.globalSchedulerEnabled = globalSchedulerEnabled;
	    }
	    
	    public boolean isGlobalSchedulerEnabled() {
	        return this.globalSchedulerEnabled;
	    }

	    public void shutdownGlobalScheduler(String message) {
	        this.globalSchedulerStatusReason = message;
	        schedulerService.stopGLobalScheduler(message);
	    }

	    public void startGlobalScheduler(String message) {
	        this.globalSchedulerStatusReason = message;
	        schedulerService.startGlobalScheduler(message);
	    }
	    

		public boolean isBtaSchedulerEnabledInitBool() {
			return btaSchedulerEnabledInitBool;
		}



		public void setBtaSchedulerEnabledInitBool(boolean btaSchedulerEnabledInitBool) {
			this.btaSchedulerEnabledInitBool = btaSchedulerEnabledInitBool;
		}



		public AtomicBoolean getBtaSchedulerEnabled() {
			return btaSchedulerEnabled;
		}



		public void setBtaSchedulerEnabled(AtomicBoolean btaSchedulerEnabled) {
			this.btaSchedulerEnabled = btaSchedulerEnabled;
		}



		public boolean isBtaInProgress() {
			return btaInProgress;
		}



		public void setBtaInProgress(boolean btaInProgress) {
			this.btaInProgress = btaInProgress;
		}



		public String getGlobalSchedulerStatusReason() {
			return globalSchedulerStatusReason;
		}

}
