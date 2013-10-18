package com.jinglining.core;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Condition {

	private String propertyName;
	private String matchType;
	private String value;
	
	public Condition(){}
	public Condition(String propertyName,String matchType,String value){
		
			this.propertyName = propertyName;
			this.matchType = matchType;
			this.value = value;
	}
	
	public static List<Condition> buildConditionByRequest(HttpServletRequest request){
		
		List<Condition> list = new ArrayList<Condition>();
			
		@SuppressWarnings("unchecked")
		Enumeration<String> en = request.getParameterNames();
		while(en.hasMoreElements()){
			
			String paraName = en.nextElement();
			
			if(paraName.startsWith("q_")){
				String[] str;
				if(paraName.contains("_OR_")){
					str = paraName.split("_",3);
				} else {
					str = paraName.split("_");
				}
				if(str.length != 3){
					throw new IllegalArgumentException("��ѯ������д����!");
				} else {
					String mType = str[1];
					String pName = str[2];
					String value = request.getParameter(paraName);
					
					if(value != null && ! value.isEmpty()){
						
						Condition c = new Condition(pName, mType, value);
						request.setAttribute(paraName, value);
						list.add(c);
					}
					
				}
				
			}
		}
		return list;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
