import { Cell, Column, Row, Table } from "@tanstack/react-table";
import React, { useEffect, useState, useRef } from "react";

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

/**
 * 单元格
 * @param props
 * @returns
 */
export default function ResultSetTableCell<T>(
  props: ResultSetTableCellProps<T>
) {
  const { column, cell, table } = props;
  // 初始值
  const initialValue = cell.getValue();
  // 旧值
  const [oldValue] = useState(initialValue);
  // 当前值
  const [value, setValue] = useState(initialValue);
  // 菜单图标是否可见
  const [menuVisiable, setMenuVisiable] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);
  const containerRef = useRef<HTMLDivElement>(null);
  // 是否处于编辑状态
  const [editing, setEditing] = useState(false);
  // 是否选中此单元格
  const [selected, setSelected] = useState(false);

  function highlightCellIfValueChanged() {
    if (containerRef.current) {
      // 不能使用Object.is()判断
      if (Object.is(oldValue, value)) {
        containerRef.current.style.backgroundColor = "white";
      } else {
        if (oldValue == value) {
          containerRef.current.style.backgroundColor = "white";
        } else {
          containerRef.current.style.backgroundColor = "#e5d2aa";
        }
      }
    }
  }

  /**
   * 切换编辑状态
   */
  const toggleEdit = function () {
    if (editing) {
      setEditing(false);
      setSelected(false);
      if (menuVisiable) {
        setMenuVisiable(false);
      }
      highlightCellIfValueChanged();
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

  function handleUndoEdit(
    event:
      | React.MouseEvent<HTMLButtonElement, MouseEvent>
      | React.MouseEvent<SVGSVGElement, MouseEvent>
  ) {
    setValue(oldValue);
  }

  function onMenuClicked(
    event: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) {
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
              // console.log("获取焦点");
            }}
            onClick={(event) => {
              // console.log("edit div clicked");
            }}
          >
            <input
              ref={inputRef}
              value={value}
              style={{
                flex: 1, // 撑满剩余区域
                width: "100%",
                height: "100%",
                border: "transparent",
                outline: "none",
              }}
              onBlur={() => toggleEdit()}
              onChange={(e) => setValue(e.target.value)}
            />
            <button
              style={{
                height: "100%",
                backgroundColor: "white",
                cursor: "pointer",
                border: "none",
                borderLeft: "1px solid lightgray",
              }}
              onDoubleClick={handleUndoEdit}
              onMouseDown={(event) => {
                handleUndoEdit(event);
              }}
            >
              <Undo
                width={16}
                height={16}
                onClick={handleUndoEdit}
                onDoubleClick={handleUndoEdit}
              />
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
            onMouseLeave={(event) => {
              setMenuVisiable(false);
            }}
            onClick={(event) => {
              // event.currentTarget.style.border = "1px solid blue";
            }}
            onBlur={(event) => {
              console.log("cell div blur");
              // event.currentTarget.style.border = "none";
            }}
          >
            <div
              style={{
                flex: "1 1 auto",
                overflow: "hidden",
                textOverflow: "ellipsis",
                whiteSpace: "nowrap",
                minWidth: 50,
                width: 0,
                marginLeft: 5,
              }}
              onClick={(event) => {}}
            >
              {value}
            </div>
            {menuVisiable ? (
              <button
                style={{
                  height: "100%",
                  backgroundColor: "white",
                  cursor: "pointer",
                  border: "none",
                }}
                onClick={onMenuClicked}
                onDoubleClick={onMenuClicked}
                onFocus={(event) => {
                  // 阻止单元格获取焦点
                  event.stopPropagation();
                }}
              >
                <Menu width={16} height={16} />
              </button>
            ) : null}
          </div>
        )}
      </div>
    </>
  );
}
