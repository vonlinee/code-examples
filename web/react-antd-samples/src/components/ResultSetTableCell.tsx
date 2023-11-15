import { Cell, Column, Header, Row, Table } from "@tanstack/react-table";
import React, {
  useEffect,
  useState,
  useRef,
  MouseEventHandler,
  SyntheticEvent,
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
  // 单元格的值
  const [value, setValue] = useState(initialValue);

  const inputRef = useRef<HTMLInputElement>(null);
  const containerRef = useRef<HTMLDivElement>(null);
  const [editing, setEditing] = useState(false);
  const [selected, setSelected] = useState(false);
  const toggleEdit = function () {
    if (editing) {
      setEditing(false);
      setSelected(false);
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

  function handleUndoEdit(e : any) {
    e.stopPropagation();
  }

  function onMenuClicked() {
    console.log("clicked menu");
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
            onBlur={() => toggleEdit()}
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
              onChange={(e) => setValue(e.target.value)}
            />
            <button onClick={handleUndoEdit}>
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
            <button>
              <Menu width={16} height={16} onClick={onMenuClicked} />
            </button>
          </div>
        )}
      </div>
    </>
  );
}
