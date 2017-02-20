package com.founder.web.term;

import com.founder.web.dto.PagingDto;

public class MapCodeSearchCondition extends PagingDto {
	private String sourceTable;
	private String sourceName;
	private String targetTable;
	private String targetName;
	private String sourceCode_y;
	private String sourceCodeContent_y;
	private String targetCode_y;
	private String targetCodeContent_y;
	private String targetCode;
	private String sourceCode;
	private String srcUniKey;
	private String tarUniKey;
	private String srcUniKey_y;
	private String tarUniKey_y;
	public String getSrcUniKey_y() {
		return srcUniKey_y;
	}
	public void setSrcUniKey_y(String srcUniKey_y) {
		this.srcUniKey_y = srcUniKey_y;
	}
	public String getTarUniKey_y() {
		return tarUniKey_y;
	}
	public void setTarUniKey_y(String tarUniKey_y) {
		this.tarUniKey_y = tarUniKey_y;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	public String getSrcUniKey() {
		return srcUniKey;
	}
	public void setSrcUniKey(String srcUniKey) {
		this.srcUniKey = srcUniKey;
	}
	public String getTarUniKey() {
		return tarUniKey;
	}
	public void setTarUniKey(String tarUniKey) {
		this.tarUniKey = tarUniKey;
	}
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
	public String getTargetCode() {
		return targetCode;
	}
	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}
	public String getSourceCode_y() {
		return sourceCode_y;
	}
	public void setSourceCode_y(String sourceCode_y) {
		this.sourceCode_y = sourceCode_y;
	}
	public String getSourceCodeContent_y() {
		return sourceCodeContent_y;
	}
	public void setSourceCodeContent_y(String sourceCodeContent_y) {
		this.sourceCodeContent_y = sourceCodeContent_y;
	}
	public String getTargetCode_y() {
		return targetCode_y;
	}
	public void setTargetCode_y(String targetCode_y) {
		this.targetCode_y = targetCode_y;
	}
	public String getTargetCodeContent_y() {
		return targetCodeContent_y;
	}
	public void setTargetCodeContent_y(String targetCodeContent_y) {
		this.targetCodeContent_y = targetCodeContent_y;
	}
	public String getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(String sourceTable) {
		this.sourceTable = sourceTable;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getTargetTable() {
		return targetTable;
	}
	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
}
