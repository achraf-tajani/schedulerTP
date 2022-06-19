package fr.bnf.batchTp2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.bnf.batchTp2.common.DateUtils;
import fr.bnf.batchTp2.common.Loggable;
import fr.bnf.batchTp2.common.SchedulerType;
import fr.bnf.batchTp2.service.scheduler.SchedulerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "Scheduler operations")
@RestController
@RequestMapping(path = "/scheduler")
public class SchedulerRestController extends Loggable {

	@Autowired
	private SchedulerService schedulerService;
	
	@ApiOperation(value = "Scheduler start/stop action")
	@GetMapping(value = "/scheduler/{name}/{action}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String globalSchedulerStatus(
			@PathVariable("name") @ApiParam("Allows : ALL|AGREGATION|RECUPRES|MAJSUIVINUM|SUIVIBTA") String name,
			@PathVariable("action") @ApiParam("Allows : start|stop|run") String action) throws Exception {
		SchedulerType type = SchedulerType.valueOf(name);
		return executeAction(type, action);
	}
	
	
	private String executeAction(SchedulerType type, String action) throws Exception {
		switch (action) {
		case "start":
			schedulerService.start(type, "Started by remote service : ".concat(DateUtils.formatDateNowPretty()));
			return type.name().concat("Scheduler has been started");
		case "stop":
			schedulerService.stop(type, "Stopped by remote service : ".concat(DateUtils.formatDateNowPretty()));
			return type.name().concat("Scheduler has been stopped");
		case "run":
			logger.info("Run scheduler action requested on batch-tools API for scheduler [{}]", type);
			schedulerService.run();
			return type.name().concat("Scheduler has been triggered");
		default:
			throw new IllegalArgumentException("Unknown action [".concat(action).concat("] (expected : start or stop"));
		}
	}
}
