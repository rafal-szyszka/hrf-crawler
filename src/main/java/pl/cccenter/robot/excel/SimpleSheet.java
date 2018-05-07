package pl.cccenter.robot.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Iterator;

/**
 * Created by Squier on 2016-10-12.
 */
public class SimpleSheet {

    private XSSFSheet hardSheet;
    private Iterator<Row> rows;
    private Iterator<Cell> cells;

    public SimpleSheet(XSSFSheet hardSheet) {
        this.hardSheet = hardSheet;
    }

    private void initRowIterator() {
        rows = hardSheet.iterator();
    }

    public int getCellIntegerValue(String cell) {
        int row, column;
        initRowIterator();

        row = getRowFromLetters(cell);
        column = getColumnIndexFromLetters(cell);

        int value = 0;

        Row destRow = null;
        for (int i = 0; i < row; i++) {
            destRow = rows.next();
        }

        Cell destCell = null;
        if (destRow != null) {
            cells = destRow.cellIterator();
            for (int i = 0; i < column; i++) {
                destCell = cells.next();
            }
            value = destCell != null ? (int) destCell.getNumericCellValue() : null;
        }

        return value;
    }

    public String getCellStringValue(String cell) {
        int row, column;
        initRowIterator();

        row = getRowFromLetters(cell);
        column = getColumnIndexFromLetters(cell);

        String value = null;

        Row destRow = null;
        for (int i = 0; i < row; i++) {
            destRow = rows.next();
        }

        Cell destCell = null;
        if (destRow != null) {
            cells = destRow.cellIterator();
            for (int i = 0; i < column; i++) {
                destCell = cells.next();
            }
            value = destCell != null ? destCell.getStringCellValue() : null;
        }

        return value;
    }

    public int getColumnIndexFromLetters(String cell) {
        int result = 0;
        int weight = 0;

        cell = cell.toUpperCase();

        for (char ch : cell.toCharArray()){
            if( (int)ch >= 65) {
                result += Math.pow(26, weight) * ((int) ch - 64);
                weight++;
            }
        }

        return result;
    }

    public int getRowFromLetters(String cell) {
        int row = 0;
        int weight = 0;

        char[] cells = cell.toCharArray();
        weight = cells.length - 2;


        for(char ch : cells) {
            if( (int)ch <= 57) {
                row += Math.pow(10, weight) * ((int)ch - 48);
                weight--;
            }
        }

        return row;
    }
}
