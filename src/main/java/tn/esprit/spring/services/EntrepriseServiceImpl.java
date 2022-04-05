package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
@Slf4j
public class EntrepriseServiceImpl implements IEntrepriseService {

	@Autowired
	EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;

	public int ajouterEntreprise(Entreprise entreprise) {
		log.info("add enterprise" + entreprise.getName());
		entrepriseRepoistory.save(entreprise);
		log.info("entreprise " + entreprise.getName() + "added");
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		log.info("add department" + dep.getName());
		deptRepoistory.save(dep);
		log.info("departement " + dep.getName() + "added");
		return dep.getId();
	}

	@Transactional
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		log.info("affect department to enterprise");
		try {
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
			Departement depManagedEntity = deptRepoistory.findById(depId).get();

			if (entrepriseManagedEntity.getDepartements() == null) {
				log.info("entreprise has no departement : ");
				List<Departement> departements = new ArrayList<>();
				departements.add(depManagedEntity);
				entrepriseManagedEntity.setDepartements(departements);

			} else {
				log.info("entreprise has already departemnt : ");
				entrepriseManagedEntity.getDepartements().add(depManagedEntity);
			}
		} catch (Exception ex) {
			log.error("failed to affect departement to entreprise");
		}
	}

	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		List<String> depNames = new ArrayList<>();
		for (Departement dep : entrepriseManagedEntity.getDepartements()) {
			depNames.add(dep.getName());
		}

		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		log.info("deleted enterprise");
		entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).get());
	}

	@Transactional
	public void deleteDepartementById(int depId) {
		log.info("delete department");
		deptRepoistory.delete(deptRepoistory.findById(depId).get());
	}

	public Entreprise getEntrepriseById(int entrepriseId) {
		return entrepriseRepoistory.findById(entrepriseId).get();
	}

}
