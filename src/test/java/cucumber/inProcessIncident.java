package cucumber;

import org.springframework.beans.factory.annotation.Autowired;

import com.uniovi.entitites.Incident;
import com.uniovi.entitites.IncidentStates;
import com.uniovi.entitites.Operator;
import com.uniovi.services.IncidentsService;
import com.uniovi.services.OperatorService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class inProcessIncident {

	private Operator operator;
	private Incident incident;

	@Autowired
	private IncidentsService incidentsService;

	@Autowired
	private OperatorService operatorService;

	@Given("^operator with username \"(.+)\"$")
	public void operator_with_username(String username) throws Throwable {
		this.operator = operatorService.getOperatorByEmail(username);
	}

	@Given("^an assigned incident$")
	public void an_assigned_incident() throws Throwable {
		incident = incidentsService.getIncidentsOfOperator(operator.getEmail()).get(0);
	}

	@When("^he modifies the incident$")
	public void he_modifies_the_incidence() throws Throwable {
		incidentsService.changeIncidentState(incident.getId(), IncidentStates.IN_PROCESS.toString());
	}

	@Then("^the incident is in process$")
	public void the_incidence_is_in_process() throws Throwable {
		if (!incident.getState().equals(IncidentStates.IN_PROCESS)) {
			throw new Throwable(
					"The state of the incident " + incident.getId().toString() + " was not changed to IN_PROCESS");
		}
	}
}