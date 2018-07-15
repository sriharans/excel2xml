package com.excel.xml.excel2xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class Snippet {
	

	public static void main(String[] args) throws Exception {

		Snippet s = new Snippet();
		
		s.exceldata(s.getProperty());
		
	}

	@SuppressWarnings("deprecation")
	public MultiValueMap<String, String>  exceldata(List<ExcelProp> prop) throws FileNotFoundException, IOException {

		
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("Book1.xlsx").getFile());
		InputStream ExcelFileToRead = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
		MultiValueMap<String, String> xmlmap = new LinkedMultiValueMap<>();
		List<String> strList = new ArrayList<>();
		
		for (ExcelProp property : prop) {
			XSSFRow row;
			XSSFCell cell;
			
			XSSFSheet sheet=wb.getSheet(property.getSheetName());
			Iterator rows = sheet.rowIterator();

			while (rows.hasNext()) {
				row = (XSSFRow) rows.next();
				Iterator cells = row.cellIterator();

				while (cells.hasNext()) {
					cell = (XSSFCell) cells.next();

					if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING && (cell.getColumnIndex() == Util.columnStrtoInt(property.getXmlColumn()))) {
						if (!cell.getStringCellValue().isEmpty() && !(cell.getStringCellValue().length() == 0)) {
							String Address=sheet.getSheetName()+" "+cell.getAddress().toString();
							String check = cell.getStringCellValue();
							
							if (check.startsWith("<")) {
								check=check.substring(1,check.length()-1);
								List<String>xmltagsList= new ArrayList<String>();
								String[]xmltags = check.split("\\r?\\n");
								
								for (String string : xmltags) {
									string=string.replace("><", "/");
									string=string.replace("<", "");
									string=string.replace(">", "");
									string=string.replaceAll("\\s+", "");
									xmltagsList.add(string);
								}
								xmlmap.put(Address, xmltagsList);
								strList.add(check);
								
							}
						}
					}

					else {
						
						// U Can Handel Boolean, Formula, Errors
					}
				}

			}
		}
		
		System.out.println(strList.size());
		System.out.println(xmlmap.size());
		
		return xmlmap;
	}
	
	public List<ExcelProp> getProperty() throws Exception{
		
		List<ExcelProp>propList= new ArrayList<>();
		List<String> list = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/resources/sheetdata.txt"))) {
			list = br.lines().collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (String string : list) {
			ExcelProp prop = new ExcelProp();
			String[]strarrays=string.split("#");
			prop.setSheetName(strarrays[0]);
			prop.setDynamicColumn(strarrays[1]);
			prop.setXmlColumn(strarrays[2]);
			propList.add(prop);
		}
		return propList;
	}
}
