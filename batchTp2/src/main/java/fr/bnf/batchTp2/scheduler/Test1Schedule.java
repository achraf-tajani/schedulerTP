package fr.bnf.batchTp2.scheduler;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import fr.bnf.batchTp2.common.Loggable;
import fr.bnf.batchTp2.service.suiviBatch.BatchServiceLogiqueWriteService;

@Component
public class Test1Schedule extends Loggable {

	@Autowired
	public BatchServiceLogiqueWriteService batchService;
	
	@Value("${schedule.bta.cron}")
	private String btaCronExp;

	
	@PostConstruct
	public void init() {
		logger.info("[Conf] Async Test Scheduler : scheduled with cron expression [{}]", btaCronExp);
	}
	
	@Scheduled(cron = "${schedule.bta.cron}")
	@Async(value = "asyncExecutor")
	public void WriteLogique() throws Exception {
		batchService.write();
	}
}
