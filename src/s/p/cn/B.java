package s.p.cn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class B {
	
	
	public static void main(String[] args) {
    	
        try {
        	String path = System.getProperty("user.dir")+S.o;       	
            FileInputStream excelFile = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(excelFile);
            
            Sheet datatypeSheet = workbook.getSheetAt(1);
            Iterator<Row> iterator = datatypeSheet.iterator();

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    //getCellTypeEnum shown as deprecated for version 3.15
                    //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + " | "+currentCell.getColumnIndex() + " # ");
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                    	BigInteger big = BigInteger.valueOf((long) currentCell.getNumericCellValue());
                        System.out.print(currentCell.getNumericCellValue() + " | "+currentCell.getColumnIndex() + " # ");
                    }

                }
                System.out.println();

            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private void setData(){
    	String p = System.getProperty("user.dir")+S.o;       	
        FileInputStream i;
		try {
			i = new FileInputStream(new File(p));
			Workbook workbook = new XSSFWorkbook(i);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
    }

}
