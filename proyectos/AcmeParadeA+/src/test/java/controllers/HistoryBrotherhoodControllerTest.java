
package controllers;

import java.util.Collection;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import controllers.brotherhood.HistoryBrotherhoodController;

import services.HistoryService;
import services.BrotherhoodService;
import services.InceptionRecordService;
import utilities.AbstractTest;
import domain.History;
import domain.Brotherhood;
import domain.InceptionRecord;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class HistoryBrotherhoodControllerTest extends AbstractTest {

	private MockMvc					mockMvc;

	// The SUT (Service Under Test) -------------------------------------------

	@Autowired
	private HistoryBrotherhoodController	historyBrotherhoodController;

	@Autowired
	private BrotherhoodService				brotherhoodService;

	@Autowired
	private HistoryService		historyService;
	
	@Autowired
	private InceptionRecordService		inceptionRecordService;


	// SetUp ------------------------------------------------------------------

	@Override
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.historyBrotherhoodController).build();
	}

	// Tests ------------------------------------------------------------------

	@Test
	public void testDisplayHistory() throws Exception {

		this.authenticate("brotherhood1");

		Brotherhood principal = null;
		History history = null;

		principal = this.brotherhoodService.findByPrincipal();
		history = this.historyService.findByBrotherhoodId(principal.getId());

		this.mockMvc.perform(MockMvcRequestBuilders.get("/history/brotherhood/display"))
		.andExpect(MockMvcResultMatchers.status().isOk()) //Comprobamos que no da fallo
		.andExpect(MockMvcResultMatchers.view().name("history/display")) //Comprobamos que redirige a la vista esperada
		.andExpect(MockMvcResultMatchers.model().attribute("principal", principal)) //Comprobamos que el usuario que pasamos a la vista es el usuario logeado
		.andExpect(MockMvcResultMatchers.model().attribute("history", history)); //Comprobamos que la history que enviamos es la que le pertenece al brotherhood

		this.unauthenticate();
	}
	
	@Test
	public void testCreateHistory() throws Exception {

		this.authenticate("brotherhood1");

		Brotherhood principal = null;
		InceptionRecord inceptionRecord = null;

		principal = this.brotherhoodService.findByPrincipal();
		inceptionRecord = this.inceptionRecordService.create();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/history/brotherhood/create.do"))
		.andExpect(MockMvcResultMatchers.status().isOk()) //Comprobamos que no da fallo
		.andExpect(MockMvcResultMatchers.view().name("history/edit")) //Comprobamos que redirige a la vista esperada
		.andExpect(MockMvcResultMatchers.model().attribute("principal", principal)) //Comprobamos que el usuario que pasamos a la vista es el usuario logeado
		.andExpect(MockMvcResultMatchers.model().attribute("inceptionRecord", inceptionRecord)); //Comprobamos que el record que pasamos es uno nuevo

		this.unauthenticate();
	}
	
	@Test
	public void testEditHistory() throws Exception {

		this.authenticate("brotherhood1");

		Brotherhood principal = null;
		History history = null;
		
		InceptionRecord inceptionRecord = null;

		principal = this.brotherhoodService.findByPrincipal();
		history = this.historyService.findByBrotherhoodId(principal.getId());
		inceptionRecord = history.getInceptionRecord();

		this.mockMvc.perform(MockMvcRequestBuilders.get("/history/brotherhood/edit.do?historyId=" + history.getId()))
		.andExpect(MockMvcResultMatchers.status().isOk()) //Comprobamos que no da fallo
		.andExpect(MockMvcResultMatchers.view().name("history/edit")) //Comprobamos que redirige a la vista esperada
		.andExpect(MockMvcResultMatchers.model().attribute("principal", principal)) //Comprobamos que el usuario que pasamos a la vista es el usuario logeado
		.andExpect(MockMvcResultMatchers.model().attribute("inceptionRecord", inceptionRecord)); //Comprobamos que el record que pasamos es uno nuevo

		this.unauthenticate();
	}
}
