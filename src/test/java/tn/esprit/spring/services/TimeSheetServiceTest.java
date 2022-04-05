package tn.esprit.spring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import org.apache.log4j.Logger;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j

public class TimeSheetServiceTest {
	@Autowired
	TimesheetRepository timesheetRepository ;
	@Autowired
	MissionRepository missionRepository ;
	@Autowired
	EmployeRepository employeRepository;
	private static final Logger log= Logger.getLogger(TimesheetServiceImpl.class);

	
	Employe employe = new Employe();
	Mission mission = new Mission();
	Timesheet timesheet = new Timesheet();
	long dataBeforeTest ;
	
	@Test
	public void testAddTimeSheet() {
		buildTimeSheet();
		timesheetRepository.save(timesheet);
		//after
		long dataAfterTest = timesheetRepository.count();
		//isequal
		assertThat(dataBeforeTest).isEqualTo(dataAfterTest -1);
		timesheetRepository.delete(timesheet);
		employeRepository.deleteById(employe.getId());
		missionRepository.deleteById(mission.getId());
	}
	@Test
	public void testUpdateTimeSheet(){
		buildTimeSheet();
		timesheetRepository.save(timesheet);
		//update
		Mission mission2 = new Mission();
		mission2.setName("Tester");
		missionRepository.save(mission2);
		TimesheetPK timesheetpk = new TimesheetPK(mission2.getId(),employe.getId(),new Date(),new Date());
		timesheet.setTimesheetPK(timesheetpk);
		timesheet.setMission(mission2);
		timesheetRepository.save(timesheet);
		//assertion
		assertThat(timesheet.getMission().getName().toString()).isEqualTo(mission2.getName());
		timesheetRepository.delete(timesheet);
		employeRepository.deleteById(employe.getId());
		missionRepository.deleteById(mission.getId());
	}
	private void buildTimeSheet(){
		dataBeforeTest = timesheetRepository.count();
		employe.setNom("ghada");
		mission.setName("Etudiante");
		employeRepository.save(employe);
		missionRepository.save(mission);
		TimesheetPK timesheetPK = new TimesheetPK(mission.getId(),employe.getId(),new Date(),new Date());
		timesheet.setEmploye(employe);
		timesheet.setMission(mission);
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(true);
		log.info(timesheet);
	}
}
