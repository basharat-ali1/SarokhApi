package net.sarokh.api.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.itextpdf.html2pdf.HtmlConverter;
import net.sarokh.api.model.entity.ShipmentOrder;

public class PDFPrintFileUtil
{
    private static final String PDF_OUTPUT_DIR = "/home/sarokh/public_html/app/web/bulk_prints/";
    private static final String WEB_URL = "http://app.sarokh.net/web/bulk_prints/";

    public static void main( String[] args ) throws IOException
    {

        HtmlConverter.convertToPdf("", new FileOutputStream("D:\\output\\string-to-pdf.pdf"));

        System.out.println( "PDF Created!" );
    }

    public static String generateBulkShipmentPrintPdf(List<ShipmentOrder> shipmentOrders) {
        String html = "";
        String filePath = PDF_OUTPUT_DIR + new Date().getTime() + "_print.pdf";

        for (ShipmentOrder shipment: shipmentOrders){
            String shipmentHtml = getShipmentPrintHtml(shipment);
            html += "<br/><br/>" + shipmentHtml ;
        }

        try {
            HtmlConverter.convertToPdf(html, new FileOutputStream(filePath));
            System.out.println("Bulk Shipment PDF Created: " + filePath);
            filePath = filePath.replace(PDF_OUTPUT_DIR, WEB_URL);
        } catch (Exception e){
            e.printStackTrace();
        }

        return filePath;
    }

    private static String getShipmentPrintHtml(ShipmentOrder shipment){
        String shipmentHtml = "<table width='100%' border='1'>" +
                "                <tbody >" +
                "                    <tr >" +
                "                        <td  colspan='2' style='padding: 10px 0;'>" +
                "                            <img  alt='Logo' src='http://app.sarokh.net/web/assets/img/brand/sarokh-logo.png'>" +
                "                        </td>" +
                "                        <td  colspan='3'>" +
                "                            <img  alt='Logo' src='" + shipment.getShipmentOrderItems().get(0).getBarCode() + "'>" +
                "                        </td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td  colspan='3' rowspan='4'>From</td>" +
                "                        <td >" +
                "                            <strong >Company Name</strong>" +
                "                        </td>" +
                "                        <td >ABC</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td >" +
                "                            <strong >Address</strong>" +
                "                        </td>" +
                "                        <td >Shipper Address</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td >" +
                "                            <strong >City</strong>" +
                "                        </td>" +
                "                        <td >" + shipment.getShipFromCity() + "</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td >" +
                "                            <strong >Phone</strong>" +
                "                        </td>" +
                "                        <td >" + shipment.getShipmentOrderItems().get(0).getContact() + "</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td  colspan='2'>&nbsp;</td>" +
                "                        <td  rowspan='4' style='font-size: 18px; font-weight: bold;'>To</td>" +
                "                        <td >" +
                "                            <strong >Customer Name</strong>" +
                "                        </td>" +
                "                        <td >" + shipment.getShipmentOrderItems().get(0).getReceiverName() + "</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td  colspan='2' rowspan='2' style='font-size: 18px; font-weight: bold;'>" + shipment.getOrderId() + "</td>" +
                "                        <td >" +
                "                            <strong >Address</strong>" +
                "                        </td>" +
                "                        <td >" + shipment.getShipmentOrderItems().get(0).getAddress() + "</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td >" +
                "                            <strong >City</strong>" +
                "                        </td>" +
                "                        <td >" + shipment.getShipToCity() + "</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td  colspan='2'>&nbsp;</td>" +
                "                        <td >" +
                "                            <strong >Phone</strong>" +
                "                        </td>" +
                "                        <td >" + shipment.getShipmentOrderItems().get(0).getContact() + "</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td  colspan='2'>&nbsp;</td>" +
                "                        <td  style='font-size: 15px; font-weight: bold;'>" + shipment.getShipFromCity() + "</td>" +
                "                        <td  colspan='2'>For Sender Only</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td  colspan='2'>Clasification</td>" +
                "                        <td >To</td>" +
                "                        <td  colspan='2' rowspan='2'>" +
                "                            <img  alt='QR Code' style='width: 80px' src='" + shipment.getShipmentOrderItems().get(0).getQRCode() + "'>" +
                "                        </td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td  colspan='2'>&nbsp;</td>" +
                "                        <td  style='font-size: 15px; font-weight: bold;'>" + shipment.getShipToCity() + "</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td >" +
                "                            <strong >Pickup Date</strong>" +
                "                        </td>" +
                "                        <td  colspan='2'>" + shipment.getCreatedDatetime() + "</td>" +
                "                        <td >" +
                "                            <strong >Service</strong>" +
                "                        </td>" +
                "                        <td  colspan='2'>" + shipment.getShipmentOrderItems().get(0).getPaymentType() + "</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td >" +
                "                            <strong >Pices</strong>" +
                "                        </td>" +
                "                        <td  colspan='2'>1 of 1</td>" +
                "                        <td >" +
                "                            <strong >Weight</strong>" +
                "                        </td>" +
                "                        <td  colspan='2'>" + shipment.getShipmentOrderItems().get(0).getWeight() + "</td>" +
                "                    </tr>" +
                "                    <tr >" +
                "                        <td >" +
                "                            <strong >Reference</strong>" +
                "                        </td>" +
                "                        <td  colspan='2'>xxxxx</td>" +
                "                        <td >" +
                "                            <strong >Notes</strong>" +
                "                        </td>" +
                "                        <td  colspan='2'>" + shipment.getShipmentOrderItems().get(0).getAdditionalServices() + "</td>" +
                "                    </tr>" +
                "                </tbody>" +
                "            </table>";

        return shipmentHtml;
    }
}