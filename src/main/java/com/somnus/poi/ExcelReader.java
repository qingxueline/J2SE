package com.somnus.poi;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFPictureData;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 读取Excel 兼容2003/2007/2010
 *
 * @author lyl
 * @version 1.0
 * @date 2020年3月27日12:06:08
 */
public class ExcelReader {
    /**
     * 读取“.xls”格式使用  import org.apache.poi.hssf.usermodel.*;包的内容，例如：HSSFWorkbook
     * 读取“.xlsx”格式使用 import org.apache.poi.xssf.usermodel.*; 包的内容，例如：XSSFWorkbook
     * 读取两种格式使用    import org.apache.poi.ss.usermodel.*    包的内容，例如：Workbook
     *
     * @param filePath 文件相对路径。
     * @return
     */
    public static List<String[]> readExcel(String filePath) {
//        String fullPath = Objects.requireNonNull(ExcelReader.class.getClassLoader().getResource(filePath)).getPath();
        Workbook wb;
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            wb = WorkbookFactory.create(in);
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        List<String[]> list = new ArrayList<String[]>();
        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
            Sheet st = wb.getSheetAt(sheetIndex);
            //todo 这是测试代码后面删除
            test(wb);

            // 第一行为标题，不取
            for (int rowIndex = 0; rowIndex <= st.getPhysicalNumberOfRows(); rowIndex++) {
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
                    case HSSFCell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
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
                    case HSSFCell.CELL_TYPE_FORMULA:
                        try {
                            value = String.valueOf(cell.getNumericCellValue());
                        } catch (IllegalStateException e) {
                            value = String.valueOf(cell.getRichStringCellValue());
                        }
                        break;
                    case HSSFCell.CELL_TYPE_BLANK:
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        value = "";
                        break;
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        value = (cell.getBooleanCellValue() ? "Y" : "N");
                        break;
                    default:
                        value = "";
                }
            } else {
                value = "";
            }
            cellArray[index] = value;
        }
        return cellArray;
    }

    /**
     * 这是一段测代码
     * @param wb
     */
    private static void test(Workbook wb){
        //读取图片,这段是测试代码
        List<XSSFPictureData> pictures = (List<XSSFPictureData>) wb.getAllPictures();
        for (int i = 0; i < pictures.size(); i++) {
            System.out.println("=======================================" + i);
            XSSFPictureData pictureData = pictures.get(i);
            byte[] picData = pictureData.getData();
            try {
                FileOutputStream fis =  new FileOutputStream(new File("E:/22222.txt"));
                IOUtils.write(picData, fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("image-size:" + picData.length);
        }
    }
}
