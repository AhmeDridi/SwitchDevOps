package tn.esprit.spring.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EntrepriseServiceTest {
	@Autowired
	private IEntrepriseService es;
	private Entreprise entreprise;
	private Employe employe;
	private Departement departement;

	@Before
	public void initEntreprise() {
		entreprise = new Entreprise();
		entreprise.setId(0);
		entreprise.setName("Vermeg");
		entreprise.setRaisonSocial("SA");
	}

	@Test
	public void verifyEntrepriseName() {
		log.info("verify entreprise name");
		Assert.assertEquals("Vermeg", entreprise.getName());
	}

	@Before
	public void initDepartement() {
		departement = new Departement();
		departement.setId(0);
		departement.setName("Palmyra");
		departement.setEntreprise(entreprise);
	}

	@Test
	public void verifyDepartemntName() {
		log.info("verify departement name");
		Assert.assertFalse(departement.getName().equals("AnotherDepartment"));
	}

	@Test
	public void verifyDepartemntNameNotNull() {
		log.info("verify departement name not null");
		Assert.assertTrue(departement.getName() != null);
	}

}
