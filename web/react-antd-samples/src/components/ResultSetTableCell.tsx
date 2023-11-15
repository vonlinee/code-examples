import { Cell, Column, Row, Table } from "@tanstack/react-table";
import React, {
  useEffect,
  useState,
  useRef,
} from "react";

import { ReactComponent as Undo } from "../icons/svg/undo.svg";
import { ReactComponent as Menu } from "../icons/svg/menu.svg";

type ResultSetTableCellProps<T> = {
  table: Table<T>;
  row: Row<T>;
  column: Column<T>;
  cell: Cell<T, any>;
  getValue: () => any;
  renderValue: () => any;
};

export default function ResultSetTableCell<T>(
  props: ResultSetTableCellProps<T>
) {
  const { column, cell, table } = props;

  const initialValue = cell.getValue();
  const [oldValue] = useState(initialValue);
  // 单元格的值
  const [value, setValue] = useState(initialValue);
  const [menuVisiable, setMenuVisiable] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);
  const containerRef = useRef<HTMLDivElement>(null);
  const [editing, setEditing] = useState(false);
  const [selected, setSelected] = useState(false);
  const toggleEdit = function () {
    if (editing) {
      setEditing(false);
      setSelected(false);
      if (menuVisiable) {
        setMenuVisiable(false)
      }
    } else {
      setEditing(true);
    }
  };

  useEffect(() => {
    if (editing) {
      inputRef.current?.focus();
    }
  }, [editing]);

  useEffect(() => {
    setValue(initialValue);
  }, [initialValue]);

  useEffect(() => {
    if (containerRef.current && selected) {
      containerRef.current.style.backgroundColor = "green";
    }
  }, [selected]);

  function handleContainerClicked() {
    if (containerRef.current) {
      // setContainerBorderStyle('dashed red')
    }
  }

  const [containerBorderStyle, setContainerBorderStyle] = useState("");

  function handleUndoEdit(event: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    event.stopPropagation();
    setValue(oldValue)
  }

  function onMenuClicked(event: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
    console.log("clicked menu");
    event.stopPropagation();
  }

  return (
    <>
      <div
        ref={containerRef}
        onClick={handleContainerClicked}
        style={{
          border: containerBorderStyle,
          height: "100%",
        }}
        onDoubleClick={() => setEditing(true)}
      >
        {editing ? (
          <div
            style={{
              height: "100%",
              display: "flex",
              alignItems: "center",
              overflow: "hidden",
            }}
            onFocus={() => {
              console.log("获取焦点");
            }}

          >
            <input
              ref={inputRef}
              value={value}
              style={{
                flex: 1, // 撑满剩余区域
                width: "100%",
                border: "transparent",
                outline: "none",
              }}
              onBlur={() => toggleEdit()}
              onChange={(e) => setValue(e.target.value)}
            />
            <button style={{
              height: '100%'
            }} onClick={handleUndoEdit} onDoubleClick={handleUndoEdit}>
              <Undo width={16} height={16} />
            </button>
          </div>
        ) : (
          <div
            style={{
              height: "100%",
              display: "flex",
              alignItems: "center",
              overflow: "hidden",
              textOverflow: "ellipsis",
            }}
            onMouseEnter={() => setMenuVisiable(true)}
            onMouseLeave={() => setMenuVisiable(false)}
          >
            <span
              style={{
                flex: "1 1 auto",
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
                minWidth: 50,
                width: 0,
              }}
            >
              {value}
            </span>
            {
              menuVisiable ? <button style={{
                height: '100%'
              }} onClick={onMenuClicked} onDoubleClick={onMenuClicked}>
                <Menu width={16} height={16} />
              </button> : null
            }
          </div>
        )}
      </div>
    </>
  );
}
