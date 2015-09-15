package com.nms.workflow.main;

import java.io.InputStream;
import java.util.List;

public interface WorkflowMain {

	public abstract boolean deployProcess(String resourceName,
			InputStream iStream);

	public abstract boolean createProcessInstance(String workFlowName);

	public abstract List<CommonWorkflowTask> getTaskListForGroup(String groupId);
	
	public abstract List<CommonWorkflowTask> getTaskListForUser(String userId);
	
	public abstract boolean claimTask(String userID, String taskID);

	public abstract boolean completeTask(String taskID);

	public abstract boolean transferTask(String taskID, String userID);

	//TODO
	public abstract boolean reAssignTask();

	//TODO
	public abstract void getTaskHistory();

}