import { Column, Header, Table } from "@tanstack/react-table"


type ResultSetTableHeaderProps<T> = {
  table: Table<T>
  header: Header<T, any>
  column: Column<T>
}

export default function ResultSetTableHeader<T>(props: ResultSetTableHeaderProps<T>) {

  const { header } = props

  return <><span>{header.id}</span>
    <div
      {...{
        onMouseDown: header.getResizeHandler(),
        onTouchStart: header.getResizeHandler(),
        className: `resizer ${header.column.getIsResizing() ? 'isResizing' : ''
          }`,
      }}
    />
  </>
}