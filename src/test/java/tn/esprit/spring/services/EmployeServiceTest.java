package tn.esprit.spring.services;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmployeServiceTest {

	@Autowired
	private IEmployeService es;
	private Employe employe;
	private Contrat contrat;
	private Departement department;
	private Entreprise entreprise;
	private static final Logger log= Logger.getLogger(EmployeServiceImpl.class);

	@Before
	public void initEmployee() {
		employe = new Employe();
		employe.setId(0);
		employe.setNom("devops");
		employe.setPrenom("alinfo4");
		employe.setEmail("alinfo4@devops.com");
		employe.setRole(Role.CHEF_DEPARTEMENT);
		contrat = new Contrat();
		contrat.setEmploye(employe);
		contrat.setSalaire(Float.valueOf("2"));
		employe.setContrat(contrat);
	}

	@Before
	public void initEntreprise() {
		entreprise = new Entreprise();
		entreprise.setId(5);
		entreprise.setName("entreprise");
		entreprise.setRaisonSocial("test");
	}
	@Before
	public void initDepartment() {
		department = new Departement();
		department.setId(1);
		department.setName("department");
		
	}
	@Test
	public void verifyEmployeName() {
		log.info("verify employee name");
		Assert.assertEquals("devops", employe.getNom());
	}

	@Test
	public void verifyEmployeFamilyName() {
		log.info("verify employee family name");
		Assert.assertEquals("alinfo4", employe.getPrenom());
	}

	@Test
	public void verifyEmployeEmail() {
		log.info("verify employee email");
		Assert.assertEquals("alinfo4@devops.com", employe.getEmail());
	}

	@Test
	public void verifyEmployeID() {
		log.info("verify employee id");
		Assert.assertEquals(0, employe.getId());
	}

	@Test
	public void getEmployePrenomById_Null() {
		log.info("Error getting employe");
		//Assert.assertEquals(null, es.getEmployePrenomById(0));
	}

	@Test
	public void verifyEmployeRole() {
		log.info("verify employee role");
		Assert.assertFalse(employe.getRole().equals(Role.ADMINISTRATEUR));
	}
	
	@Test
	public void verifyEmployeContract() {
		log.info("verify employe contract");
		Assert.assertTrue(employe.getContrat() != null);
	}
	
	@Test
	public void verifyAjouterContrat() {
		log.info("Add contract");
		Contrat contract1= new Contrat();
		contract1.setReference(2);
		es.ajouterContrat(contract1);
	}

	@Test
	public void verifyAffecterContratAEmploye() {
		log.info("Affect contract to employee");
		es.affecterContratAEmploye(1,1);
	}
	
	@Test
	public void verifyDeleteEmployeById() {
		log.info("Delete employee by id :");
		es.deleteEmployeById(1);
	}
	
	@Test
	public void verifyDeleteContratById() {
		log.info("Delete contract by id :");
		es.deleteContratById(2);
	}
	
	@Test
	public void verifyGetNombreEmployeJPQL() {
		log.info("Number of employees :");
		es.getNombreEmployeJPQL();
	}
	
}
