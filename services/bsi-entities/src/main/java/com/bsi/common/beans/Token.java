package com.bsi.common.beans;

import java.util.HashMap;
import java.util.Set;


public class Token{

	private Long tokenId;
	private String tokenName;
	private String tokenDesc;
	private Set tokenValues;

	public void setTokenId(Long tokenId){
		this.tokenId=tokenId;
	}

	public Long getTokenId(){
		return tokenId;
	}

	public void setTokenName(String tokenName){
		this.tokenName=tokenName;
	}

	public String getTokenName(){
		return tokenName;
	}

	public void setTokenDesc(String tokenDesc){
		this.tokenDesc=tokenDesc;
	}

	public String getTokenDesc(){
		return tokenDesc;
	}

	public void setTokenValues(Set tokenValues){
		this.tokenValues=tokenValues;
	}

	public Set getTokenValues(){
		return tokenValues;
	}


}