package com.app.payu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaveToXLSX {
	public static int process(ArrayList<DataObjectMerged> data) throws IOException
	{
		String excelFileName = PayUAppConstants.WORKING_DIR+"/ZestawieniePayU.xlsx";
		//name of excel file
		
		System.out.println("Saving Excel file...");

		String sheetName = "Zestawienie PayU";//name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName);
		
		// fonts definitions
		
		XSSFFont defaultFont = wb.createFont();
	    defaultFont.setFontHeightInPoints((short)10);
	    defaultFont.setFontName("Calibri");
	    defaultFont.setColor(IndexedColors.BLACK.getIndex());
	    defaultFont.setBold(false);
	    defaultFont.setItalic(false);

	    XSSFFont boldFont = wb.createFont();
	    boldFont.setBold(true);
	    
	    XSSFFont whiteFont = wb.createFont();
	    whiteFont.setFontHeightInPoints((short)10);
	    whiteFont.setFontName("Calibri");
	    whiteFont.setColor(IndexedColors.WHITE.getIndex());
	    whiteFont.setBold(false);
	    whiteFont.setItalic(false);
		
		// styles definitions
		
		CellStyle style;
		CellStyle style_price2;
		CellStyle style_default = wb.createCellStyle();
		CellStyle style_red = wb.createCellStyle();
		CellStyle style_yellow = wb.createCellStyle();
		CellStyle style_gray = wb.createCellStyle();
		CellStyle style_title = wb.createCellStyle();
		CellStyle style_prices = wb.createCellStyle();
		CellStyle style_prices_yellow = wb.createCellStyle();
		CellStyle style_prices_red = wb.createCellStyle();
		CellStyle style_prices_gray = wb.createCellStyle();
		
		style_prices.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("0.00"));
		
		style_prices_yellow.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("0.00"));
		style_prices_yellow.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style_prices_yellow.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		style_prices_red.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("0.00"));
		style_prices_red.setFillForegroundColor(IndexedColors.RED1.getIndex());
		style_prices_red.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style_prices_red.setFont(whiteFont);
		
		style_prices_gray.setDataFormat(wb.getCreationHelper().createDataFormat().getFormat("0.00"));
		style_prices_gray.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style_prices_gray.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		style_red.setFillForegroundColor(IndexedColors.RED1.getIndex());
		style_red.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style_red.setFont(whiteFont);
		
		style_yellow.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		style_yellow.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		style_gray.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style_gray.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		style_title.setFont(boldFont);
		
		// write data to workbook
		
		XSSFRow row = sheet.createRow(0);
		row.setRowStyle(style_title);
		
		XSSFCell cell = row.createCell(0);
		cell.setCellValue("ID P³atnoœci");
		sheet.setColumnWidth(0, 3072);
		cell.setCellStyle(style_title);
		
		cell = row.createCell(1);
		cell.setCellValue("Data P³atnoœci");
		sheet.setColumnWidth(1, 4350);
		cell.setCellStyle(style_title);
		
		cell = row.createCell(2);
		cell.setCellValue("Login kupuj¹cego");
		sheet.setColumnWidth(2, 4096);
		cell.setCellStyle(style_title);
		
		cell = row.createCell(3);
		cell.setCellValue("Kwota");
		sheet.setColumnWidth(3, 2248);
		cell.setCellStyle(style_title);
		
		cell = row.createCell(4);
		cell.setCellValue("Lista transakcji");
		sheet.setColumnWidth(4, 10240);
		cell.setCellStyle(style_title);
		
		cell = row.createCell(5);
		cell.setCellValue("ID Baselinker");
		sheet.setColumnWidth(5, 3072);
		cell.setCellStyle(style_title);
		
		cell = row.createCell(6);
		cell.setCellValue("Nr faktury w GT");
		sheet.setColumnWidth(6, 5800);
		cell.setCellStyle(style_title);
		
		cell = row.createCell(7);
		cell.setCellValue("ML");
		sheet.setColumnWidth(7, 1024);
		cell.setCellStyle(style_title);
		
		cell = row.createCell(8);
		cell.setCellValue("Ostrze¿enia");
		sheet.setColumnWidth(8, 12288);
		cell.setCellStyle(style_title);
		
		sheet.createFreezePane(0, 1);
		
		//iterating r number of rows
		for (int i = 0; i < data.size(); i++ )
		{
			row = sheet.createRow(i+1);
			
			// select style a style
			style = style_default;
			style_price2 = style_prices;
			
			if(data.get(i).MatchLevel<3)
			{
				style = style_yellow;
				style_price2 = style_prices_yellow;
				
				if(data.get(i).MatchLevel==0)
				{
					style = style_red;
					style_price2 = style_prices_red;
				}
			}
			
			if(data.get(i).InvoiceId.contains("Brak") && data.get(i).MatchLevel==3)
			{
				style = style_gray;
				style_price2 = style_prices_gray;
			}
			
			
			// Zapis danych do arkusza
			
			cell = row.createCell(0);
			cell.setCellValue(data.get(i).PaymentID);
			cell.setCellStyle(style);
			
			String tmp = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(data.get(i).PaymentDate);
			
			cell = row.createCell(1);
			cell.setCellValue(tmp);
			cell.setCellStyle(style);
			
			cell = row.createCell(2);
			cell.setCellValue(data.get(i).UserID);
			cell.setCellStyle(style);
			
			cell = row.createCell(3);
			cell.setCellValue(data.get(i).TotalCash);
			cell.setCellStyle(style_price2);
			
			tmp="";
			for(int j=0; j<data.get(i).Transactions.size(); j++)
			{
				tmp+=data.get(i).Transactions.get(j)+"; ";
			}
			
			cell = row.createCell(4);
			cell.setCellValue(tmp);
			cell.setCellStyle(style);
			
			cell = row.createCell(5);
			cell.setCellValue(data.get(i).OrderId);
			cell.setCellStyle(style);
			
			cell = row.createCell(6);
			cell.setCellValue(data.get(i).InvoiceId);
			cell.setCellStyle(style);
			
			cell = row.createCell(7);
			cell.setCellValue(data.get(i).MatchLevel);
			cell.setCellStyle(style);
			
			cell = row.createCell(8);
			cell.setCellValue(data.get(i).Warnings);
			cell.setCellStyle(style);
			
			row.setRowStyle(style);
		}

		FileOutputStream fileOut = new FileOutputStream(excelFileName);

		//write this workbook to an Outputstream.
		wb.write(fileOut);
		wb.close();
		fileOut.flush();
		fileOut.close();
		
		System.out.println("File saved.");
		
		return 0;
	}
}
