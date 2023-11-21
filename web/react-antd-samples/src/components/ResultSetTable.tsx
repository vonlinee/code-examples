import React, { useState } from "react";
import { useVirtualizer } from '@tanstack/react-virtual'
import "./index.css";

import {
  useReactTable,
  ColumnResizeMode,
  getCoreRowModel,
  ColumnDef,
  flexRender,
  Row,
} from "@tanstack/react-table";
import ResultSetTableHeader from "./ResultSetTableHeader";
import ResultSetTableCell from "./ResultSetTableCell";

type Person = {
  firstName: string;
  lastName: string;
  age: number;
  visits: number;
  status: string;
  progress: number;
};

let defaultData: Person[] = [
  {
    firstName: "tanner",
    lastName: "linsley",
    age: 24,
    visits: 100,
    status: "In Relationship",
    progress: 50,
  },
  {
    firstName: "tandy",
    lastName: "miller",
    age: 40,
    visits: 40,
    status: "Single",
    progress: 80,
  },
];

for (let i = 0; i < 2; i++) {
  for (let j = 0; j < 100; j++) {
    const element = Object.assign(defaultData[i], {}) as Person;
    defaultData.push(element);
  }
}

const defaultColumns: ColumnDef<Person>[] = [
  {
    id: "serial",
    header: (props) => {
      return <>1</>;
    },
    cell: (cell) => (
      <div
        style={{
          textAlign: "center",
        }}
      >
        {cell.row.index}
      </div>
    ),
    minSize: 50,
    maxSize: 50,
    accessorFn: (originalRow: Person, index: number) => index,
    footer: (props) => props.column.id,
  },
  {
    header: ResultSetTableHeader,
    accessorKey: "firstName",
    cell: ResultSetTableCell,
    minSize: 100,
    footer: (props) => props.column.id,
  },
  {
    accessorKey: "age",
    cell: ResultSetTableCell,
    header: ResultSetTableHeader,
    minSize: 100,
    footer: (props) => props.column.id,
  },
  {
    accessorKey: "visits",
    header: ResultSetTableHeader,
    cell: ResultSetTableCell,
    minSize: 100,
    footer: (props) => props.column.id,
  },
  {
    accessorKey: "status",
    header: ResultSetTableHeader,
    cell: ResultSetTableCell,
    minSize: 100,
    footer: (props) => props.column.id,
  },
  {
    accessorKey: "progress",
    header: ResultSetTableHeader,
    cell: ResultSetTableCell,
    minSize: 100,
    footer: (props) => props.column.id,
  },
];

function ResultSetTable() {
  const [data, setData] = React.useState(() => [...defaultData]);
  const [columns] = React.useState<typeof defaultColumns>(() => [
    ...defaultColumns,
  ]);

  const [columnResizeMode, setColumnResizeMode] =
    React.useState<ColumnResizeMode>("onChange");

  const rerender = React.useReducer(() => ({}), {})[1];

  const [currentCell, setCurrentCell] = useState<HTMLTableCellElement | null>(
    null
  );

  const table = useReactTable({
    data,
    columns,
    columnResizeMode,
    getCoreRowModel: getCoreRowModel(),
    // debugTable: true,
    // debugHeaders: true,
    // debugColumns: true,
    meta: {
      currentCell: null,
    },
  });

  const tableContainerRef = React.useRef<HTMLDivElement>(null);
  const { rows } = table.getRowModel();
  const rowVirtualizer = useVirtualizer({
    count: rows.length,
    getScrollElement: () => tableContainerRef.current,
    estimateSize: () => 34,
    overscan: 20,
  });

  const totalSize = rowVirtualizer.getTotalSize()
  const virtualRows = rowVirtualizer.getVirtualItems()

  const paddingTop = virtualRows.length > 0 ? virtualRows?.[0]?.start || 0 : 0;
  const paddingBottom =
    virtualRows.length > 0
      ? totalSize - (virtualRows?.[virtualRows.length - 1]?.end || 0)
      : 0;

  return (
    <div className="view">
      <div className="wrapper">
        <table
          {...{
            style: {
              width: table.getCenterTotalSize(),
              verticalAlign: "middle",
              border: "1px solid lightgray",
              borderCollapse: "collapse",
              borderSpacing: 0,
              tableLayout: "fixed",
            },
          }}
        >
          <thead>
            {table.getHeaderGroups().map((headerGroup) => (
              <tr key={headerGroup.id}>
                {headerGroup.headers.map((header) =>
                  header.id == "serial" ? (
                    <th className="sticky-col first-col">
                      {header.isPlaceholder
                        ? null
                        : flexRender(
                            header.column.columnDef.header,
                            header.getContext()
                          )}
                    </th>
                  ) : (
                    <th
                      {...{
                        key: header.id,
                        colSpan: header.colSpan,
                        style: {
                          width: header.getSize(),
                          minWidth: "200px !important",
                          border: "1px solid lightgray",
                        },
                      }}
                    >
                      <div
                        style={{
                          width: "100%",
                        }}
                      >
                        {header.isPlaceholder
                          ? null
                          : flexRender(
                              header.column.columnDef.header,
                              header.getContext()
                            )}
                      </div>
                    </th>
                  )
                )}
              </tr>
            ))}
          </thead>
          <tbody>
            {paddingTop > 0 && (
              <tr>
                <td style={{ height: `${paddingTop}px` }} />
              </tr>
            )}
            {virtualRows.map((virtualRow: any) => {
              const row = rows[virtualRow.index] as Row<Person>;
              return (
                <tr key={row.id}>
                  {row.getVisibleCells().map((cell) =>
                    cell.id == "serial" ? (
                      <td className="ticky-col first-col">
                        {flexRender(
                          cell.column.columnDef.cell,
                          cell.getContext()
                        )}
                      </td>
                    ) : (
                      <td
                        {...{
                          key: cell.id,
                          style: {
                            width: cell.column.getSize(),
                            border: "1px solid lightgray",
                            padding: 0,
                            margin: 0,
                          },
                        }}
                        onFocus={(event) => {
                          event.currentTarget.style.border =
                            "2px solid #98FB98";
                        }}
                        onBlur={(event) => {
                          event.currentTarget.style.border =
                            "1px solid lightgray";
                        }}
                      >
                        {flexRender(
                          cell.column.columnDef.cell,
                          cell.getContext()
                        )}
                      </td>
                    )
                  )}
                </tr>
              );
            })}
            {paddingBottom > 0 && (
              <tr>
                <td style={{ height: `${paddingBottom}px` }} />
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ResultSetTable;
