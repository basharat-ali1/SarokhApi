package net.sarokh.api.excel;

import net.sarokh.api.model.order.OrderNewFormDTO;
import net.sarokh.api.model.order.OrderShipmentItemDTO;
import net.sarokh.api.util.ApplicationUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class ExcelReader {

    public static List<OrderShipmentItemDTO> getShipmentItemsFromExcel(OrderNewFormDTO order, String filePath){
        List<OrderShipmentItemDTO> shipmentItemsList = new ArrayList<>();

        try {
            //FileInputStream file = new FileInputStream(new File("./order.xlsx"));
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);

            Sheet sheet = workbook.getSheetAt(0);

            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            for (Row row: sheet) {
                if (row.getLastCellNum() == 13 && row.getRowNum() != 0 ) {
                    Double shipmentValue = ApplicationUtil.isNotNullAndEmpty(dataFormatter.formatCellValue(row.getCell(4))) ? Double.parseDouble(dataFormatter.formatCellValue(row.getCell(4))) : 0;
                    Double codAmount = ApplicationUtil.isNotNullAndEmpty(dataFormatter.formatCellValue(row.getCell(6)) ) ? Double.parseDouble(dataFormatter.formatCellValue(row.getCell(6))) : 0;
                    OrderShipmentItemDTO shipment = OrderShipmentItemDTO.builder()
                            .receiverName(dataFormatter.formatCellValue(row.getCell(0)))
                            .receiverMobileNumber(dataFormatter.formatCellValue(row.getCell(1)))
                            .shipmentTitle(dataFormatter.formatCellValue(row.getCell(2)))
                            .shipmentContent(dataFormatter.formatCellValue(row.getCell(3)))
                            .shipmentValue(shipmentValue)
                            .paymentType(dataFormatter.formatCellValue(row.getCell(5)))
                            .codAmount(codAmount)
                            .shipmentType(dataFormatter.formatCellValue(row.getCell(7)))
                            .weight(dataFormatter.formatCellValue(row.getCell(8)))
                            .locationLatitude(dataFormatter.formatCellValue(row.getCell(9)))
                            .locationLongitude(dataFormatter.formatCellValue(row.getCell(10)))
                            .receiverAddress(dataFormatter.formatCellValue(row.getCell(11)))
                            .additionalServices(dataFormatter.formatCellValue(row.getCell(12)))
                            .build();

                    shipmentItemsList.add(shipment);
                }
            }

            workbook.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }

        return shipmentItemsList;
    }

    public static void readExcel(){
        try {
            FileInputStream file = new FileInputStream(new File("./sample.xlsx"));
            Workbook workbook = new XSSFWorkbook(file);

            // Retrieving the number of sheets in the Workbook
            System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
            /*
                System.out.println("Retrieving Sheets");
                for(Sheet sheet: workbook) {
                    System.out.println("=> " + sheet.getSheetName());
                }
             */

            Sheet sheet = workbook.getSheetAt(0);

            // Create a DataFormatter to format and get each cell's value as String
            DataFormatter dataFormatter = new DataFormatter();

            for (Row row: sheet) {
                for(Cell cell: row) {
                    String cellValue = dataFormatter.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                }
                System.out.println();
            }

            // Closing the workbook
            workbook.close();

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        readExcel();
    }


}
