package io.maker.extension.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XlsxReader {

	public List<User> read(File file) throws InvalidFormatException, IOException {
		Workbook workbook = new XSSFWorkbook(file);
	    Sheet sheet = workbook.getSheetAt(0);

	    List<User> result = new ArrayList<User>();

	    int rowStart = sheet.getFirstRowNum() + 1;
	    int rowEnd = sheet.getLastRowNum();

	    for(int i = rowStart; i <= rowEnd; i++) {
	    	Row row = sheet.getRow(i);
	    	User user = this.getUserFromRow(row);
	    	if(user != null) result.add(user);
	    }
	    workbook.close();
	    return result;
	}

	protected User getUserFromRow(Row row) {
		if(row == null) return null;
    	int current = row.getFirstCellNum() + 1;
    	Cell cell = row.getCell(current);
    	if(null != cell) {
    		User user = new User();
    		user.setUsername(cell.getStringCellValue());
        	current++;

        	cell = row.getCell(current);
        	user.setPassword(cell.getStringCellValue());
        	current++;

        	cell = row.getCell(current);
        	user.setNickname(cell.getStringCellValue());

        	return user;
    	}
    	return null;
	}
}
