var jrSortTable = Object.create(null);

(function (doc) {
    'use strict';

    var _arrows = Object.create(null, {
        up: {value: '&nbsp;&#x25B4;'},
        down: {value: '&nbsp;&#x25BE;'}
    });

    var _getElementText = function (el) {
        return (el.textContent).replace(/^\s+|\s+$/g, '');
    };

    jrSortTable.tableProp = [];

    jrSortTable.sort = function (tblNumber, thElem) {
        var column = thElem.cellIndex;
        var prop = jrSortTable.tableProp[tblNumber], tbody = prop.tbody;
        var th = prop.headerCells[column];
        var sortfn = th.sortfn, sortdir = th.sortdir, sortedTBody = th.sortedTBody;
        var i, len, row, tbr;
        var fragment = doc.createDocumentFragment();

        if (th.isSorted) { // This column is already sorted
            sortdir = (sortdir === 'up') ? 'down' : 'up';
            if (sortdir === 'up') {
                for (i = 0, len = sortedTBody.length; i < len; i++) {
                    row = sortedTBody[i][1];
                    fragment.appendChild(row);
                }
            } else {
                // Reverse tbody. I didn't use sortedTBody.reverse() because it would require one more step
                i = sortedTBody.length;
                while (i) {
                    row = sortedTBody[--i][1];
                    fragment.appendChild(row);
                }
            }
        } else {
            sortedTBody = [];
            for (i = 0, tbr = tbody.rows, len = tbr.length; i < len; i++) {
                row = tbr[i];
                sortedTBody.push([_getElementText(row.cells[column]), row]);
            }
            sortedTBody.sort(sortfn);
            jrSortTable.tableProp[tblNumber].headerCells[column].isSorted = true;
            for (i = 0; i < len; i++) {
                row = sortedTBody[i][1];
                fragment.appendChild(row);
            }
        }
        tbody.appendChild(fragment);

        var oldSpanElem = doc.getElementById(prop.spanArrowId);
        if (oldSpanElem) {
            var paren = oldSpanElem.parentNode; // thElem node that contains span node.
            paren.removeChild(oldSpanElem);
        }
        var newSpanElem = doc.createElement('span');
        newSpanElem.id = prop.spanArrowId;
        newSpanElem.innerHTML = _arrows[sortdir];
        thElem.appendChild(newSpanElem);

        jrSortTable.tableProp[tblNumber].headerCells[column].sortedTBody = sortedTBody;
        jrSortTable.tableProp[tblNumber].headerCells[column].sortdir = sortdir;
    };

    jrSortTable.sortMethods = (function (obj) {
        obj.alphaNumeric = function (a, b) {
            var aa = a[0], bb = b[0], tmpa, tmpb;
            if (aa.search(/^0+.*$/) === 0) {
                tmpa = aa;
            } else {
                tmpa = (aa.length) ? (isNaN(tmpa = parseFloat(aa)) ? aa : tmpa) : 0;
            }
            if (bb.search(/^0+.*$/) === 0) {
                tmpb = bb;
            } else {
                tmpb = (bb.length) ? (isNaN(tmpb = parseFloat(bb)) ? bb : tmpb) : 0;
            }
            if (typeof tmpa === 'string' && typeof tmpb === 'string') {
                return tmpa.localeCompare(tmpb); // Take accented and case-sensitive chars into account
            }
            if (typeof tmpa === 'number' && typeof tmpb === 'number') {
                return tmpa - tmpb;
            }
            // In case of different types, number < object < string
            return (typeof tmpa < typeof tmpb ? -1 : 1);
        };

        obj.sortDate = function (a, b) { // dd/mm/yyyy
            var aa = a[0], bb = b[0];
            var date1 = aa.substr(6, 4) + aa.substr(3, 2) + aa.substr(0, 2); // turns dd-mm-yyyy into yyyymmdd
            var date2 = bb.substr(6, 4) + bb.substr(3, 2) + bb.substr(0, 2);
            if (date1 === date2) return 0;
            if (date1 < date2) return -1;
            return 1;
        };

        obj.sortDate_US = function (a, b) { // mm/dd/yyyy
            var aa = a[0], bb = b[0];
            var date1 = aa.substr(6, 4) + aa.substr(0, 2) + aa.substr(3, 2); // turns mm-dd-yyyy into yyyymmdd
            var date2 = bb.substr(6, 4) + bb.substr(0, 2) + bb.substr(3, 2);
            if (date1 === date2) return 0;
            if (date1 < date2) return -1;
            return 1;
        };

        obj.sortNumberJS = function (a, b) {
            var re = /[^\d.-]+/g; // remove the thousands separator, currency and % symbols
            var aa = a[0].replace(re, '');
            var bb = b[0].replace(re, '');
            if (isNaN(aa)) aa = 0;
            if (isNaN(bb)) bb = 0;
            return aa - bb;
        };

        obj.sortNumber_nonJS = function (a, b) {
            var re = /[^\d,-]+/g;
            var aa = a[0].replace(re, '');
            var bb = b[0].replace(re, '');
            aa = aa.replace(/,/, '.');
            bb = bb.replace(/,/, '.');
            if (isNaN(aa)) aa = 0;
            if (isNaN(bb)) bb = 0;
            return aa - bb;
        };
        return obj;
    }(Object.create(null)));

    jrSortTable.setup = function () {
        var tables = doc.querySelectorAll('.sortable');
        [].forEach.call(tables, function (tbl, idx) {
            prepareTables(tbl, idx);
        });
        tables = null;

        function prepareTables(tableElem, tblNumber) {
            var addOnClickEvt = function () {
                jrSortTable.sort(tblNumber, this);
            };
            var addEvent = function (row) {
                var cells = row.cells, len = cells.length;
                while (len--) {
                    cells[len].onclick = addOnClickEvt;
                }
            };
            var sortMethods = jrSortTable.sortMethods;

            function guessDataType(txtCell) {
                var sortfn, testDate;
                if (txtCell.length > 0) {
                    if (txtCell.match(/^-?\D*?[\d,.]+[\s%]*?$/)) {
                        return sortMethods.sortNumberJS;
                    }
                    testDate = (txtCell.match(/^(\d\d?)[/.-](\d\d?)[/.-]((\d\d)?\d\d)$/));
                    if (testDate) {
                        if (parseInt(testDate[1], 10) > 12) {
                            sortfn = sortMethods.sortDate;
                        } else if (parseInt(testDate[2], 10) > 12) {
                            sortfn = sortMethods.sortDate_US;
                        } else {
                            sortfn = sortMethods.sortDate;
                        }
                    }
                }
                return sortfn || sortMethods.alphaNumeric;
            }

            var arr = Object.keys(sortMethods);
            var rxFn = new RegExp(arr.join('|'));

            function getSortFn(className) {
                var found = rxFn.exec(className);
                return (found) ? sortMethods[found[0]] : '';
            }

            if (tableElem.getElementsByTagName('thead').length === 0) {
                var the = doc.createElement('thead');
                the.appendChild(tableElem.rows[0]);
                tableElem.insertBefore(the, tableElem.firstChild);
            }
            if (tableElem.tHead === null) {
                tableElem.tHead = tableElem.getElementsByTagName('thead')[0];
            }
            var thead = tableElem.tHead;
            var tbody = tableElem.tBodies[0];

            jrSortTable.tableProp[tblNumber] = {
                headerCells: [], // TH elements
                spanArrowId: "jrSortSpan" + tblNumber,
                tbody: tbody
            };

            var arrTh = thead.rows[0].cells, fn;
            for (var i = 0, len = arrTh.length; i < len; i++) {
                arrTh[i].sortdir = 'up';
                arrTh[i].isSorted = false;
                fn = getSortFn(arrTh[i].className);
                arrTh[i].sortfn = fn || guessDataType(
                    _getElementText(tbody.rows[0].cells[i])
                );
            }
            jrSortTable.tableProp[tblNumber].headerCells = arrTh;

            addEvent(thead.rows[0]);
            if (tableElem.tFoot) {
                addEvent(tableElem.tFoot.rows[0]);
            }
        }
    };
}(document));
window.addEventListener('load', jrSortTable.setup, false);