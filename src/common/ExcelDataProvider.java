package common;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.NotFoundException;

public class ExcelDataProvider extends TestDataProvider {

	private static final int COLUMN_TEST_CASE_NAME = 0;

	private FileInputStream excelFile;

	private XSSFSheet excelWSheet;
	private XSSFWorkbook excelWBook;
	private XSSFCell cell;
	// private XSSFRow row;

	public ExcelDataProvider(String workBookPath, String sheetName) throws Exception {

		// Open the Excel file
		this.excelFile = new FileInputStream(workBookPath);
		// Access the required test data sheet
		this.excelWBook = new XSSFWorkbook(excelFile);
		this.excelWSheet = excelWBook.getSheet(sheetName);

	}

	/**
	 * Gets the Data inside a Cell
	 * 
	 * @param (int)
	 *            row number
	 * @param (int)
	 *            column number
	 * @return (String) Cell Data
	 */
	public String getCellData(int rowNum, int colNum) throws Exception {
		String cellData = null;

		try {
			cell = excelWSheet.getRow(rowNum).getCell(colNum);
			cellData = cell.getStringCellValue();

			return cellData;

		} catch (Exception e) {
			return "Exception catched in getCellData method";
		} finally {
			try {
				if (excelFile != null)
					excelFile.close();
			} catch (IOException ex) {
				return "Exception catched in getCellData method";
			}
		}
	}

	/**
	 * Gets the Row Number of the specific TestCase
	 * 
	 * @param (String)
	 *            Test Case Name
	 * @return (int) Row Number
	 * 
	 */
	public int getTestCaseRow(String sTestCaseName) {
		int i = 0;
		boolean foundFlag = false;
		Logger.log("TestCaseName: " + sTestCaseName);
		try {

			int rowCount = getRowUsed();

			for (i = 0; i <= rowCount; i++) {

				if (getCellData(i, COLUMN_TEST_CASE_NAME).equals((sTestCaseName))) {
					foundFlag = true;
					break;
				}
			}
			if (!foundFlag) {
				Exception e = new NotFoundException("Test Case Name in Data Sheet not found: " + sTestCaseName);
				throw e;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * Gets the total number of rows used inside the worksheet
	 * 
	 * @return (int) Total Number of Rows
	 */
	public int getRowUsed() throws Exception {
		try {
			int RowCount = excelWSheet.getLastRowNum();
			return RowCount;
		} catch (Exception e) {
			Logger.log(e.getMessage());
			throw (e);
		}

	}

	@Override
	public void close() throws IOException {
		if (excelFile != null) {
			Logger.log("Closing Excel File Inputstream");
			this.excelFile.close();
		}
		
	}


}
