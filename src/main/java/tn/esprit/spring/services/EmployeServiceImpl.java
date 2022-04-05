package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
@Slf4j
public class EmployeServiceImpl implements IEmployeService {
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
private static final Logger log= Logger.getLogger(EmployeServiceImpl.class);
	public int ajouterEmploye(Employe employe) {
		log.info("add of employee : " + employe.getNom());
		employeRepository.save(employe);
		log.info("employee " + employe.getNom() + "added");
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		log.info("Update mail : ");
		try {
			Employe employe = employeRepository.findById(employeId).get();
			employe.setEmail(email);
			employeRepository.save(employe);
			log.info("mail " + email + "updated");
		} catch (Exception ex) {
			log.error("failed to update :" + ex.getMessage());
		}
	}

	@Transactional
	public void affecterEmployeADepartement(int employeId, int depId) {
		log.info("Set Employe to department : ");
		try {
			Departement depManagedEntity = deptRepoistory.findById(depId).get();
			Employe employeManagedEntity = employeRepository.findById(employeId).get();

			if (depManagedEntity.getEmployes() == null) {
				log.info("department has no employee : ");
				List<Employe> employes = new ArrayList<>();
				employes.add(employeManagedEntity);
				depManagedEntity.setEmployes(employes);
			} else {
				log.info("department has already employees : ");
				depManagedEntity.getEmployes().add(employeManagedEntity);

			}
		} catch (Exception ex) {
			log.error("failed to affect employee to department");
		}

	}

	@Override
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		Departement dep = deptRepoistory.findById(depId).get();
		int employeNb = dep.getEmployes().size();
		log.info("Unset employe "+employeNb+" from department "+ dep.getName());
		try {
		for (int index = 0; index < employeNb; index++) {
			if (dep.getEmployes().get(index).getId() == employeId) {
				{
					dep.getEmployes().remove(index);
				log.info("Employee "+employeId+" is removed");
				}
				break;
			}
		}
		}catch (Exception ex) {
			log.error("failed to remove employe "+employeNb+" from department "+ dep.getName());
		}
		
	}

	@Override
	public int ajouterContrat(Contrat contrat) {
		log.info("Add contract : ");
		try {
		contratRepoistory.save(contrat);
		log.info("Contract has been added successfuly");
		return contrat.getReference();
		}catch(Exception ex)
		{
			log.error("failed to add contract ");
		}
		return 0;
	}

	@Override
	public void affecterContratAEmploye(int contratId, int employeId) {
		log.info("Affect contract "+contratId+" to employee"+ employeId);
		try {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);
		log.info("Contract affected");
		}catch(Exception ex)
		{
			log.error("Failed to affect contract "+contratId+" to employee"+ employeId);
		}
	}

	@Override
	public String getEmployePrenomById(int employeId) {
		log.info("Show employee"+ employeId);
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		return employeManagedEntity.getPrenom();
	}

	@Override
	public void deleteEmployeById(int employeId) {
		Employe employe = new Employe();
		try {
		employe = employeRepository.findById(employeId).get();
		}catch (Exception ex)
		{
			log.error("cannot find employee with id : "+ employeId);
		}
		log.info("Delete employee"+ employeId);
		try {
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}
		employeRepository.delete(employe);
		log.info("Employe removed successfuly");
		}catch(Exception ex )
		{
			log.error("Employee "+ employeId+ " cannot be removed");
		}

	}

	@Override
	public void deleteContratById(int contratId) {
		Contrat contratManagedEntity = new Contrat();
		try {
			 contratManagedEntity = contratRepoistory.findById(contratId).get();
		}catch (Exception ex)
		{
			log.error("cannot find contract with id : "+ contratId);
		}
		contratRepoistory.delete(contratManagedEntity);
		log.info("Contract "+contratId+" has been deleted successfuly");
	}

	@Override
	public int getNombreEmployeJPQL() {
		log.info("The number of employees is  : ");
		return employeRepository.countemp();
	}

	@Override
	public List<String> getAllEmployeNamesJPQL() {
		log.info("Employees names are  : ");
		return employeRepository.employeNames();
	}

	@Override
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllContratJPQL() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getSalaireByEmployeIdJPQL(int employeId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Double getSalaireMoyenByDepartementId(int departementId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employe> getAllEmployes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		// TODO Auto-generated method stub
		return null;
	}

	 
}
