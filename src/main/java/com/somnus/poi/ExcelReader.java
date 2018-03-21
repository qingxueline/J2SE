package com.somnus.poi;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/** 
 * @Description: 读取Excel 兼容2003/2007/2010
 * @author Somnus
 * @date 2015年11月6日 上午10:05:52 
 * @version 1.0 
 */
public class ExcelReader {
    /**
     * 读取“.xls”格式使用  import org.apache.poi.hssf.usermodel.*;包的内容，例如：HSSFWorkbook
     * 读取“.xlsx”格式使用 import org.apache.poi.xssf.usermodel.*; 包的内容，例如：XSSFWorkbook
     * 读取两种格式使用    import org.apache.poi.ss.usermodel.*    包的内容，例如：Workbook
     * @param filePath
     * @return
     */
    public static List<String[]> readExcel(String filePath){
        String fullPath = ExcelReader.class.getClassLoader().getResource(filePath).getPath();
        Workbook wb = null;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(fullPath));
            wb = WorkbookFactory.create(in); 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        List<String[]> list = new ArrayList<String[]>();
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++){
            Sheet st = wb.getSheetAt(sheetIndex);
            // 第一行为标题，不取
            for (int rowIndex = 0; rowIndex <= st.getPhysicalNumberOfRows(); rowIndex++){
                Row row = st.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                String[] cells = cellArray(row);
                list.add(cells);
            }
        }
        return list;
    }
    
    private static String[] cellArray(Row row) {
        String[] cellArray = new String[row.getPhysicalNumberOfCells()];
        for (int index = 0; index < row.getPhysicalNumberOfCells(); index++) {
            String value = "";
            Cell cell = row.getCell(index);
            if (cell != null) {
                switch (cell.getCellType()) {
                    case HSSFCell.CELL_TYPE_STRING :
                        value = cell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC :
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {
                            Date date = cell.getDateCellValue();
                            if (date != null) {
                                value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                            } else {
                                value = "";
                            }
                        } else {
                            value = new DecimalFormat("0.00").
                                    format(cell.getNumericCellValue());
                        }
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA :
                        try{
                            value = String.valueOf(cell.getNumericCellValue());
                        } catch(IllegalStateException e){
                            value = String.valueOf(cell.getRichStringCellValue());
                        }
                        break;
                    case HSSFCell.CELL_TYPE_BLANK :
                        break;
                    case HSSFCell.CELL_TYPE_ERROR :
                        value = "";
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN :
                        value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                        break;
                    default :
                        value = "";
                }
            } else {
                value = "";
            }
            cellArray[index] = value;
        }
        return cellArray;
    }
    
    public static void main(String[] args) {
        List<String[]> list = readExcel("excel/80034.xls");
        for(String[] arr:list){
            System.out.println(Arrays.toString(arr));
        }
    }
}
