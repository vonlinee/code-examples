package io.maker.extension.poi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * Map<int[][], Object> LinkedHashMap 有序的Map
 * int[]:2个元素的数组，arr[0]放该单元格的行号，arr[1]放该单元格的列号，从1开始
 */
public class MapRowProcessor implements RowProcessor<Map<int[], Object>> {

	@Override
	public Map<int[], Object> process(Row row) {
		int rowNum = row.getRowNum();
		// 获取该行第一个元素的列号，以0开始
		short firstCellNum = row.getFirstCellNum();
		// 获取该行最后一个元素的列号，以1开始
		short lastCellNum = row.getLastCellNum(); 
		// System.out.println("Excel第" + (rowNum + 1) + "行，存在" + (lastCellNum - firstCellNum) + "个");
		// 有序的Map
		Map<int[], Object> map = new LinkedHashMap<>();
		for (int i = firstCellNum; i < lastCellNum; i++) {
			Cell cell = row.getCell(i);
			if (cell == null) {
				map.put(new int[] {rowNum + 1, i + 1}, null);
				continue;
			}
			map.put(new int[] {rowNum + 1, i + 1}, getCellValue(cell));
		}
		return map;
	}

	private Object getCellValue(Cell cell) {
		CellType cellType = cell.getCellType();
		if (cellType == CellType.STRING) {
			return cell.getRichStringCellValue().getString();
		}
		if (cellType == CellType.NUMERIC) {
			return cell.getNumericCellValue();
		}
		if (cellType == CellType.BOOLEAN) {
			return cell.getBooleanCellValue();
		}
		if (cellType == CellType.FORMULA) { // 公式
			return cell.getCellFormula();
		}
		if (cellType == CellType.BLANK) {
			return "";
		}
		if (cellType == CellType.ERROR) {
			return "ERROR";
		}
		if (cellType == CellType._NONE) {
			return "NONE";
		}
		return cell.toString();
	}

}
