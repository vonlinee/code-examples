package io.maker.extension.utils.print;

import java.util.ArrayList;
import java.util.List;

import io.maker.extension.utils.print.enums.Alignment;
import io.maker.extension.utils.print.enums.NullPolicy;
import io.maker.extension.utils.print.table.TableCell;

public class Test1 {
	
	public static void main(String[] args) {
		
		List<TableCell> header = new ArrayList<TableCell>(){{
		    add(new TableCell("name"));
		    add(new TableCell("email"));
		    add(new TableCell("tel"));
		}};
		List<List<TableCell>> body = new ArrayList<List<TableCell>>(){{
		    add(new ArrayList<TableCell>(){{
		        add(new TableCell("kat"));
		        add(new TableCell(Alignment.CENTER,"kat@gimal.com"));
		        add(new TableCell(Alignment.RIGHT,"54321"));
		    }});
		    add(new ArrayList<TableCell>(){{
		        add(new TableCell("ashe"));
		        add(new TableCell("ashe_111@hotmail.com"));
		        add(new TableCell("9876543210"));
		    }});
		    add(new ArrayList<TableCell>(){{
		        add(null);
		        add(new TableCell(null));
		        add(new TableCell(Alignment.LEFT,"11"));
		    }});
		}};
		//default
		new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();

		//no header
		new ConsoleTable.ConsoleTableBuilder().addRows(body).build().print();

		//restrict
		header.add(new TableCell("not restrict"));
		//new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).restrict(false).build().print();
		new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).restrict(true).build().print();

		//"null"
		new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).nullPolicy(NullPolicy.NULL_STRING).build().print();
		new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).nullPolicy(NullPolicy.THROW).build().print();

		//line sep
		new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).lineSep("\n\n").build().print();

		//vertical sep & horizontal sep & join sep
		new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).verticalSep("*").horizontalSep("*").joinSep("*").build().print();
	}
}
