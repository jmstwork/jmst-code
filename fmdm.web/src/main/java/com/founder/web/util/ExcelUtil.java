package com.founder.web.util;

import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.founder.fmdm.entity.DictCodeMap;

public class ExcelUtil {
	
	public static String readValue(Cell cell) {
		String value = "";
		if (cell == null) {
			return "NULL";
		}
		int rowType = cell.getCellType();
		if (rowType == Cell.CELL_TYPE_STRING) {
			value = cell.getStringCellValue();
		} else if (rowType == Cell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				String date = sdf.format(
						HSSFDateUtil.getJavaDate(cell.getNumericCellValue()))
						.toString();
				value = date.toString();
			} else {
				Double doubleValue = cell.getNumericCellValue();
				String str = doubleValue.toString();
				if (str.contains(".0")) {
					str = str.replace(".0", "");
				}
				Integer intValue = Integer.parseInt(str);
				value = intValue.toString();
			}
		} else if (rowType == Cell.CELL_TYPE_BOOLEAN) {
			Boolean booleanValue = cell.getBooleanCellValue();
			value = booleanValue.toString();
		} else if (rowType == Cell.CELL_TYPE_BLANK) {
			value = "";
		} else if (rowType == Cell.CELL_TYPE_FORMULA) {
			value = cell.getCellFormula();
		} else if (rowType == Cell.CELL_TYPE_ERROR) {
			value = "";
		}
		return value;
	}

	// 读取单元格的数据,把一行行的数据保存到map中，用map来过滤重复身份证的数据
	public static Map<Integer,DictCodeMap> readExcelFile(String fileName) throws Exception{

		Map<Integer,DictCodeMap> map = new HashMap<Integer,DictCodeMap>();
		try {
			FileInputStream in = new FileInputStream(fileName);

			Workbook wb;
			if (fileName.endsWith(".xls")) {
				// Excel2003
				wb = new HSSFWorkbook(in);
			} else {
				// Excel 2007
				wb = new XSSFWorkbook(in);
			}

			// 创建对工作表的引用
			Sheet sheet = wb.getSheetAt(0);
			// 遍历所有单元格，读取单元格
			int row_num = sheet.getLastRowNum();

			for (int i = 0; i <= row_num; i++) {
				Row row = sheet.getRow(i);
				if (i > 0) {
					// 得到每一行的单元格
					DictCodeMap settle = new DictCodeMap();
					if("NULL".equals(readValue(row.getCell(0))) || "".equals(readValue(row.getCell(0)))){
						continue;
					}
					settle.setSourceTable(readValue(row.getCell(0)));
					settle.setTargetTable(readValue(row.getCell(1)));
					settle.setSrcUniKey(readValue(row.getCell(2)));
					settle.setTarUniKey(readValue(row.getCell(3)));
					settle.setCreateBy(readValue(row.getCell(4)));
					settle.setCreateTime(filltime(readValue(row.getCell(5))));
					settle.setLastUpdateBy(readValue(row.getCell(6)));
					settle.setLastUpdateTime(filltime(readValue(row.getCell(7))));
					settle.setDeleteBy(readValue(row.getCell(8)));
					settle.setDeleteTime(filltime(readValue(row.getCell(9))));
					settle.setDeleteFlg(readValue(row.getCell(10)));
					settle.setUpdateCount(new Long(readValue(row.getCell(11))==""?"0":readValue(row.getCell(11))));
					settle.setItemVersion(new Long("".equals(readValue(row.getCell(12)))?"0":readValue(row.getCell(12))));
					settle.setOptStaus(readValue(row.getCell(13)));
					settle.setReleaseStatus(readValue(row.getCell(14)));
					settle.setUniKey(readValue(row.getCell(15)));
					map.put(new Integer(i), settle);
				}
			}

		} catch (Exception e) {
			throw e;

		} finally {
             
		}
		return map;
	}
	
	public static Date filltime(String readValue) {
		try {
			if(readValue != null && !"".equals(readValue)){
				DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				return sdf.parse(readValue);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
