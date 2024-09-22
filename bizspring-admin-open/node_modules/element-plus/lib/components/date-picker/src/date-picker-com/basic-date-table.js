'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var vue = require('vue');
var dayjs = require('dayjs');
var lodashUnified = require('lodash-unified');
require('../../../../hooks/index.js');
require('../../../../utils/index.js');
var basicDateTable = require('../props/basic-date-table.js');
var utils = require('../utils.js');
var basicCellRender = require('./basic-cell-render.js');
var pluginVue_exportHelper = require('../../../../_virtual/plugin-vue_export-helper.js');
var index = require('../../../../hooks/use-namespace/index.js');
var index$1 = require('../../../../hooks/use-locale/index.js');
var arrays = require('../../../../utils/arrays.js');

function _interopDefaultLegacy (e) { return e && typeof e === 'object' && 'default' in e ? e : { 'default': e }; }

var dayjs__default = /*#__PURE__*/_interopDefaultLegacy(dayjs);

const _hoisted_1 = ["aria-label", "onMousedown"];
const _hoisted_2 = {
  key: 0,
  scope: "col"
};
const _hoisted_3 = ["aria-label"];
const _hoisted_4 = ["aria-current", "aria-selected", "tabindex"];
const _sfc_main = /* @__PURE__ */ vue.defineComponent({
  __name: "basic-date-table",
  props: basicDateTable.basicDateTableProps,
  emits: ["changerange", "pick", "select"],
  setup(__props, { expose, emit }) {
    const props = __props;
    const ns = index.useNamespace("date-table");
    const { t, lang } = index$1.useLocale();
    const tbodyRef = vue.ref();
    const currentCellRef = vue.ref();
    const lastRow = vue.ref();
    const lastColumn = vue.ref();
    const tableRows = vue.ref([[], [], [], [], [], []]);
    let focusWithClick = false;
    const firstDayOfWeek = props.date.$locale().weekStart || 7;
    const WEEKS_CONSTANT = props.date.locale("en").localeData().weekdaysShort().map((_) => _.toLowerCase());
    const offsetDay = vue.computed(() => {
      return firstDayOfWeek > 3 ? 7 - firstDayOfWeek : -firstDayOfWeek;
    });
    const startDate = vue.computed(() => {
      const startDayOfMonth = props.date.startOf("month");
      return startDayOfMonth.subtract(startDayOfMonth.day() || 7, "day");
    });
    const WEEKS = vue.computed(() => {
      return WEEKS_CONSTANT.concat(WEEKS_CONSTANT).slice(firstDayOfWeek, firstDayOfWeek + 7);
    });
    const hasCurrent = vue.computed(() => {
      return lodashUnified.flatten(rows.value).some((row) => {
        return row.isCurrent;
      });
    });
    const days = vue.computed(() => {
      const startOfMonth = props.date.startOf("month");
      const startOfMonthDay = startOfMonth.day() || 7;
      const dateCountOfMonth = startOfMonth.daysInMonth();
      const dateCountOfLastMonth = startOfMonth.subtract(1, "month").daysInMonth();
      return {
        startOfMonthDay,
        dateCountOfMonth,
        dateCountOfLastMonth
      };
    });
    const selectedDate = vue.computed(() => {
      return props.selectionMode === "dates" ? arrays.castArray(props.parsedValue) : [];
    });
    const setDateText = (cell, {
      count,
      rowIndex,
      columnIndex
    }) => {
      const { startOfMonthDay, dateCountOfMonth, dateCountOfLastMonth } = vue.unref(days);
      const offset = vue.unref(offsetDay);
      if (rowIndex >= 0 && rowIndex <= 1) {
        const numberOfDaysFromPreviousMonth = startOfMonthDay + offset < 0 ? 7 + startOfMonthDay + offset : startOfMonthDay + offset;
        if (columnIndex + rowIndex * 7 >= numberOfDaysFromPreviousMonth) {
          cell.text = count;
          return true;
        } else {
          cell.text = dateCountOfLastMonth - (numberOfDaysFromPreviousMonth - columnIndex % 7) + 1 + rowIndex * 7;
          cell.type = "prev-month";
        }
      } else {
        if (count <= dateCountOfMonth) {
          cell.text = count;
        } else {
          cell.text = count - dateCountOfMonth;
          cell.type = "next-month";
        }
        return true;
      }
      return false;
    };
    const setCellMetadata = (cell, {
      columnIndex,
      rowIndex
    }, count) => {
      const { disabledDate, cellClassName } = props;
      const _selectedDate = vue.unref(selectedDate);
      const shouldIncrement = setDateText(cell, { count, rowIndex, columnIndex });
      const cellDate = cell.dayjs.toDate();
      cell.selected = _selectedDate.find((d) => d.valueOf() === cell.dayjs.valueOf());
      cell.isSelected = !!cell.selected;
      cell.isCurrent = isCurrent(cell);
      cell.disabled = disabledDate == null ? void 0 : disabledDate(cellDate);
      cell.customClass = cellClassName == null ? void 0 : cellClassName(cellDate);
      return shouldIncrement;
    };
    const setRowMetadata = (row) => {
      if (props.selectionMode === "week") {
        const [start, end] = props.showWeekNumber ? [1, 7] : [0, 6];
        const isActive = isWeekActive(row[start + 1]);
        row[start].inRange = isActive;
        row[start].start = isActive;
        row[end].inRange = isActive;
        row[end].end = isActive;
      }
    };
    const rows = vue.computed(() => {
      const { minDate, maxDate, rangeState, showWeekNumber } = props;
      const offset = offsetDay.value;
      const rows_ = tableRows.value;
      const dateUnit = "day";
      let count = 1;
      if (showWeekNumber) {
        for (let rowIndex = 0; rowIndex < 6; rowIndex++) {
          if (!rows_[rowIndex][0]) {
            rows_[rowIndex][0] = {
              type: "week",
              text: startDate.value.add(rowIndex * 7 + 1, dateUnit).week()
            };
          }
        }
      }
      utils.buildPickerTable({ row: 6, column: 7 }, rows_, {
        startDate: minDate,
        columnIndexOffset: showWeekNumber ? 1 : 0,
        nextEndDate: rangeState.endDate || maxDate || rangeState.selecting && minDate || null,
        now: dayjs__default["default"]().locale(vue.unref(lang)).startOf(dateUnit),
        unit: dateUnit,
        relativeDateGetter: (idx) => startDate.value.add(idx - offset, dateUnit),
        setCellMetadata: (...args) => {
          if (setCellMetadata(...args, count)) {
            count += 1;
          }
        },
        setRowMetadata
      });
      return rows_;
    });
    vue.watch(() => props.date, async () => {
      var _a, _b;
      if ((_a = tbodyRef.value) == null ? void 0 : _a.contains(document.activeElement)) {
        await vue.nextTick();
        (_b = currentCellRef.value) == null ? void 0 : _b.focus();
      }
    });
    const focus = async () => {
      var _a;
      (_a = currentCellRef.value) == null ? void 0 : _a.focus();
    };
    const isNormalDay = (type = "") => {
      return ["normal", "today"].includes(type);
    };
    const isCurrent = (cell) => {
      return props.selectionMode === "date" && isNormalDay(cell.type) && cellMatchesDate(cell, props.parsedValue);
    };
    const cellMatchesDate = (cell, date) => {
      if (!date)
        return false;
      return dayjs__default["default"](date).locale(lang.value).isSame(props.date.date(Number(cell.text)), "day");
    };
    const getCellClasses = (cell) => {
      const classes = [];
      if (isNormalDay(cell.type) && !cell.disabled) {
        classes.push("available");
        if (cell.type === "today") {
          classes.push("today");
        }
      } else {
        classes.push(cell.type);
      }
      if (isCurrent(cell)) {
        classes.push("current");
      }
      if (cell.inRange && (isNormalDay(cell.type) || props.selectionMode === "week")) {
        classes.push("in-range");
        if (cell.start) {
          classes.push("start-date");
        }
        if (cell.end) {
          classes.push("end-date");
        }
      }
      if (cell.disabled) {
        classes.push("disabled");
      }
      if (cell.selected) {
        classes.push("selected");
      }
      if (cell.customClass) {
        classes.push(cell.customClass);
      }
      return classes.join(" ");
    };
    const getDateOfCell = (row, column) => {
      const offsetFromStart = row * 7 + (column - (props.showWeekNumber ? 1 : 0)) - offsetDay.value;
      return startDate.value.add(offsetFromStart, "day");
    };
    const handleMouseMove = (event) => {
      var _a;
      if (!props.rangeState.selecting)
        return;
      let target = event.target;
      if (target.tagName === "SPAN") {
        target = (_a = target.parentNode) == null ? void 0 : _a.parentNode;
      }
      if (target.tagName === "DIV") {
        target = target.parentNode;
      }
      if (target.tagName !== "TD")
        return;
      const row = target.parentNode.rowIndex - 1;
      const column = target.cellIndex;
      if (rows.value[row][column].disabled)
        return;
      if (row !== lastRow.value || column !== lastColumn.value) {
        lastRow.value = row;
        lastColumn.value = column;
        emit("changerange", {
          selecting: true,
          endDate: getDateOfCell(row, column)
        });
      }
    };
    const isSelectedCell = (cell) => {
      return !hasCurrent.value && (cell == null ? void 0 : cell.text) === 1 && cell.type === "normal" || cell.isCurrent;
    };
    const handleFocus = (event) => {
      if (focusWithClick || hasCurrent.value || props.selectionMode !== "date")
        return;
      handlePickDate(event, true);
    };
    const handleMouseDown = (event) => {
      const target = event.target.closest("td");
      if (!target)
        return;
      focusWithClick = true;
    };
    const handleMouseUp = (event) => {
      const target = event.target.closest("td");
      if (!target)
        return;
      focusWithClick = false;
    };
    const handlePickDate = (event, isKeyboardMovement = false) => {
      const target = event.target.closest("td");
      if (!target)
        return;
      const row = target.parentNode.rowIndex - 1;
      const column = target.cellIndex;
      const cell = rows.value[row][column];
      if (cell.disabled || cell.type === "week")
        return;
      const newDate = getDateOfCell(row, column);
      if (props.selectionMode === "range") {
        if (!props.rangeState.selecting || !props.minDate) {
          emit("pick", { minDate: newDate, maxDate: null });
          emit("select", true);
        } else {
          if (newDate >= props.minDate) {
            emit("pick", { minDate: props.minDate, maxDate: newDate });
          } else {
            emit("pick", { minDate: newDate, maxDate: props.minDate });
          }
          emit("select", false);
        }
      } else if (props.selectionMode === "date") {
        emit("pick", newDate, isKeyboardMovement);
      } else if (props.selectionMode === "week") {
        const weekNumber = newDate.week();
        const value = `${newDate.year()}w${weekNumber}`;
        emit("pick", {
          year: newDate.year(),
          week: weekNumber,
          value,
          date: newDate.startOf("week")
        });
      } else if (props.selectionMode === "dates") {
        const newValue = cell.selected ? arrays.castArray(props.parsedValue).filter((d) => (d == null ? void 0 : d.valueOf()) !== newDate.valueOf()) : arrays.castArray(props.parsedValue).concat([newDate]);
        emit("pick", newValue);
      }
    };
    const isWeekActive = (cell) => {
      if (props.selectionMode !== "week")
        return false;
      let newDate = props.date.startOf("day");
      if (cell.type === "prev-month") {
        newDate = newDate.subtract(1, "month");
      }
      if (cell.type === "next-month") {
        newDate = newDate.add(1, "month");
      }
      newDate = newDate.date(Number.parseInt(cell.text, 10));
      if (props.parsedValue && !Array.isArray(props.parsedValue)) {
        const dayOffset = (props.parsedValue.day() - firstDayOfWeek + 7) % 7 - 1;
        const weekDate = props.parsedValue.subtract(dayOffset, "day");
        return weekDate.isSame(newDate, "day");
      }
      return false;
    };
    expose({
      focus
    });
    return (_ctx, _cache) => {
      return vue.openBlock(), vue.createElementBlock("table", {
        role: "grid",
        "aria-label": vue.unref(t)("el.datepicker.dateTablePrompt"),
        cellspacing: "0",
        cellpadding: "0",
        class: vue.normalizeClass([vue.unref(ns).b(), { "is-week-mode": _ctx.selectionMode === "week" }]),
        onClick: handlePickDate,
        onMousemove: handleMouseMove,
        onMousedown: vue.withModifiers(handleMouseDown, ["prevent"]),
        onMouseup: handleMouseUp
      }, [
        vue.createElementVNode("tbody", {
          ref_key: "tbodyRef",
          ref: tbodyRef
        }, [
          vue.createElementVNode("tr", null, [
            _ctx.showWeekNumber ? (vue.openBlock(), vue.createElementBlock("th", _hoisted_2, vue.toDisplayString(vue.unref(t)("el.datepicker.week")), 1)) : vue.createCommentVNode("v-if", true),
            (vue.openBlock(true), vue.createElementBlock(vue.Fragment, null, vue.renderList(vue.unref(WEEKS), (week, key) => {
              return vue.openBlock(), vue.createElementBlock("th", {
                key,
                scope: "col",
                "aria-label": vue.unref(t)("el.datepicker.weeksFull." + week)
              }, vue.toDisplayString(vue.unref(t)("el.datepicker.weeks." + week)), 9, _hoisted_3);
            }), 128))
          ]),
          (vue.openBlock(true), vue.createElementBlock(vue.Fragment, null, vue.renderList(vue.unref(rows), (row, rowKey) => {
            return vue.openBlock(), vue.createElementBlock("tr", {
              key: rowKey,
              class: vue.normalizeClass([vue.unref(ns).e("row"), { current: isWeekActive(row[1]) }])
            }, [
              (vue.openBlock(true), vue.createElementBlock(vue.Fragment, null, vue.renderList(row, (cell, columnKey) => {
                return vue.openBlock(), vue.createElementBlock("td", {
                  key: `${rowKey}.${columnKey}`,
                  ref_for: true,
                  ref: (el) => isSelectedCell(cell) && (currentCellRef.value = el),
                  class: vue.normalizeClass(getCellClasses(cell)),
                  "aria-current": cell.isCurrent ? "date" : void 0,
                  "aria-selected": cell.isCurrent,
                  tabindex: isSelectedCell(cell) ? 0 : -1,
                  onFocus: handleFocus
                }, [
                  vue.createVNode(vue.unref(basicCellRender["default"]), { cell }, null, 8, ["cell"])
                ], 42, _hoisted_4);
              }), 128))
            ], 2);
          }), 128))
        ], 512)
      ], 42, _hoisted_1);
    };
  }
});
var DateTable = /* @__PURE__ */ pluginVue_exportHelper["default"](_sfc_main, [["__file", "/home/runner/work/element-plus/element-plus/packages/components/date-picker/src/date-picker-com/basic-date-table.vue"]]);

exports["default"] = DateTable;
//# sourceMappingURL=basic-date-table.js.map
