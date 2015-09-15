package com.nms.workflow.main;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.nms.workflow.activiti.ActivitiWorkflowManager;



/**
 * 
 * Web service carrying out all the operations on a workflow engine. 
 *
 */

@Path("/workflow")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })

public class WorkflowMainWebServiceImpl implements WorkflowMain 
{
	ActivitiWorkflowManager workflowMgr = null;

	public WorkflowMainWebServiceImpl(ActivitiWorkflowManager workflowMgr){
		this.workflowMgr=workflowMgr;
	}

	/* Deploys the given workflow definintion xml file.
	 * @see com.nms.activiti.app.WorkflowMain#deployProcess(java.lang.String, java.io.InputStream)
	 */
	@POST
	@Path("/deployProcess")
    public boolean deployProcess(String resourceName, InputStream iStream){
		return workflowMgr.deployProcess(resourceName,iStream);
	}
	
	/* (non-Javadoc)
	 * @see com.nms.activiti.app.WorkflowMain#createProcessInstance(java.lang.String)
	 */
	@POST
	@Path("/startTask")
	public boolean createProcessInstance(String workFlowName){		
		return workflowMgr.createProcessInstance(workFlowName);
	}

	/* (non-Javadoc)
	 * @see com.nms.activiti.app.WorkflowMain#getTaskList(java.lang.String, java.lang.String)
	 */
	@GET
	@Path("/getGroupTasks")
	@Produces({MediaType.APPLICATION_JSON })
	public List<CommonWorkflowTask> getTaskListForGroup(@QueryParam("groupID") String groupID){
		return workflowMgr.getTaskListForGroup(groupID);
	}
	
	/* (non-Javadoc)
	 * @see com.nms.activiti.app.WorkflowMain#claimTask(java.lang.String, java.lang.String)
	 */
	@GET
	@Path("/claimTask")
	public boolean claimTask(String userID,String taskID) {
		return workflowMgr.claimTask(userID, taskID);
	}		
	
	/* (non-Javadoc)
	 * @see com.nms.activiti.app.WorkflowMain#completeTask(java.lang.String)
	 */
	@POST
	@Path("/completeTask")
	public boolean completeTask(String taskID){
		return workflowMgr.completeTask(taskID);
	}		
	
	/* (non-Javadoc)
	 * @see com.nms.activiti.app.WorkflowMain#transferTask(java.lang.String, java.lang.String)
	 */
	@POST
	@Path("/transferTask")
	public boolean transferTask(String taskID,String userID){
		return workflowMgr.transferTask(taskID, userID);
	}

	@GET
	@Path("/version")
	@Produces({MediaType.APPLICATION_JSON })
	public String getVersion(){
		return "1.0";
	}
	//TODO
	/* (non-Javadoc)
	 * @see com.nms.activiti.app.WorkflowMain#reAssignTask()
	 */
	public boolean reAssignTask(){
		return false;
	}
	
	//TODO
	/* (non-Javadoc)
	 * @see com.nms.activiti.app.WorkflowMain#getTaskHistory()
	 */
	public void getTaskHistory(){
		
	}

	public List<CommonWorkflowTask> getTaskListForUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
