package com.nms.util.ui;

public class RequestParamValue {
	// com.bsi.common.beans.Property:name,propertiesSet=Medium

	/**
	 * For example:
	 * 
	 * public class Service{
	 * private Long nodeId; 
	 * private Long prntNodeId; 
	 * private String name;
	 * private String description; 
	 * private Set properties = new HashSet();
	 * 
	 * com.bsi.common.beans.Property:name,propertiesSet=Medium
	 * 
	 * nameOfParentClassVariable=properties
	 * classOfParentClassVariable=com.bsi.common.beans.Property
	 * typeOfParentClassVarible=Set
	 * nameofChildClassVarible=name
	 * classOfChildClassVariable=String
	 * typeOfChildClassVariable=
	 * 
	 */

	String nameOfParentClassVariable = null;
	String classOfParentClassVariable = null;
	String typeOfParentClassVarible = null;
	String nameofChildClassVarible = null;
	String classOfChildClassVariable = null;
	String typeOfChildClassVariable = null;

	public String getNameOfParentClassVariable() {
		return nameOfParentClassVariable;
	}

	public void setNameOfParentClassVariable(String nameOfParentClassVariable) {
		this.nameOfParentClassVariable = nameOfParentClassVariable;
	}

	public String getClassOfParentClassVariable() {
		return classOfParentClassVariable;
	}

	public void setClassOfParentClassVariable(String classOfParentClassVariable) {
		this.classOfParentClassVariable = classOfParentClassVariable;
	}

	public String getTypeOfParentClassVarible() {
		return typeOfParentClassVarible;
	}

	public void setTypeOfParentClassVarible(String typeOfParentClassVarible) {
		this.typeOfParentClassVarible = typeOfParentClassVarible;
	}

	public String getNameofChildClassVarible() {
		return nameofChildClassVarible;
	}

	public void setNameofChildClassVarible(String nameofChildClassVarible) {
		this.nameofChildClassVarible = nameofChildClassVarible;
	}

	public String getClassOfChildClassVariable() {
		return classOfChildClassVariable;
	}

	public void setClassOfChildClassVariable(String classOfChildClassVariable) {
		this.classOfChildClassVariable = classOfChildClassVariable;
	}

	public String getTypeOfChildClassVariable() {
		return typeOfChildClassVariable;
	}

	public void setTypeOfChildClassVariable(String typeOfChildClassVariable) {
		this.typeOfChildClassVariable = typeOfChildClassVariable;
	}

}
