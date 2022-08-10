package io.maker.extension.utils.print.table;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.maker.extension.utils.print.util.PrintUtil;

public class TableHeader {

    public List<TableCell> tableCells;

    public TableHeader(){
        this.tableCells = new ArrayList<>();
    }

    public void addHead(TableCell tableCell){
        tableCells.add(tableCell);
    }

    public void addHeads(List<TableCell> headers){
        tableCells.addAll(headers);
    }

    public boolean isEmpty(){
        return tableCells == null || tableCells.isEmpty();
    }

    /**
     * print header including top and bottom sep
     * @param columnWidths max width of each column
     * @param horizontalSep char of h-sep, default '-'
     * @param verticalSep char of v-sep, default '|'
     * @param joinSep char of corner, default '+'
     * @return like:
     * +------------+--------------+------------+
     * | one        | two          | three      |
     * bottom will be printed by the body, for more completely output when there is no header sometimes
     */
	public List<String> print(int[] columnWidths, String horizontalSep, String verticalSep, String joinSep) {
		List<String> result = new ArrayList<>();
		if (!isEmpty()) {
			// top horizontal sep line
			result.addAll(PrintUtil.printLineSep(columnWidths, horizontalSep, verticalSep, joinSep));
			// header row
			result.addAll(PrintUtil.printRows(Collections.singletonList(tableCells), columnWidths, verticalSep));
		}
		return result;
	}
}
