package valueset.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

/**
 * Read Excel File from specified directory
 * @author ZHOU WENTAO
 *
 */
@Service
public class ReadExcelFileService {

	private static final String JFCS_12_18_2017_FILE_NAME = "src/main/resources/testData/JFCS_Medical_Records_12.18.17.xlsx";
	private static final String PIR_12_18_2017_FILE_NAME = "src/main/resources/testData/PIR_Medical_Records_12.18.17.xlsx";

    /**
     * find the terms with specified sheet and column name
     * @param sheetColumanName
     */
    public List<String> getTermsWithSheetColumnName (String sheetName, String columnName, String type) {

    	List<String> terms = new ArrayList<String>();
        try {

        	//Hard coded the file directory
            FileInputStream excelFile = new FileInputStream(new File(JFCS_12_18_2017_FILE_NAME));
            Workbook workbook = new XSSFWorkbook(excelFile);
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
            	//Sheet datatypeSheet = workbook.getSheetAt(0);
            	System.out.println("SheetName: " + workbook.getSheetAt(i).getSheetName()) ;
            	if (type.equals("term_med")) {
            		if (workbook.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetName)) {
                		terms = getSpecificMedicationTerms(workbook.getSheetAt(i), columnName);
                		break;
                	}
            	}
            	if (type.equals("code")) {
            		if (workbook.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetName)) {
                		terms = getCodes(workbook.getSheetAt(i), columnName);
                		break;
                	}
            	}
            	if (type.equalsIgnoreCase("term_lab")) {
            		if (workbook.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetName)) {
                		terms = getLabs(workbook.getSheetAt(i), columnName);
                		break;
                	}
            	}
            	
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return terms;
    }
    
    /**
     * @param datatypeSheet
     * @param columnName
     * @return
     */
    public List<String> getSpecificMedicationTerms (Sheet datatypeSheet, String columnName) {

    	List<String> terms = new ArrayList<String>();
    	Map<String, Short> columnNameMap = getColumnName(datatypeSheet);
    	int targetColumnIndex = columnNameMap.get(columnName);
    	Iterator<Row> iterator = datatypeSheet.iterator();

        while (iterator.hasNext()) {

            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();

            //Just the specified column will be checked
            while (cellIterator.hasNext()) {

                Cell currentCell = cellIterator.next();
                //System.out.println(currentCell.getCellTypeEnum());
                if (currentCell.getColumnIndex() != targetColumnIndex) {
                	continue;
                }
                //getCellTypeEnum shown as deprecated for version 3.15
                //getCellTypeEnum will be renamed to getCellType starting from version 4.0
                if (currentCell.getCellTypeEnum() == CellType.STRING) {
                    //System.out.print(currentCell.getStringCellValue() + "--");
                	//For medication, remove the "Tab" at the end of term
                	String cellValue = currentCell.getStringCellValue();
                	if (null != cellValue && !cellValue.equals("")) {
                		//cellValue.replaceAll("Tab", "");
                		terms.add(cellValue.replaceAll("c", ""));
                	} else {
                		terms.add(cellValue);
                	}
                    
                } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                    //System.out.print(currentCell.getNumericCellValue() + "--");
                	terms.add(String.valueOf(currentCell.getNumericCellValue()));
                }

            }
            //System.out.println();
        }
        
        return terms;
    }
  
    /**
     * Extract all codes with specified sheet and column name
     * @param datatypeSheet
     * @param columnName
     * @return
     */
    public List<String> getCodes (Sheet datatypeSheet, String columnName) {
    	List<String> codes = new ArrayList<String>();
    	Map<String, Short> columnNameMap = getColumnName(datatypeSheet);
    	int targetColumnIndex = columnNameMap.get(columnName);
    	
    	Iterator<Row> it = datatypeSheet.iterator();
    	while (it.hasNext()) {
    		Row currentRow = it.next();
    		Iterator<Cell> cellIterator = currentRow.iterator();
    		while (cellIterator.hasNext()) {
    			Cell cell = cellIterator.next();
    			if (cell.getColumnIndex() != targetColumnIndex) {
    				continue;
    			} else {
    				String cellValue = "";
    				if (cell.getCellTypeEnum() == CellType.STRING) {
    					//in case the situation: F10.10 Alcohol use disorder, Mild
    					cellValue = cell.getStringCellValue().split(" ")[0];
    					
    				}
    				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
    					cellValue = String.valueOf(cell.getNumericCellValue());
    					//for svc code, some of cell type is numberic, and the point added automatically should be removed
    					if (columnName.equalsIgnoreCase("cpt4_code_id")) {
    						cellValue = StringUtils.substringBefore(cellValue, ".");
    					}
    					
    				}
    				//remove " " before and after the code
    				if (!codes.contains(cellValue.replaceAll(" ", ""))) {
    					codes.add(cellValue.replaceAll(" ", ""));
    				}
    			}
    		}
    	}
    	
    	return codes;
    }
    
    public List<String> getLabs (Sheet datatypeSheet, String columnName) {

    	List<String> terms = new ArrayList<String>();
    	Map<String, Short> columnNameMap = getColumnName(datatypeSheet);
    	int targetColumnIndex = columnNameMap.get(columnName);
    	
    	Iterator<Row> it = datatypeSheet.iterator();
    	while (it.hasNext()) {
    		Row currentRow = it.next();
    		Iterator<Cell> cellIterator = currentRow.iterator();
    		while (cellIterator.hasNext()) {
    			Cell cell = cellIterator.next();
    			if (cell.getColumnIndex() != targetColumnIndex) {
    				continue;
    			} else {
    				String cellValue = "";
    				if (cell.getCellTypeEnum() == CellType.STRING) {
    					//in case the situation: F10.10 Alcohol use disorder, Mild   					
    					if (cell.getStringCellValue().equalsIgnoreCase("NULL")) {
    						continue;
    					}
    					//cellValue = cell.getStringCellValue().replaceAll("Absolute|Non-Dialysis|Qualitative", "");
    					cellValue = cell.getStringCellValue().replaceAll(" ", ""); //the value is code, then remove all " "
    					if (!terms.contains(cellValue)) {
    						terms.add(cellValue);
    					}	
    				}
    				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
    					cellValue = String.valueOf(cell.getNumericCellValue());
    					terms.add(cellValue);
    				}
    				
    			}
    		}
    	}
    	
    	return terms;
    
    }
    
    public Map<String, Short> getColumnName (Sheet datatypeSheet) {
    	Row firstRow = datatypeSheet.getRow(0);
    	short firstColIndex = firstRow.getFirstCellNum();
    	short lastColIndex = firstRow.getLastCellNum();
    	Map<String, Short> columnName = new HashMap<String, Short>();
    	for (short i = firstColIndex; i < lastColIndex; i++) {
    		Cell cell = firstRow.getCell(i);
    		columnName.put(cell.getStringCellValue(), i);
    	}
    	
    	/* Set<Map.Entry<String, Short>> set = columnName.entrySet();
    	for (Map.Entry<String, Short> entry : set) {
    		System.out.println(entry.getKey() + " / " + entry.getValue());
    	}*/
    	
    	return columnName;
    }
}
