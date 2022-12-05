package io.devpl.spring.extension.utils.print;

import io.devpl.sdk.util.StringUtils;
import io.devpl.spring.extension.utils.print.enums.Alignment;
import io.devpl.spring.extension.utils.print.enums.NullPolicy;
import io.devpl.spring.extension.utils.print.table.TableBody;
import io.devpl.spring.extension.utils.print.table.TableCell;
import io.devpl.spring.extension.utils.print.table.TableHeader;
import io.devpl.spring.extension.utils.print.util.StringPadUtil;

import java.util.ArrayList;
import java.util.List;

public class ConsoleTable {

	private TableHeader tableHeader;
	private TableBody tableBody;
	private String lineSep = "\n";
	private String verticalSep = "|";
	private String horizontalSep = "-";
	private String joinSep = "+";
	private int[] columnWidths;
	private NullPolicy nullPolicy = NullPolicy.EMPTY_STRING;
	private boolean restrict = false;

	private ConsoleTable() {
	}

	void print() {
		System.out.println(getContent());
	}

	String getContent() {
		return toString();
	}

	List<String> getLines() {
		List<String> lines = new ArrayList<>();
		if ((tableHeader != null && !tableHeader.isEmpty()) || (tableBody != null && !tableBody.isEmpty())) {
			lines.addAll(tableHeader.print(columnWidths, horizontalSep, verticalSep, joinSep));
			lines.addAll(tableBody.print(columnWidths, horizontalSep, verticalSep, joinSep));
		}
		return lines;
	}

	@Override
	public String toString() {
		return StringUtils.join(getLines(), lineSep);
	}

	public static class ConsoleTableBuilder {
		ConsoleTable consoleTable = new ConsoleTable();

		public ConsoleTableBuilder() {
			consoleTable.tableHeader = new TableHeader();
			consoleTable.tableBody = new TableBody();
		}

		public ConsoleTableBuilder addHead(TableCell tableCell) {
			consoleTable.tableHeader.addHead(tableCell);
			return this;
		}

		public ConsoleTableBuilder addRow(List<TableCell> row) {
			consoleTable.tableBody.addRow(row);
			return this;
		}

		public ConsoleTableBuilder addHeaders(List<TableCell> headers) {
			consoleTable.tableHeader.addHeads(headers);
			return this;
		}

		public ConsoleTableBuilder addRows(List<List<TableCell>> rows) {
			consoleTable.tableBody.addRows(rows);
			return this;
		}

		public ConsoleTableBuilder lineSep(String lineSep) {
			consoleTable.lineSep = lineSep;
			return this;
		}

		public ConsoleTableBuilder verticalSep(String verticalSep) {
			consoleTable.verticalSep = verticalSep;
			return this;
		}

		public ConsoleTableBuilder horizontalSep(String horizontalSep) {
			consoleTable.horizontalSep = horizontalSep;
			return this;
		}

		public ConsoleTableBuilder joinSep(String joinSep) {
			consoleTable.joinSep = joinSep;
			return this;
		}

		public ConsoleTableBuilder nullPolicy(NullPolicy nullPolicy) {
			consoleTable.nullPolicy = nullPolicy;
			return this;
		}

		public ConsoleTableBuilder restrict(boolean restrict) {
			consoleTable.restrict = restrict;
			return this;
		}

		public ConsoleTable build() {
			// compute max column widths
			if (!consoleTable.tableHeader.isEmpty() || !consoleTable.tableBody.isEmpty()) {
				List<List<TableCell>> allRows = new ArrayList<>();
				allRows.add(consoleTable.tableHeader.tableCells);
				allRows.addAll(consoleTable.tableBody.rows);
				int maxColumn = allRows.stream().map(List::size).mapToInt(size -> size).max().getAsInt();
				int minColumn = allRows.stream().map(List::size).mapToInt(size -> size).min().getAsInt();
				if (maxColumn != minColumn && consoleTable.restrict) {
					throw new IllegalArgumentException(
							"number of columns for each row must be the same when strict mode used.");
				}
				consoleTable.columnWidths = new int[maxColumn];
				for (List<TableCell> row : allRows) {
					for (int i = 0; i < row.size(); i++) {
						TableCell tableCell = row.get(i);
						if (tableCell == null || tableCell.getValue() == null) {
							tableCell = consoleTable.nullPolicy.getCell(tableCell);
							row.set(i, tableCell);
						}
						int length = StringPadUtil.strLength(tableCell.getValue());
						if (consoleTable.columnWidths[i] < length) {
							consoleTable.columnWidths[i] = length;
						}
					}
				}
			}
			return consoleTable;
		}
	}

	public static void main(String[] args) {
		List<TableCell> header = new ArrayList<TableCell>() {
			{
				add(new TableCell("name"));
				add(new TableCell("email"));
				add(new TableCell("tel"));
			}
		};
		List<List<TableCell>> body = new ArrayList<List<TableCell>>() {
			{
				add(new ArrayList<TableCell>() {
					{
						add(new TableCell("kat"));
						add(new TableCell(Alignment.CENTER, "kat@gimal.com"));
						add(new TableCell(Alignment.RIGHT, "54321"));
					}
				});
				add(new ArrayList<TableCell>() {
					{
						add(new TableCell("ashe"));
						add(new TableCell("ashe_111@hotmail.com"));
						add(new TableCell("9876543中文测试210"));
					}
				});
				add(new ArrayList<TableCell>() {
					{
						add(null);
						add(new TableCell(null));
						add(new TableCell(Alignment.LEFT, "11"));
					}
				});
			}
		};

		// default
		new ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();

		System.out.println("===============================");

		// 中文
		header = new ArrayList<TableCell>() {
			{
				add(new TableCell("姓名name"));
				add(new TableCell("电子邮箱email"));
				add(new TableCell("电话号码tel"));
			}
		};
		body = new ArrayList<List<TableCell>>() {
			{
				add(new ArrayList<TableCell>() {
					{
						add(new TableCell("凯特kat"));
						add(new TableCell(Alignment.CENTER, "kat@gimal.com"));
						add(new TableCell(Alignment.RIGHT, "54321"));
					}
				});
				add(new ArrayList<TableCell>() {
					{
						add(new TableCell("艾希ashe"));
						add(new TableCell("ashe_111@hotmail.com"));
						add(new TableCell("9876543210"));
					}
				});
				add(new ArrayList<TableCell>() {
					{
						add(new TableCell("這是一串很長的繁體中文"));
						add(new TableCell("これは長い日本語です"));
						add(new TableCell(Alignment.LEFT, "11这是一串很长的中文"));
					}
				});
			}
		};
		new ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();

		// no header
		// new ConsoleTable.ConsoleTableBuilder().addRows(body).build().print();

		// restrict
		// header.add(new Cell("not restrict"));
		// new
		// ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).restrict(false).build().print();
		// new
		// ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).restrict(true).build().print();

		// "null"
		// new
		// ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).nullPolicy(NullPolicy.NULL_STRING).build().print();
		// new
		// ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).nullPolicy(NullPolicy.THROW).build().print();

		// line sep
		// new
		// ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).lineSep("\n\n").build().print();

		// vertical sep & horizontal sep & join sep
		// new
		// ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).verticalSep("*").horizontalSep("*").joinSep("*").build().print();
	}
}
