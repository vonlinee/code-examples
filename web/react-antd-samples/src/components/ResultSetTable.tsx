import React from 'react'

import './index.css'

import {
  useReactTable,
  ColumnResizeMode,
  getCoreRowModel,
  ColumnDef,
  flexRender,
} from '@tanstack/react-table'
import ResultSetTableHeader from './ResultSetTableHeader'
import ResultSetTableCell from './ResultSetTableCell'

type Person = {
  firstName: string
  lastName: string
  age: number
  visits: number
  status: string
  progress: number
}

const defaultData: Person[] = [
  {
    firstName: 'tanner',
    lastName: 'linsley',
    age: 24,
    visits: 100,
    status: 'In Relationship',
    progress: 50,
  },
  {
    firstName: 'tandy',
    lastName: 'miller',
    age: 40,
    visits: 40,
    status: 'Single',
    progress: 80,
  },
  {
    firstName: 'joe',
    lastName: 'dirte',
    age: 45,
    visits: 20,
    status: 'Complicated',
    progress: 10,
  },
]

const defaultColumns: ColumnDef<Person>[] = [
  {
    header: ResultSetTableHeader,
    accessorKey: 'firstName',
    cell: ResultSetTableCell,
    footer: props => props.column.id,
  },

  {
    accessorKey: 'age',
    cell: ResultSetTableCell,
    header: ResultSetTableHeader,
    footer: props => props.column.id,
  },
  {
    accessorKey: 'visits',
    header: ResultSetTableHeader,
    cell: ResultSetTableCell,
    footer: props => props.column.id,
  },
  {
    accessorKey: 'status',
    header: ResultSetTableHeader,
    cell: ResultSetTableCell,
    footer: props => props.column.id,
  },
  {
    accessorKey: 'progress',
    header: ResultSetTableHeader,
    cell: ResultSetTableCell,
    footer: props => props.column.id,
  },
]

function ResultSetTable() {
  const [data, setData] = React.useState(() => [...defaultData])
  const [columns] = React.useState<typeof defaultColumns>(() => [
    ...defaultColumns,
  ])

  const [columnResizeMode, setColumnResizeMode] =
    React.useState<ColumnResizeMode>('onChange')

  const rerender = React.useReducer(() => ({}), {})[1]

  const table = useReactTable({
    data,
    columns,
    columnResizeMode,
    getCoreRowModel: getCoreRowModel(),
    debugTable: true,
    debugHeaders: true,
    debugColumns: true,
    meta: {
      currentCell: null
    }
  })

  return (
    <div className="p-2" style={{
      padding: 20
    }}>
      <div className="overflow-x-auto">
        <table
          {...{
            style: {
              width: table.getCenterTotalSize(),
              verticalAlign: 'middle',
              border: '1px solid lightgray',
              borderCollapse: 'collapse',
              borderSpacing: 0,
              tableLayout: 'fixed'
            },
          }}
        >
          <thead>
            {table.getHeaderGroups().map(headerGroup => (
              <tr key={headerGroup.id}>
                {headerGroup.headers.map(header => (
                  <th
                    {...{
                      key: header.id,
                      colSpan: header.colSpan,
                      style: {
                        width: header.getSize(),
                        border: '1px solid lightgray',
                      },
                    }}
                  >
                    {header.isPlaceholder
                      ? null
                      : flexRender(
                        header.column.columnDef.header,
                        header.getContext()
                      )}
                  </th>
                ))}
              </tr>
            ))}
          </thead>
          <tbody>
            {table.getRowModel().rows.map(row => (
              <tr key={row.id}>
                {row.getVisibleCells().map(cell => (
                  <td
                    {...{
                      key: cell.id,
                      style: {
                        width: cell.column.getSize(),
                        border: '1px solid lightgray',
                        padding: 0,
                        margin: 0
                      },
                    }}
                    onClick={(event) => {
                      
                    }}
                  >
                    {flexRender(cell.column.columnDef.cell, cell.getContext())}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default ResultSetTable