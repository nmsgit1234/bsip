package com.nms.workflow.main;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nms.workflow.activiti.ActivitiWorkflowManager;

/**
 * 
 * 
 */
public class WorkflowMainAppImpl implements WorkflowMain {
	@Autowired
	ActivitiWorkflowManager workflowMgr = null;

	public ActivitiWorkflowManager getWorkflowMgr() {
		return workflowMgr;
	}

	public void setWorkflowMgr(ActivitiWorkflowManager workflowMgr) {
		this.workflowMgr = workflowMgr;
	}

	public WorkflowMainAppImpl() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nms.activiti.app.WorkflowMain#deployProcess(java.lang.String,
	 * java.io.InputStream)
	 */
	public boolean deployProcess(String resourceName, InputStream iStream) {
		return workflowMgr.deployProcess(resourceName, iStream);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nms.activiti.app.WorkflowMain#createProcessInstance(java.lang.String)
	 */
	public boolean createProcessInstance(String workFlowName) {
		return workflowMgr.createProcessInstance(workFlowName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nms.activiti.app.WorkflowMain#getTaskList(java.lang.String,
	 * java.lang.String)
	 */
	public List<CommonWorkflowTask> getTaskListForGroup(String groupId) {
		return workflowMgr.getTaskListForGroup(groupId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nms.activiti.app.WorkflowMain#claimTask(java.lang.String,
	 * java.lang.String)
	 */
	public boolean claimTask(String userID, String taskID) {
		return workflowMgr.claimTask(userID, taskID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nms.activiti.app.WorkflowMain#completeTask(java.lang.String)
	 */
	public boolean completeTask(String taskID) {
		return workflowMgr.completeTask(taskID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nms.activiti.app.WorkflowMain#transferTask(java.lang.String,
	 * java.lang.String)
	 */
	public boolean transferTask(String taskID, String userID) {
		return workflowMgr.transferTask(taskID, userID);
	}

	// TODO
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nms.activiti.app.WorkflowMain#reAssignTask()
	 */
	public boolean reAssignTask() {
		return false;
	}

	// TODO
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nms.activiti.app.WorkflowMain#getTaskHistory()
	 */
	public void getTaskHistory() {

	}

	public List<CommonWorkflowTask> getTaskListForUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
