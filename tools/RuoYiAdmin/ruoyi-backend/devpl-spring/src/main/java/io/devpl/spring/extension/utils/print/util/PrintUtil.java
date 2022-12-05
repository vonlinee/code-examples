package io.devpl.spring.extension.utils.print.util;

import io.devpl.spring.extension.utils.print.enums.Alignment;
import io.devpl.spring.extension.utils.print.table.TableCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintUtil {

	/**
	 * print sep line
	 * @param columnWidths  max width of each column
	 * @param horizontalSep char of h-sep, default '-'
	 * @param verticalSep   char of v-sep, default '|'
	 * @param joinSep       char of corner, default '+'
	 * @return like: +------------+--------------+------------+
	 */
	public static List<String> printLineSep(int[] columnWidths, String horizontalSep, String verticalSep,
			String joinSep) {
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < columnWidths.length; i++) {
			String l = String.join("", Collections.nCopies(columnWidths[i] + StringPadUtil.strLength(verticalSep) + 1, horizontalSep));
			line.append(joinSep).append(l).append(i == columnWidths.length - 1 ? joinSep : "");
		}
		return Collections.singletonList(line.toString());
	}

	/**
	 * print real data rows
	 * 
	 * @param rows         data rows
	 * @param columnWidths max width of each column
	 * @param verticalSep  char of v-sep, default '|'
	 * @return like: | super | broccoli | flexible | | assumption | announcement |
	 *         reflection | | logic | pleasant | wild |
	 */
	public static List<String> printRows(List<List<TableCell>> rows, int[] columnWidths, String verticalSep) {
		List<String> result = new ArrayList<>();
		for (List<TableCell> row : rows) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < row.size(); i++) {
				TableCell tableCell = row.get(i);
				if (tableCell == null) {
					tableCell = new TableCell("");
				}
				// add v-sep after last column
				String verStrTemp = i == row.size() - 1 ? verticalSep : "";
				Alignment alignment = tableCell.getAlign();
				switch (alignment) {
				case LEFT:
					sb.append(String.format("%s %s %s", verticalSep,
							StringPadUtil.rightPad(tableCell.getValue(), columnWidths[i]), verStrTemp));
					break;
				case RIGHT:
					sb.append(String.format("%s %s %s", verticalSep,
							StringPadUtil.leftPad(tableCell.getValue(), columnWidths[i]), verStrTemp));
					break;
				case CENTER:
					sb.append(String.format("%s %s %s", verticalSep,
							StringPadUtil.center(tableCell.getValue(), columnWidths[i]), verStrTemp));
					break;
				default:
					throw new IllegalArgumentException("wrong align : " + alignment.name());
				}
			}
			result.add(sb.toString());
		}
		return result;
	}

}
