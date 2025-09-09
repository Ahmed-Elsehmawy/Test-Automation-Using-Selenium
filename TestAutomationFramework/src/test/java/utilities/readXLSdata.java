package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

/**
 * Utility class to provide test data from Excel sheets to TestNG tests.
 * <p>
 * Uses Apache POI to read an Excel workbook and returns data as a
 * two-dimensional {@link String} array for consumption by tests.
 * </p>
 * <p>
 * Expected usage:
 * <ul>
 *   <li>Each TestNG test method name corresponds to an Excel sheet name.</li>
 *   <li>Excel file should be located in <code>/src/test/resources/testdata/testdata.xlsx</code>.</li>
 *   <li>First row is treated as headers; actual test data begins from the second row.</li>
 * </ul>
 * </p>
 */
public class readXLSdata {
	
    /**
     * Data provider that fetches test data from an Excel sheet matching the test method name.
     * <p>
     * Example: If the test method is named <code>SearchTest</code>, data will be read
     * from a sheet called <code>SearchTest</code> inside <code>testdata.xlsx</code>.
     * </p>
     *
     * @param m the {@link Method} object representing the calling test method, used to determine the sheet name
     * @return a 2D string array with the test data values
     * @throws EncryptedDocumentException if the Excel file is password-protected or unreadable
     * @throws IOException if there is an error reading the file
     */
	@DataProvider(name="datatest")
	public String[][] getData(Method m) throws EncryptedDocumentException, IOException {
		
		String excelSheetName = m.getName();
	    File f = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\testdata.xlsx");
	    FileInputStream fis = new FileInputStream(f);
	    Workbook wb = WorkbookFactory.create(fis);
	    Sheet sheetName = wb.getSheet(excelSheetName);

	    int totalRows = sheetName.getLastRowNum();
	    System.out.println(totalRows);

	    Row rowCells = sheetName.getRow(0);
	    int totalCols = rowCells.getLastCellNum();
	    System.out.println(totalCols);

	    DataFormatter format = new DataFormatter();
	    
	    String testData[][]= new String[totalRows][totalCols];
	    for (int i=1; i<=totalRows; i++) {
	    	for(int j=0; j<totalCols; j++) {
	    		testData[i-1][j] = format.formatCellValue(sheetName.getRow(i).getCell(j));
	    		System.out.println();
	    	}
	    }
	    return testData;
	}
}
