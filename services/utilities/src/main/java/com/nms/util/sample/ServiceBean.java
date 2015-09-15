package com.nms.util.sample;

import java.util.HashSet;
import java.util.Set;


public class ServiceBean{

	private Long nodeId;
	private Long prntNodeId;
	private String name;
	private String description;
	private Set properties = new HashSet();
        private Set persons = new HashSet();


	public void setNodeId(Long nodeId){
		this.nodeId=nodeId;
	}

	public Long getNodeId(){
		return nodeId;
	}

	public void setPrntNodeId(Long prntNodeId){
		this.prntNodeId=prntNodeId;
	}

	public Long getPrntNodeId(){
		return prntNodeId;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getName(){
		return name;
	}

	public void setDescription(String description){
		this.description=description;
	}

	public String getDescription(){
		return description;
	}

	public void setProperties(Set properties){
		this.properties=properties;
	}

	public Set getProperties(){
		return properties;
	}

        public void setPersons(Set persons){
                this.persons=persons;
        }

        public Set getPersons(){
                return persons;
        }

}
