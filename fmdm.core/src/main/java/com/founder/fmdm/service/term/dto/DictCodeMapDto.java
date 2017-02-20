package com.founder.fmdm.service.term.dto;

import org.seasar.doma.Entity;

@Entity
public class DictCodeMapDto{
private  String srcUniKey;
private  String tarUniKey;
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
private  String sourceTable;
private String sourceName;
private String targetTable;
private String targetName;
private String sourceCode;
private String targetCode;
private String sourceCodeContent;
private String targetCodeContent;
private String sourceCodeVersion;
private String targetCodeVersion;
public String getSourceCodeVersion() {
	return sourceCodeVersion;
}
public void setSourceCodeVersion(String sourceCodeVersion) {
	this.sourceCodeVersion = sourceCodeVersion;
}
public String getTargetCodeVersion() {
	return targetCodeVersion;
}
public void setTargetCodeVersion(String targetCodeVersion) {
	this.targetCodeVersion = targetCodeVersion;
}
public String getSourceCode() {
	return sourceCode;
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
public String getSourceCodeContent() {
	return sourceCodeContent;
}
public void setSourceCodeContent(String sourceCodeContent) {
	this.sourceCodeContent = sourceCodeContent;
}
public String getTargetCodeContent() {
	return targetCodeContent;
}
public void setTargetCodeContent(String targetCodeContent) {
	this.targetCodeContent = targetCodeContent;
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

