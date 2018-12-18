package controller;

import java.io.FileOutputStream;

import javafx.scene.control.TableView;

public class WriteToExcelController {

	
	public WriteToExcelController() {
		

		
	}
	public void execute(TableView table) {
		//
//        Workbook workbook = new HSSFWorkbook();
//        Sheet spreadsheet = workbook.createSheet("sample");
//
//        Row row = spreadsheet.createRow(0);
//
//        for (int j = 0; j < table.getColumns().size(); j++) {
//            row.createCell(j).setCellValue(table.getColumns().get(j).getText());
//        }
//
//        for (int i = 0; i < table.getItems().size(); i++) {
//            row = spreadsheet.createRow(i + 1);
//            for (int j = 0; j < table.getColumns().size(); j++) {
//                if(table.getColumns().get(j).getCellData(i) != null) { 
//                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString()); 
//                }
//                else {
//                    row.createCell(j).setCellValue("");
//                }   
//            }
//        }
//
//        FileOutputStream fileOut = new FileOutputStream("workbook.xls");
//        workbook.write(fileOut);
//        fileOut.close();
	}
}
