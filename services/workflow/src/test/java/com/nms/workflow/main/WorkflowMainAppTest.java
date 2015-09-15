package com.nms.workflow.main;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/SpringActivitiContext.xml" })
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class WorkflowMainAppTest {
	
	@Autowired
	private WorkflowMainAppImpl workflowMain;
	
	@Before
	public void setup(){
		System.out.println("***************************Deployng the process");
		InputStream iStream = this.getClass().getClassLoader().getResourceAsStream("com/tsa/bpm/process/FinancialReportProcess.bpmn20.xml");

		//WorkflowMain workflowMain = new WorkflowMainAppImpl();
		boolean status = workflowMain.deployProcess("com/tsax/bpm/process/FinancialReportProcess.bpmn20.xml",iStream);
		
	}
	

	/*	@Test
	public void testDeployProcess() {
		InputStream iStream = this.getClass().getClassLoader().getResourceAsStream("com/tsa/bpm/process/FinancialReportProcess.bpmn20.xml");

		//WorkflowMain workflowMain = new WorkflowMainAppImpl();
		boolean status = workflowMain.deployProcess("com/tsax/bpm/process/FinancialReportProcess.bpmn20.xml",iStream);
		assertTrue("Unable to deploy process",status);		
	}
*/
	@Test
	public void testCreateProcessInstance() {
		//WorkflowMain workflowMain = new WorkflowMainAppImpl();
		boolean status = workflowMain.createProcessInstance("financialReport");
		assertTrue("Unable to create process instance",status);		

	}
	
	@Test
	public void testGetTaskListForGroup(){
		//WorkflowMain workflowMain = new WorkflowMainAppImpl();
		List<CommonWorkflowTask> tasks = workflowMain.getTaskListForGroup("accountancy");
		assertTrue("Unable to get the tasks list",(tasks != null));		
	}


	@Test
	public void testClaimTask() {
		//WorkflowMain workflowMain = new WorkflowMainAppImpl();
		workflowMain.createProcessInstance("financialReport");
		List<CommonWorkflowTask> tasks = workflowMain.getTaskListForGroup("accountancy");
		boolean status  = workflowMain.claimTask("user1", tasks.get(0).getId());
		
		assertTrue("There was a problem claiming the Task",status);		
	}

	@Test
	public void testCompleteTask() {
		//WorkflowMain workflowMain = new WorkflowMainAppImpl();
		workflowMain.createProcessInstance("financialReport");
		List<CommonWorkflowTask> tasks = workflowMain.getTaskListForGroup("accountancy");
		workflowMain.claimTask("user1", tasks.get(0).getId());

		boolean status  = workflowMain.completeTask(tasks.get(0).getId());
		assertTrue("There was a problem claiming the Task",status);		
	}

	@Test
	public void testTransferTask() {
		//WorkflowMain workflowMain = new WorkflowMainAppImpl();
		workflowMain.createProcessInstance("financialReport");
		List<CommonWorkflowTask> tasks = workflowMain.getTaskListForGroup("accountancy");
		
		boolean status  = workflowMain.transferTask(tasks.get(0).getId(), "user2");
		assertTrue("There was a problem transferring the Task",status);		
	}

	@Test
	public void testReAssignTask() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTaskHistory() {
		fail("Not yet implemented");
	}

}
