package com.centit.support.report;


import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by codefan on 17-9-20.
 */
@SuppressWarnings("unused")
public class ExcelImportUtil {
    private ExcelImportUtil() {
        throw new IllegalAccessError("Utility class");
    }

    protected static final Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);

    /**
     *
     * @param excelFile excel 文件流
     * @param sheetName 读取的页面名称
     * @param columnList 读取的列
     * @param rowList 读取的行
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, String sheetName,
                                                   int[] columnList, int[] rowList)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);

        if(sheet == null)
            return null;

        //int minRow = sheet.getFirstRowNum();
        //int maxRow = sheet.getLastRowNum();
        List<String[]> datas = new ArrayList<>(rowList.length+1);
        for(int row : rowList) {
            String[] rowObj = new String[columnList.length];
            HSSFRow excelRow = sheet.getRow(row);
            if(excelRow==null){
                datas.add(null);
            }else {
                int i = 0;
                //excelRow.getFirstCellNum()
                for (int col : columnList) {
                    HSSFCell cell = excelRow.getCell(col);
                    rowObj[i++] = cell == null ? null : cell.getStringCellValue();
                }
                datas.add(rowObj);
            }
        }

        return datas;
    }

    /**
     * 所有的行列都是 0 Base的
     * @param excelFile excel 文件流
     * @param sheetName 读取的页面名称
     * @param beginCol 起始列  包含 beginCol
     * @param endCol 终止列 不包含 endCol
     * @param beginRow 起始行 包含 beginRow
     * @param endRow 起始行 不包含 endRow
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, String sheetName,
                                                   int beginCol, int endCol, int beginRow, int endRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);

        if(sheet == null)
            return null;

        List<String[]> datas = new ArrayList<>(endRow-beginRow+1);
        for(int row =beginRow; row<endRow; row ++ ) {

            HSSFRow excelRow = sheet.getRow(row);
            if(excelRow==null)
                continue;
            int i=0;
            String[] rowObj = new String[endCol-beginCol+1];
            //excelRow.getFirstCellNum()
            for(int col =beginCol; col < endCol; col++ ){
                HSSFCell cell = excelRow.getCell(col);
                rowObj[i++] = cell == null ? null : cell.getStringCellValue();
            }
            datas.add(rowObj);
        }

        return datas;
    }

    /**
     * 所有的行列都是 0 Base的
     * @param excelFile excel 文件流
     * @param sheetName 读取的页面名称
     * @param beginCol 起始列  包含 beginCol
     * @param beginRow 起始行 包含 beginRow
     * @return 返回二维数组
     */
    public static List<String[]> loadDataFromExcel(InputStream excelFile, String sheetName,
                                                   int beginCol,  int beginRow)
            throws IOException {

        HSSFWorkbook wb = new HSSFWorkbook(excelFile);
        HSSFSheet sheet = (StringUtils.isBlank(sheetName))?
                wb.getSheetAt(0) : wb.getSheet(sheetName);

        if(sheet == null)
            return null;
        int maxRow = sheet.getLastRowNum();
        List<String[]> datas = new ArrayList<>(maxRow-beginRow+1);
        for(int row =beginRow; row<maxRow; row ++ ) {

            HSSFRow excelRow = sheet.getRow(row);
            if(excelRow==null)
                continue;

            int endCol = excelRow.getLastCellNum();
            String[] rowObj = new String[endCol-beginCol+1];
            int i=0;
            //excelRow.getFirstCellNum()
            for(int col = beginCol; col < endCol; col++ ){
                HSSFCell cell = excelRow.getCell(col);
                rowObj[i++] = cell == null ? null : cell.getStringCellValue();
            }
            datas.add(rowObj);
        }

        return datas;
    }
}
