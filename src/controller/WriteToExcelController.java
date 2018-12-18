package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;

public class WriteToExcelController {


	public WriteToExcelController() {

	}
	public void execute(TableView table, ArrayList<String> columnHeaderList,File file) {
		Workbook workbook = new HSSFWorkbook();
		Sheet spreadsheet = workbook.createSheet("Utskrift");

		Row row = spreadsheet.createRow(0);

		for (int j = 0; j < table.getColumns().size(); j++) {
			System.out.println(j);


			row.createCell(j).setCellValue(columnHeaderList.get(j));

		}

		for (int i = 0; i < table.getItems().size(); i++) {
			row = spreadsheet.createRow(i + 1);
			for (int j = 0; j < table.getColumns().size(); j++) {
				if(((TableColumnBase) table.getColumns().get(j)).getCellData(i) != null) { 
					row.createCell(j).setCellValue(((TableColumnBase) table.getColumns().get(j)).getCellData(i).toString()); 
				}
				else {
					row.createCell(j).setCellValue("empty");
				}   
			}
		}

		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(file.toString()+".xls");
			workbook.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
