package com.bsi.client.session;

import com.bsi.common.beans.Person;
import java.util.Set;

public class User
{
  private Person person;
  private String localeString = "en_US";
  private Set roles;

  public void setPerson(Person person)
  {
    this.person=person;
  }
  public Person getPerson()
  {
    return this.person;
  }

  public void setLocaleString(String localeString)
  {
    this.localeString=localeString;
  }

  public String getLocaleString()
  {
    return this.localeString;
  }

  public void setRoles(Set roles)
  {
    this.roles=roles;
  }

  public  Set getRoles()
  {
    return roles;
  }

  public boolean hasRole(String roleName)
  {
    boolean hasRole = false;

    if (roles != null && roles.contains(roleName))
      hasRole=true;
      return hasRole;
  }

}
