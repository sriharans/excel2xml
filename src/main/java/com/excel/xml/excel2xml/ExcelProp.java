package com.excel.xml.excel2xml;

import java.io.Serializable;

public class ExcelProp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sheetName;
	private String xmlColumn;
	private String dynamicColumn;
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getXmlColumn() {
		return xmlColumn;
	}
	public void setXmlColumn(String xmlColumn) {
		this.xmlColumn = xmlColumn;
	}
	public String getDynamicColumn() {
		return dynamicColumn;
	}
	public void setDynamicColumn(String dynamicColumn) {
		this.dynamicColumn = dynamicColumn;
	}
	

}
