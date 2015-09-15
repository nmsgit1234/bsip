package com.nms.workflow.activiti;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nms.workflow.main.CommonWorkflowTask;
import com.nms.workflow.main.CommonWorkflowTask;


/**
 * 
 *
 */
public class ActivitiWorkflowManager {

	private ProcessEngine processEngine = null;
	
	public ActivitiWorkflowManager(ProcessEngine processEngine) {
		this.processEngine=processEngine;
	}
	private RepositoryService repositoryService = null;
	private TaskService taskService = null;
	private IdentityService identityService = null;
	private RuntimeService runtimeService = null;
	
/*	public ActivitiWorkflowManager(){
		processEngine = ActivitiProcessEngineInstance.getInstance();
	}*/

	public boolean deployProcess(String resourceName, InputStream iStream) {
		repositoryService = processEngine.getRepositoryService();
		Deployment deployment = repositoryService.createDeployment().addInputStream(resourceName, iStream).deploy();
		return (deployment.getId() != null) ? true : false;
	}

	public boolean createProcessInstance(String workFlowName) {
		runtimeService = processEngine.getRuntimeService();
		try {
			runtimeService.startProcessInstanceByKey(workFlowName);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public List<CommonWorkflowTask> getTaskListForGroup(String groupId){
		taskService = processEngine.getTaskService();
		List taskList = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
			
		System.out.println("Task list is :" + taskList.toString());
		List<CommonWorkflowTask> tasks = translateToCommonCommonTaskType(taskList); 
		//return taskList!=null?taskList.toString():null;
		return tasks;
	}


	/**
	 * Translate the Task of the specific workflow engine to common workflow task type.
	 * @param taskList
	 * @return
	 */
	private List<CommonWorkflowTask> translateToCommonCommonTaskType(List taskList) {
		List<CommonWorkflowTask> newTaskList = new ArrayList<CommonWorkflowTask>();
		for (Object task:taskList){
			CommonWorkflowTask newTask = new CommonWorkflowTask();
			try {
				BeanUtils.copyProperties(newTask, task);
				newTaskList.add(newTask);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newTaskList;
	}

	public boolean claimTask(String userID,String taskID) {
		taskService = processEngine.getTaskService();
		try {
			taskService.claim(taskID,userID);
		} catch (Exception ex) {
			System.out.println("Exception occured while claiming the task for user:return false;"+ userID);
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean completeTask(String taskID) {
		taskService = processEngine.getTaskService();
		try {
			taskService.complete(taskID);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean transferTask(String taskID,String userID) {
		taskService = processEngine.getTaskService();
		try {
			taskService.delegateTask(taskID, userID);
		} catch (Exception ex) {
			System.out.println("Exception occured while trasferring the task for "+ userID);
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean reAssignTask() {
		return false;
	}

	public void getTaskHistory() {

	}

	// TODO this is for test purpose only, need to delete later.
	private void createUsers() {

		identityService = processEngine.getIdentityService();
		for (int x = 0; x < 99 + 1; x++) {
			User user = identityService.newUser("user" + x);
			user.setEmail("localuser" + x + "@localhost.com");
			user.setFirstName("FNlocaluser" + x);
			user.setLastName("LNlocaluser" + x);
			user.setPassword("localuser" + x);
			identityService.saveUser(user);
			identityService.createMembership("user" + x, "accountancy");
			System.out.println(": Created user: " + x);
		}
	}

	// TODO this is for test purpose only, need to delete later.
	private void deleteUsers() {
		// Delete users and membership if exists
		identityService = processEngine.getIdentityService();
		for (int x = 0; x < 99 + 1; x++) {
			try {
				identityService.deleteMembership("user" + x, "accountancy");
				identityService.deleteUser("user" + x);
				System.out.println(": Deleted user: " + x);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		String contextPath = "SpringActivitiContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				contextPath);

		ActivitiWorkflowManager wMgr = new ActivitiWorkflowManager((ProcessEngine)applicationContext.getBean("processEngine"));
		wMgr.setProcessEngine();
		wMgr.createUsers();
	}

	public void setProcessEngine() {
		this.processEngine = ActivitiProcessEngineInstance.getInstance();
	}
}
