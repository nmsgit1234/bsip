package com.nms.catalog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "item"
})
public class Item {

	protected List<Item> item;
    @XmlAttribute
    protected String text;
    @XmlAttribute
    protected Byte child;
    @XmlAttribute
    protected Integer id;
    @XmlAttribute
    protected String im0;
    @XmlAttribute
    protected String im1;
    @XmlAttribute
    protected String im2;
    @XmlAttribute
    protected Byte call;
    @XmlAttribute
    protected Byte select;
    
    public List<Item> getItem() {
        if (item == null) {
            item = new ArrayList<Item>();
        }
        return this.item;
    }
    
    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setText(String value) {
        this.text = value;
    }

    /**
     * Gets the value of the child property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getChild() {
        return child;
    }

    /**
     * Sets the value of the child property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setChild(Byte value) {
        this.child = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the im0 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIm0() {
        return im0;
    }

    /**
     * Sets the value of the im0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIm0(String value) {
        this.im0 = value;
    }

    /**
     * Gets the value of the im1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIm1() {
        return im1;
    }

    /**
     * Sets the value of the im1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIm1(String value) {
        this.im1 = value;
    }

    /**
     * Gets the value of the im2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIm2() {
        return im2;
    }

    /**
     * Sets the value of the im2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIm2(String value) {
        this.im2 = value;
    }

    /**
     * Gets the value of the call property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getCall() {
        return call;
    }

    /**
     * Sets the value of the call property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setCall(Byte value) {
        this.call = value;
    }

    /**
     * Gets the value of the select property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getSelect() {
        return select;
    }

    /**
     * Sets the value of the select property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setSelect(Byte value) {
        this.select = value;
    }

}
