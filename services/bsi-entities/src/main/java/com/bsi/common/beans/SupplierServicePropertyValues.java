package com.bsi.common.beans;
// Generated Oct 20, 2014 7:01:30 PM by Hibernate Tools 3.2.2.GA



/**
 * SupplierServicePropertyValues generated by hbm2java
 */
public class SupplierServicePropertyValues  implements java.io.Serializable {


     private String property_id;
     private String property_value;

    public SupplierServicePropertyValues() {
    }

    public SupplierServicePropertyValues(String property_id, String property_value) {
       this.property_id = property_id;
       this.property_value = property_value;
    }
   
    public String getProperty_id() {
        return this.property_id;
    }
    
    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }
    public String getProperty_value() {
        return this.property_value;
    }
    
    public void setProperty_value(String property_value) {
        this.property_value = property_value;
    }




}


