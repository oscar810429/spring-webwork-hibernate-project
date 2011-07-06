/**
 * @(#)Area.java Nov 12, 2009
 * 
 * Copyright 2009 Mingda. All rights reserved.
 */
package com.painiu.core.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.painiu.core.model.IntegerEnum;

/**
 * <p>
 * <a href="Area.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: Area.java 7 2010-05-11 16:23:49Z zhangsf $
 */
public class Area extends BaseObject{
	
	public static class AreaType extends IntegerEnum implements Serializable {

		private static final long serialVersionUID = 8806559586000299687L;
	    public static final AreaType AREA_ENABLE 	= new AreaType(0);
		public static final AreaType AREA_DISABLE  	= new AreaType(1);

		private AreaType(int value) {
			super(value);
		}

		public static AreaType valueOf(int value) {
			return (AreaType) IntegerEnum.valueOf(AreaType.class, value);
		}
	}

	//~ Static fields/initializers =============================================

    private static final long serialVersionUID = 7349520650019746631L;
    
    //~ Instance fields ========================================================
    
    private Integer id;
    private String name;
    private String ename;
    private String aliasname1;
    private String aliasname2;
    private String areaPackage = new String("mainland");
    private Area parentArea;
    private String regionsPath;
    private Integer position;
    private Integer grade;
    private AreaType areaType = AreaType.AREA_ENABLE;
    private Set childAreas = new HashSet();

	//~ Constructors ===========================================================
    
    public Area(){}

	//~ Methods ================================================================

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getAreaPackage() {
		return areaPackage;
	}

	public void setAreaPackage(String areaPackage) {
		this.areaPackage = areaPackage;
	}

	public Area getParentArea() {
		return parentArea;
	}

	public void setParentArea(Area parentArea) {
		this.parentArea = parentArea;
	}

	public String getRegionsPath() {
		return regionsPath;
	}

	public void setRegionsPath(String regionsPath) {
		this.regionsPath = regionsPath;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public AreaType getAreaType() {
		return areaType;
	}

	public void setAreaType(AreaType areaType) {
		this.areaType = areaType;
	}
	
	public Set getChildAreas() {
		return childAreas;
	}

	public void setChildAreas(Set childAreas) {
		this.childAreas = childAreas;
	}
	
	public void addChildAreas(Area area){
		if(area==null){
			throw new IllegalArgumentException("cant't add a null area as child");
		}
		if(area.getParentArea()!=null){
			area.getParentArea().getChildAreas().remove(area);
			area.setParentArea(this);
			this.getChildAreas().add(area);
		}
	}
	
	/*
	 * @see com.mingda.core.model.BaseObject#equals()
	 */
	
	public boolean equals(Object object) {
		if (!(object instanceof Area)) {
			return false;
		}
		Area rhs = (Area) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.name, rhs.name)
				.append(this.areaType, rhs.areaType).isEquals();
	}

	/*
	 * @see com.mingda.core.model.BaseObject#hashCode()
	 */

	public int hashCode() {
		return new HashCodeBuilder(-576253229, -214754223)
		.append(this.id).append(this.name).append(this.areaType).toHashCode();
	}

	/*
	 * @see com.mingda.core.model.BaseObject#toString()
	 */
	
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id)
		.append("name", this.name)
		.append("areaType",this.areaType).toString();
	}

	/**
	 * @return the aliasname1
	 */
	public String getAliasname1() {
		return aliasname1;
	}

	/**
	 * @param aliasname1 the aliasname1 to set
	 */
	public void setAliasname1(String aliasname1) {
		this.aliasname1 = aliasname1;
	}

	/**
	 * @return the aliasname2
	 */
	public String getAliasname2() {
		return aliasname2;
	}

	/**
	 * @param aliasname2 the aliasname2 to set
	 */
	public void setAliasname2(String aliasname2) {
		this.aliasname2 = aliasname2;
	}
	
	
	


	//~ Accessors ==============================================================

}
