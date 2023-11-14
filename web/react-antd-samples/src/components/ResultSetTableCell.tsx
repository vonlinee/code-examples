import { Cell, Column, Header, Row, Table } from "@tanstack/react-table"
import React, { useEffect, useState, useRef } from "react"

type ResultSetTableCellProps<T> = {
  table: Table<T>
  row: Row<T>
  column: Column<T>
  cell: Cell<T, any>
  getValue: () => any
  renderValue: () => any
  
}

export default function ResultSetTableCell<T>(props: ResultSetTableCellProps<T>) {

  const { column, cell, table } = props

  const t = table as any
  console.log(t.meta);

  const initialValue = cell.getValue();
  // 单元格的值
  const [value, setValue] = useState(initialValue);

  const inputRef = useRef<HTMLInputElement>(null)
  const containerRef = useRef<HTMLDivElement>(null)
  const [editing, setEditing] = useState(false)
  const [selected, setSelected] = useState(false)
  const toggleEdit = function () {
    if (editing) {
      setEditing(false);
      setSelected(false)
    } else {
      setEditing(true);
    }
  };

  useEffect(() => {
    if (editing) {
      inputRef.current?.focus()
    }
  }, [editing])

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  useEffect(() => {
    if (containerRef.current && selected) {
      containerRef.current.style.backgroundColor = 'green'
    }
  }, [selected])

  function handleContainerClicked() {
    if (containerRef.current) {
      setContainerBorderStyle('dashed red')
    }
  }

  const [containerBorderStyle, setContainerBorderStyle] = useState('')

  return <><div ref={containerRef} 
  onClick={handleContainerClicked} 
  style={{
    border: containerBorderStyle
  }}
  onDoubleClick={() => setEditing(true)}>{editing ?
    <div style={{
      display: 'flex'
    }}
      onBlur={() => toggleEdit()}
    >
      <input ref={inputRef} value={value}
        style={{
          flex: 1 // 撑满剩余区域
        }}
        onChange={e => setValue(e.target.value)}
      />
      <button>A</button>
    </div>
    :
    <span>{value}</span>}</div></>
}
