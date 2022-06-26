package fr.bnf.batchTp2.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.bnf.batchTp2.entities.SuiviGalrapport;
import fr.bnf.batchTp2.rest.param.ParamSuivi;
import fr.bnf.batchTp2.service.suiviBatch.BatchServiceLogiqueWriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Api(value = "AppRestController operations")
@RestController
@RequestMapping(path = "/api")
public class AppRestController {

	@Autowired
	private BatchServiceLogiqueWriteService batchService;
	
	@ApiOperation(value = "listDemande")
	@GetMapping(value = "/listDemande", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SuiviGalrapport> list()  {
		return batchService.listeDemande();
	}
	
	@ApiOperation(value = "new suivi")
	@PostMapping(value = "/generateRapport", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public SuiviGalrapport generateRapport(@RequestBody ParamSuivi exportModel)  {
		return this.batchService.newSuivi(exportModel);
	}
	
	
}
