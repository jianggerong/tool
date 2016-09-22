package com.kit.util;

/**
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Hongten
 * @created 2014-5-18
 */
public class ReadExcel {

	public static HSSFWorkbook readXls(File path) throws IOException {
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		return hssfWorkbook;
	}

	public static XSSFWorkbook readXlsx(File path) throws IOException {
		InputStream is = new FileInputStream(path);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		return xssfWorkbook;
	}

	@SuppressWarnings("static-access")
	public static String getValue(XSSFCell xssfRow) {
		if (xssfRow.getCellType() == xssfRow.CELL_TYPE_BOOLEAN) {
			return String.valueOf(xssfRow.getBooleanCellValue());
		} else if (xssfRow.getCellType() == xssfRow.CELL_TYPE_NUMERIC) {
			return String.valueOf(xssfRow.getNumericCellValue());
		} else {
			return String.valueOf(xssfRow.getStringCellValue());
		}
	}

	@SuppressWarnings("static-access")
	public static String getValue(HSSFCell hssfCell) {
		if(hssfCell==null){
			return null;
		}
		
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
	
	/**
	 * 判断excel的版本：
	 * @param fileName
	 * @return 0：不是excel  1:2003版本     2:2007版本
	 */
	public static int judgeExcelVersion(String fileName){
		if(fileName.matches("^.+\\.(?i)(xls)$")){
			return 1;
		}else if(fileName.matches("^.+\\.(?i)(xlsx)$")){
			return 2;
		}else{
			return 0;
		}
	}
	
	public static boolean deleteFile(Workbook wb, String fileName){
		try {
			if(wb!=null){
				wb.close();
			}
			if(new File(fileName).isAbsolute()){
				new File(fileName).delete();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(fileName+"没有正确关闭");
			return false;
		}
		return true;
	}
	
	public static String getValue(Cell cell) {
		if(cell==null){
			return "";
		}
		String cellStr = "";
		switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_STRING:  
            // 读取String  
            cellStr = cell.getStringCellValue().toString();  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            // 得到Boolean对象的方法  
            cellStr = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case Cell.CELL_TYPE_NUMERIC:  
            // 先看是否是日期格式  
            if (DateUtil.isCellDateFormatted(cell)) {  
                cellStr = formatTime(cell.getDateCellValue());
                if(cellStr.contains("/")){
                	cellStr = ReadExcel.formatStringTime(cellStr);
    			}else if(cellStr.contains("-")){
    				cellStr = ReadExcel.formatStrTime(cellStr);
    			}
            } else {  
                // 读取数字 
            	DecimalFormat df = new DecimalFormat("0");  
            	cellStr = df.format(cell.getNumericCellValue());  
            }
            break;  
        case Cell.CELL_TYPE_FORMULA:  
            // 读取公式  
            cellStr = cell.getCellFormula().toString();  
            break;  
		}
		if(null!=cellStr){
			return cellStr.trim();
		}
		return "";
	}
	
	public static String formatTime(Date date) {
		String result = null;
		if(date != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			result = sdf.format(date);  
		}
		return result;  
	}
	/**
	 * 处理字符串“2016-1-15”或者“2016-1-15 12:45:59”
	 * @param s
	 * @return
	 */
	public static String formatStrTime(String s){
		if(null==s || s.isEmpty()){
			return null;
		}
		String result = null;
		if(s.contains("-")){
			String[] str = s.split("-");
			if(str.length!=3){
				return result;
			}
			try {
				Date date = null;
				Calendar c=Calendar.getInstance();
				if(Integer.valueOf(str[0])>c.get(Calendar.YEAR) || Integer.valueOf(str[1])>12){
					return null;
				}
				StringBuilder sb = new StringBuilder();
				sb.append(str[0]);
				sb.append("-");
				sb.append(str[1]);
				sb.append("-");
				if(str[2].contains(" ")){
					String[] str2 = str[2].split(" ");
					if(Integer.valueOf(str2[0])>31){
						return null;
					}else{
						if(null!=str2[1] && !str2[1].isEmpty() && str2[1].contains(":")){
							String[] ss = str2[1].split(":");
							if(ss.length!=3){
								return null;
							}
							if(Integer.valueOf(ss[0])>23 || Integer.valueOf(ss[1])>59 || Integer.valueOf(ss[2])>59){
								return null;
							}
							sb.append(str[2]);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							date = sdf.parse(sb.toString());
						}
					}
				}
				if(null==date){
					sb.append(str[2]);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					date = sdf.parse(sb.toString());
				}
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    result = sdf2.format(date);
			} catch (ParseException e) {
				return null;
			}
		}
        return result;  
    
	}
	
	/**
	 * 处理字符串“2016/1/15”或者“2016/1/15 12:45:59”
	 * @param s
	 * @return
	 */
	public static String formatStringTime(String s) {
		if(null==s || s.isEmpty()){
			return null;
		}
		String result = null;
		if(s.contains("/")){
			String[] str = s.split("/");
			if(str.length!=3){
				return result;
			}
			try {
				Date date = null;
				Calendar c=Calendar.getInstance();
				if(Integer.valueOf(str[0])>c.get(Calendar.YEAR) || Integer.valueOf(str[1])>12){
					return null;
				}
				StringBuilder sb = new StringBuilder();
				sb.append(str[0]);
				sb.append("-");
				sb.append(str[1]);
				sb.append("-");
				if(str[2].contains(" ")){
					String[] str2 = str[2].split(" ");
					if(Integer.valueOf(str2[0])>31){
						return null;
					}else{
						if(null!=str2[1] && !str2[1].isEmpty() && str2[1].contains(":")){
							String[] ss = str2[1].split(":");
							if(ss.length!=3){
								return null;
							}
							if(Integer.valueOf(ss[0])>23 || Integer.valueOf(ss[1])>59 || Integer.valueOf(ss[2])>59){
								return null;
							}
							sb.append(str[2]);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							date = sdf.parse(sb.toString());
						}
					}
				}
				if(null==date){
					sb.append(str[2]);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					date = sdf.parse(sb.toString());
				}
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    result = sdf2.format(date);
			} catch (ParseException e) {
				return null;
			}
		}
        return result;  
    }
}