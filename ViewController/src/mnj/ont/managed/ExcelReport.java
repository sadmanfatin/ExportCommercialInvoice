package mnj.ont.managed;

import java.io.IOException;
import java.io.OutputStream;

import java.text.DecimalFormat;

import javax.faces.context.FacesContext;

import mnj.ont.model.services.MainAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelReport {
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFCellStyle style1 = workbook.createCellStyle();
    HSSFSheet worksheet = null;
    HSSFRow excelrow = null;
    HSSFCell excelcell = null;
    HSSFCellStyle tableHeaderStyle = workbook.createCellStyle();
    // HSSFCellStyle borderstyle = workbook.createCellStyle();
    HSSFCellStyle headerStyle = workbook.createCellStyle();
    HSSFCellStyle dataStyle = workbook.createCellStyle();
    HSSFFont hSSFFont1 = workbook.createFont();
    HSSFFont hSSFFont2 = workbook.createFont();
    HSSFPalette palette = workbook.getCustomPalette();
    StringBuilder sb;
    StringBuilder sb1;
    
    public ExcelReport() {
    }
    
    MainAMImpl appM =getAppModuleImpl();

    private MainAMImpl getAppModuleImpl() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContainer.findDataControl("MainAMDataControl"); // Name of application module in datacontrolBinding.cpx
        MainAMImpl appM = (MainAMImpl)dc.getDataProvider();
        return appM;
    }

    public void invoicExcelReport(FacesContext facesContext,
                                  OutputStream outputStream) {        ViewObject cilineVo = appM.getCiLines1();
        String packingList=cilineVo.getCurrentRow().getAttribute("PackListNo").toString();    
       
        ViewObject excelVo = appM.getExcelVO1();
        excelVo.setWhereClause("PACKING_LIST='"+packingList+"'");
        excelVo.executeQuery();

//...............

            
        worksheet = workbook.createSheet("sheet1" );
        //create 200 rows in work sheet initially
        for (int i = 0; i < 200; i++) {
            worksheet.createRow(i);
        } 
        
        
        
        
        
        int rowPos=26;
        double totalQty=0.0;
        double totalNetNetW=0.0;
        double totalNetW=0.0;

        int sizeHeaderRowStart =18;
       int header_TRowStart=15;

        
       printHeaderForm() ;
       sizeHeaderTable(header_TRowStart);
        
        reportView2SizeHeaderRow(sizeHeaderRowStart);
        
        ViewObject headerVo = appM.getCiHeaders1();
        ViewObject lineVo = appM.getExcelVO1();
        lineVo.setRangeSize(50);
        Row[] lineRows = lineVo.getAllRowsInRange();

        String orderbpo=cilineVo.getCurrentRow().getAttribute("OrderBpo").toString();
        String unitCost=cilineVo.getCurrentRow().getAttribute("UnitPrice").toString();
        String currency=cilineVo.getCurrentRow().getAttribute("Currency").toString();
        String description=cilineVo.getCurrentRow().getAttribute("ItemDescription").toString(); 
       


        String factory_name=headerVo.getCurrentRow().getAttribute("OrgNameLov").toString();
        
        rowPos=19;
        for(Row lineRow:lineRows){
            excelcell=worksheet.getRow(rowPos).createCell(9);
            excelcell.setCellValue(factory_name);
            excelcell=worksheet.getRow(rowPos).createCell(11);
             excelcell.setCellValue(currency);
            
           excelcell=worksheet.getRow(rowPos).createCell(6);
            String NetNetWeight=lineRow.getAttribute("NetNetWeight").toString();
            excelcell.setCellValue(NetNetWeight);
            double NNweightD=Double.valueOf(NetNetWeight);
            totalNetNetW=totalNetNetW+NNweightD;      
       
            
           excelcell=worksheet.getRow(rowPos).createCell(7);
            String Nweight=lineRow.getAttribute("Nweight").toString();
           // excelcell.setCellValue(lineRow.getAttribute("Nweight").toString());
           excelcell.setCellValue(Nweight);
            double NweightD=Double.valueOf(Nweight);
            totalNetW=totalNetW+NweightD;
                                             
            excelcell=worksheet.getRow(rowPos).createCell(2);
            excelcell.setCellValue(description);
            excelcell=worksheet.getRow(rowPos).createCell(3);
            excelcell.setCellValue(lineRow.getAttribute("Color").toString());
           excelcell=worksheet.getRow(rowPos).createCell(4);
            excelcell.setCellValue(lineRow.getAttribute("SizeNo").toString()); 
            excelcell=worksheet.getRow(rowPos).createCell(0);
            excelcell.setCellValue(lineRow.getAttribute("Bpo").toString());
           excelcell=worksheet.getRow(rowPos).createCell(12);
            excelcell.setCellValue(unitCost);
            excelcell=worksheet.getRow(rowPos).createCell(10);
            String quantity=lineRow.getAttribute("Quantity").toString();
            excelcell.setCellValue(quantity);
            double unitC=Double.valueOf(unitCost);
            double qty=Double.valueOf(quantity);
            double totalUnitCost=qty*unitC;
            
            
            totalQty=totalQty+qty;
            
            excelcell=worksheet.getRow(rowPos).createCell(13); 
            excelcell.setCellValue(new DecimalFormat("##.###").format(totalUnitCost));
           // excelcell.setCellValue(String.valueOf(totalUnitCost));
            
            excelcell=worksheet.getRow(rowPos).createCell(16);
            excelcell.setCellValue(new DecimalFormat("##.##").format(totalUnitCost));
            
            excelcell=worksheet.getRow(rowPos).createCell(8);
            excelcell.setCellValue("BD");
            
            rowPos++;
        }
        totalSum(totalNetW,totalNetNetW,totalQty,rowPos);
        headerTableValues(header_TRowStart);
        try {
           workbook.write(outputStream);
           outputStream.flush();
        } catch (IOException e) {
        }
    }
    
    private void headerTableValues(int header_TRowStart) {
        
        ViewObject headerVo = appM.getCiHeaders1();
        ViewObject cilineVo = appM.getCiLines1();
        
        HSSFCellStyle headerBorder1 = workbook.createCellStyle();
        headerBorder1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerBorder1.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        String invoiceNo=headerVo.getCurrentRow().getAttribute("InvoiceNo").toString();
        String invoiceDate=headerVo.getCurrentRow().getAttribute("InvoiceDate2").toString();
        
        String grossWeight=cilineVo.getCurrentRow().getAttribute("GrossWeight").toString();
        String shipmentMode=cilineVo.getCurrentRow().getAttribute("TransportationMode").toString();
        String country=cilineVo.getCurrentRow().getAttribute("Country").toString();
        String cartonQuantity=cilineVo.getCurrentRow().getAttribute("CartonQuantity").toString();
        String termOfSale=cilineVo.getCurrentRow().getAttribute("IncoTermsValue").toString();
        
        
        worksheet.addMergedRegion(new CellRangeAddress(header_TRowStart+1,header_TRowStart+1,13,14));
        excelcell=worksheet.getRow(header_TRowStart+1).createCell(13);
       excelcell.setCellValue(grossWeight);
        excelcell.setCellStyle(headerBorder1);
        excelcell = worksheet.getRow(1).createCell(2);      
        excelcell.setCellValue(invoiceNo);
        excelcell = worksheet.getRow(2).createCell(2);      
        excelcell.setCellValue(invoiceDate);
        worksheet.addMergedRegion(new CellRangeAddress(header_TRowStart+1,header_TRowStart+1,2,3));
        worksheet.addMergedRegion(new CellRangeAddress(header_TRowStart+1,header_TRowStart+1,4,5)); 
        worksheet.addMergedRegion(new CellRangeAddress(header_TRowStart+1,header_TRowStart+1,0,1)); 
        excelcell=worksheet.getRow(header_TRowStart+1).createCell(0);
        excelcell.setCellValue(shipmentMode);
        excelcell.setCellStyle(headerBorder1);
        worksheet.addMergedRegion(new CellRangeAddress(header_TRowStart+1,header_TRowStart+1,6,7)); 
        excelcell=worksheet.getRow(header_TRowStart+1).createCell(6);
        excelcell.setCellValue(country); 
        excelcell.setCellStyle(headerBorder1);
        worksheet.addMergedRegion(new CellRangeAddress(header_TRowStart+1,header_TRowStart+1,10,12)); 
        worksheet.addMergedRegion(new CellRangeAddress(header_TRowStart+1,header_TRowStart+1,15,16));
        excelcell=worksheet.getRow(header_TRowStart+1).createCell(15);
        excelcell.setCellValue(cartonQuantity);
        excelcell.setCellStyle(headerBorder1);
        worksheet.addMergedRegion(new CellRangeAddress(header_TRowStart+1,header_TRowStart+1,8,9)); 
        excelcell=worksheet.getRow(header_TRowStart+1).createCell(8);
        excelcell.setCellValue(termOfSale);
        excelcell.setCellStyle(headerBorder1);
        worksheet.addMergedRegion(new CellRangeAddress(header_TRowStart+2,header_TRowStart+2,0,16));
    }
    
    private void totalSum(double totalNetW,double totalNetNetW,double totalQty,int rowPos) {
        worksheet.addMergedRegion(new CellRangeAddress(rowPos,rowPos,1,5));
        worksheet.addMergedRegion(new CellRangeAddress(rowPos,rowPos,8,9));
        worksheet.addMergedRegion(new CellRangeAddress(rowPos,rowPos,11,16));
        excelcell=worksheet.getRow(rowPos).createCell(0);
        excelcell.setCellValue("TOTAL:");
        excelcell=worksheet.getRow(rowPos).createCell(7);
        excelcell.setCellValue(new DecimalFormat("##.##").format(totalNetW));
        excelcell=worksheet.getRow(rowPos).createCell(6);
        excelcell.setCellValue(new DecimalFormat("##.##").format(totalNetNetW));
        excelcell=worksheet.getRow(rowPos).createCell(10);
        excelcell.setCellValue(new DecimalFormat("##.##").format(totalQty));
        
        
    }
    private void printHeaderForm() {
        worksheet.addMergedRegion(new CellRangeAddress(0,0,0,17));
        HSSFCellStyle style11 = workbook.createCellStyle();
        for(int i=0;i<17;i++)  {
           style11.setBorderBottom(HSSFCellStyle.BORDER_THIN);
           style11.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            excelcell = worksheet.getRow(0).createCell(i);
            excelcell.setCellStyle(style11);
        }
        excelcell = worksheet.getRow(0).createCell(0);
        style11.setAlignment(style11.ALIGN_CENTER);        
        excelcell.setCellValue("COMMERCIAL INVOICE");
        excelcell.setCellStyle(style11);
        
        for(int i=0;i<19;i++) {
            HSSFCellStyle style12 = workbook.createCellStyle();
            style12.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style12.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            excelcell = worksheet.getRow(i).createCell(17);
            excelcell.setCellStyle(style12);
        }
        worksheet.addMergedRegion(new CellRangeAddress(1,1,0,1));
        worksheet.addMergedRegion(new CellRangeAddress(1,1,2,3));
        excelcell = worksheet.getRow(1).createCell(0);      
        excelcell.setCellValue("Invoice Number:");
        worksheet.addMergedRegion(new CellRangeAddress(2,2,0,1));
        worksheet.addMergedRegion(new CellRangeAddress(2,2,2,3));
        excelcell = worksheet.getRow(2).createCell(0);      
        excelcell.setCellValue("Invoice Date:");
        
        excelcell = worksheet.getRow(8).createCell(0);      
        excelcell.setCellValue("Purchaser:");
        excelcell = worksheet.getRow(8).createCell(6);      
        excelcell.setCellValue("Ship To:");
    }
    
    private void sizeHeaderTable(int sizeHeaderRowStart) {
        
     HSSFCellStyle headerBorder = workbook.createCellStyle();
       
        for(int i=0;i<17;i++) {

            headerBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headerBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex()); 
            excelcell=worksheet.getRow(sizeHeaderRowStart-1).createCell(i);
            excelcell.setCellStyle(headerBorder);
            
            HSSFCellStyle headerBorder1 = workbook.createCellStyle();
            headerBorder1.setBorderTop(HSSFCellStyle.BORDER_THIN);
            headerBorder1.setTopBorderColor(IndexedColors.BLACK.getIndex());
            excelcell=worksheet.getRow(sizeHeaderRowStart+1).createCell(i);
            excelcell.setCellStyle(headerBorder1); 
        }
        

        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(0);
        excelcell.setCellValue("Ship Mode");
    
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(2);
        excelcell.setCellValue("Transfer Point ");   

        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(4);
        excelcell.setCellValue("Port of loading ");

        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(6);
        excelcell.setCellValue("Final Destination"); 
   
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(8);
        excelcell.setCellValue("Term of Sale");
   
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(10);
        excelcell.setCellValue("Payment Term");
    
       excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(13);
       excelcell.setCellValue("Gross Weight(Kg)");
       excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(15);
       excelcell.setCellValue("Total Cartons");

        
    }
    
    private void reportView2SizeHeaderRow(int sizeHeaderRowStart) {
        HSSFCellStyle headBorder = workbook.createCellStyle();
        headBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headBorder.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headBorder.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headBorder.setBottomBorderColor(IndexedColors.BLACK.getIndex()); 
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(0);
        excelcell.setCellValue("Destination Purchase Order");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(1);
        excelcell.setCellValue("SKU ");   
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(2);
        excelcell.setCellValue("Description ");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(3);
        excelcell.setCellValue("Color"); 
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(4);
        excelcell.setCellValue("Size");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(5);
        excelcell.setCellValue("Total Peices");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(6);
        excelcell.setCellValue("Total Net Net Weight(Kg)");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(7);
        excelcell.setCellValue("Total Net Weight(Kg)");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(8);
        excelcell.setCellValue("Country of Origin");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(9);
        excelcell.setCellValue("Factory Name");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(10);
        excelcell.setCellValue("Quantity Invoiced(Each)");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(11);
        excelcell.setCellValue("Currency");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(12);
        excelcell.setCellValue("Unit Cost");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(13);
        excelcell.setCellValue("Total Unit Cost");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(14);
        excelcell.setCellValue("Hard Tag Unit Cost");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(15);
        excelcell.setCellValue("Total Hard Tag Unit Cost");
        excelcell.setCellStyle(headBorder);
        excelcell=worksheet.getRow(sizeHeaderRowStart).createCell(16);
        excelcell.setCellValue("Extended Line Total");
        excelcell.setCellStyle(headBorder);
    }

}
