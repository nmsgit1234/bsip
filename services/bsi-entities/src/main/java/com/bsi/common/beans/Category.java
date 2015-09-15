package com.bsi.common.beans;

import java.util.Collection;
import java.util.ArrayList;

public class Category{

  public Long id;
  public String name;
  private Collection items = new ArrayList();

 	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Collection getItems() {
		return items;
	}

	public void setItems(Collection items) {
		this.items = items;
	}
}
