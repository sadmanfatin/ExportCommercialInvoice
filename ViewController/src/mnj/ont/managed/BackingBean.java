package mnj.ont.managed;

import java.io.OutputStream;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import mnj.ont.model.services.MainAMImpl;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.nav.RichCommandButton;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.LaunchPopupEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;

import oracle.adf.view.rich.event.ReturnPopupEvent;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.RowSetIterator;
import oracle.jbo.ViewObject;

import oracle.jdbc.internal.OracleTypes;

import org.apache.myfaces.trinidad.event.AttributeChangeEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;

public class BackingBean {
    private RichTable linesTable;
    private RichInputText orgId;
    private RichInputText exportLC;
    private RichInputText styleNo;
    private RichInputText bpo;
    private RichPanelFormLayout panelFormLayoutBean;
    private RichInputDate contractLCDateBean;
    private RichInputDate expDate;
    private RichTable searchPageTable;
    private RichInputText dcpo;
    
    MainAMImpl appM = getAppModuleImpl();
    private RichTable ciLine2Table;
    private RichInputText exp_no;
    private RichCommandButton fillStyleButton;
    private RichTable asdasd;
    private RichPanelLabelAndMessage trxNumber;
    private RichInputText totalCartonQty;
    private RichInputText packListNo;
    private HtmlOutputText commercialInvoiceDocNo;
    private RichCommandButton searchDocNoForPackList;
    private RichPanelFormLayout searchCIDocNoBlock;
    private RichOutputText commercilInvDocNo;

    public MainAMImpl getAppModuleImpl() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContainer.findDataControl("MainAMDataControl"); // Name of application module in datacontrolBinding.cpx
        MainAMImpl appM = (MainAMImpl)dc.getDataProvider();
        return appM;
    }
    
    
    

    public void setLinesTable(RichTable linesTable) {
        this.linesTable = linesTable;
    }

    public RichTable getLinesTable() {
        return linesTable;
    }
    
    /** Bean Coding For FILL Data Tab **/
    
    public void editPopupFetchListener(PopupFetchEvent popupFetchEvent) {
                  
            
            setWhereClause();
            
              if (popupFetchEvent.getLaunchSourceClientId().contains("cbInsert")) {
                
                  BindingContainer bindings = getBindings();
                  OperationBinding operationBinding = 
                                          bindings.getOperationBinding("CreateInsert");
                  operationBinding.execute();
              }
          }
    public void setWhereClause(){
        
        
        OperationBinding operationBinding = executeOperation("populateLines1");
       //  system.out.println("sabih Error 1   "+getOrgId().getValue());
        operationBinding.getParamsMap().put("OrgId", getOrgId().getValue());
       //  system.out.println("sabih Error 2   "+getOrgId().getValue());
        operationBinding.execute();
        if (!operationBinding.getErrors().isEmpty()) {
          //  system.out.println("if errors-->");
    //            List errors = operationBinding.getErrors();
    //           //  system.out.println(@);
        }
    }
    
    public void editDialogListener(DialogEvent dialogEvent) {
           if (dialogEvent.getOutcome().name().equals("ok")) {     
            FillData();
               AdfFacesContext.getCurrentInstance().addPartialTarget(linesTable);    
           } else if (dialogEvent.getOutcome().name().equals("cancel")) {          
            ;
           }
       }
    
    public  void FillData() {
        OperationBinding operationBinding = executeOperation("callFetch");
        operationBinding.execute();
       }
    public BindingContainer getBindings() {
           return BindingContext.getCurrent().getCurrentBindingsEntry();
       }
    
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindingsCont().getOperationBinding(operation);
        return createParam;

    }
    
    /*****Generic Method to Get BindingContainer**/
        public BindingContainer getBindingsCont() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
        }


    public void editPopupCancelListener(PopupCanceledEvent popupCanceledEvent) {
           
       }


    /** End Bean Coding For FILL Data Tab **/
    public void setOrgId(RichInputText orgId) {
        this.orgId = orgId;
    }

    public RichInputText getOrgId() {
        return orgId;
    }

    public void setExportLC(RichInputText exportLC) {
        this.exportLC = exportLC;
//        RichInputText bpo = this.getBpo();
//        this.exportLC.setValue(bpo.getValue());
    }

    public RichInputText getExportLC() {
        return exportLC;
    }

    public void setStyleNo(RichInputText styleNo) {
        this.styleNo = styleNo;
    }

    public RichInputText getStyleNo() {
        return styleNo;
    }

    public void setBpo(RichInputText bpo) {
        this.bpo = bpo;
    }

    public RichInputText getBpo() {
        return bpo;
    }

    public void saveActionButton(ActionEvent actionEvent) {
        // Add event code here...
        //  system.out.println("=-=-=-=-=-=-=-=-=-=-= in saveActionButton");
        OperationBinding operationBinding = getOperationBinding("saveActionButton");
        operationBinding.execute();
        
        
      //  AdfFacesContext adfFaceContext = AdfFacesContext.getCurrentInstance();
    //    adfFaceContext.addPartialTarget(totalCartonQty);
        
        
        getOperationBinding("Commit").execute(); 
        

        
    }
    
    public OperationBinding getOperationBinding(String methodName){
       //  system.out.println("Operation Binding");
        BindingContext bindingContext = BindingContext.getCurrent();
        BindingContainer bindingContainer = bindingContext.getCurrentBindingsEntry();
        OperationBinding operationBinding = bindingContainer.getOperationBinding(methodName);
       //  system.out.println("Return operation binding...");
        return  operationBinding;
    }

    public void setPanelFormLayoutBean(RichPanelFormLayout panelFormLayoutBean) {
        this.panelFormLayoutBean = panelFormLayoutBean;
    }

    public RichPanelFormLayout getPanelFormLayoutBean() {
        return panelFormLayoutBean;
    }


    public void exportLC_LaunchPopupListener(LaunchPopupEvent launchPopupEvent) {
        // Add event code here...
       //  system.out.println("Launch Popup Listener ");
    }

    public void exportLC_ReturnPopupListener(ReturnPopupEvent returnPopupEvent) {
        // Add event code here...
       //  system.out.println("Return Popup Event");
    }

    public void exportLC_AttributeChangeListener(AttributeChangeEvent attributeChangeEvent) {
        // Add event code here...
       //  system.out.println("Attribute Change Listener");
            
    }

    public void setExpDate(RichInputDate expDate) {
        this.expDate = expDate;
    }

    public RichInputDate getExpDate() {
        return expDate;
    }

    public void testDate(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding operationBinding = getOperationBinding("testDate");
        operationBinding.execute();
       // java.sql.Date date = 2016-12-02;
    }

    public String saveActionBtn() {
        // Add event code here...
       //  system.out.println("=-=-=-=-=-=-=-=- in saveActionBtn() ");
        OperationBinding operationBinding = getOperationBinding("headerExecute");
        operationBinding.execute();
        getOperationBinding("Commit").execute();

        AdfFacesContext.getCurrentInstance().addPartialTarget(exp_no);
        AdfFacesContext.getCurrentInstance().addPartialTarget(expDate);
        return null;
        
        
    }

    public void searchTable_selectionListener(SelectionEvent selectionEvent) {
        // Add event code here...
        makeCurrent(selectionEvent);
    }
    
    public static void makeCurrent( 
        
        org.apache.myfaces.trinidad.event.SelectionEvent selectionEvent){ 
        try{
            RichTable _table = (RichTable) selectionEvent.getSource(); 
            //the Collection Model is the object that provides the 
            //structured data for the table to render 
            org.apache.myfaces.trinidad.model.CollectionModel _tableModel = (org.apache.myfaces.trinidad.model.CollectionModel) _table.getValue(); 
            //the ADF object that implements the CollectionModel is  JUCtrlHierBinding. It is wrapped by the CollectionModel API 
            oracle.jbo.uicli.binding.JUCtrlHierBinding _adfTableBinding = (oracle.jbo.uicli.binding.JUCtrlHierBinding) _tableModel.getWrappedData(); 
            //Acess the ADF iterator binding that is used with ADF table binding 
            oracle.adf.model.binding.DCIteratorBinding  _tableIteratorBinding = _adfTableBinding.getDCIteratorBinding(); 
            //the role of this method is to synchronize the table component selection with the selection in the ADF model 
            Object _selectedRowData = _table.getSelectedRowData(); 
             //cast to JUCtrlHierNodeBinding, which is the ADF object that represents a row
             oracle.jbo.uicli.binding.JUCtrlHierNodeBinding _nodeBinding = (oracle.jbo.uicli.binding.JUCtrlHierNodeBinding) _selectedRowData; 
             oracle.jbo.Key _rwKey = _nodeBinding.getRowKey(); 
             _tableIteratorBinding.setCurrentRowWithKey(_rwKey.toStringFormat(true)); 
        }catch(Exception e){
           //  system.out.println("Make Current ....");    
        }
    }

    public void copyDocument(ActionEvent actionEvent) {
        // Add event code here...
        oracle.adf.model.BindingContext bindingContext = oracle.adf.model.BindingContext.getCurrent();
        oracle.binding.BindingContainer bindingContainer = bindingContext.getCurrentBindingsEntry();
        OperationBinding binding = bindingContainer.getOperationBinding("copyDocument"); 
        binding.execute();
        AdfFacesContext adfFaceContext = AdfFacesContext.getCurrentInstance();
        adfFaceContext.addPartialTarget(searchPageTable);
    }

    public void setSearchPageTable(RichTable searchPageTable) {
        this.searchPageTable = searchPageTable;
    }

    public RichTable getSearchPageTable() {
        return searchPageTable;
    }

    public String amendmentActionListener() {
        // Add event code here...
        oracle.adf.model.BindingContext bindingContext = oracle.adf.model.BindingContext.getCurrent();
        oracle.binding.BindingContainer bindingContainer = bindingContext.getCurrentBindingsEntry();
        OperationBinding binding = bindingContainer.getOperationBinding("amendmentAction"); 
        binding.execute();
        AdfFacesContext adfFaceContext = AdfFacesContext.getCurrentInstance();
        adfFaceContext.addPartialTarget(searchPageTable);
        return null;
    }

    public void setDcpo(RichInputText dcpo) {
        this.dcpo = dcpo;
    }

    public RichInputText getDcpo() {
        return dcpo;
    }

    private void fillStyle() {
        
        ViewObject headerVo =  appM.getCiHeaders1();
        Row headerCurrentRow = headerVo.getCurrentRow();
        String invoiceNo=null  ;
        String udNo=null;
        String ciLine1Color = null;
        try {
            invoiceNo= headerCurrentRow.getAttribute("InvoiceNo").toString();
            udNo= headerCurrentRow.getAttribute("Attribute1").toString();
         
            
        }catch(Exception e){
           return ;
        }
        ViewObject styleVo = appM.getFillStyleVO1();
        styleVo.setNamedWhereClauseParam("INVOICE_NO",invoiceNo);
        styleVo.setNamedWhereClauseParam("P_UD_NUM",  udNo); 
        styleVo.executeQuery();
        
        
        RowSetIterator iter = styleVo.createRowSetIterator("aa");
        
        while( iter.hasNext()){
            Row styleRow =  iter.next();
            Row ciLine2Row = createLine2();
                        //System.out.println("has next 1");
            
                        ciLine2Row.setAttribute("Style",
                                             getPopulateValue(styleRow, "Style"));
                        ciLine2Row.setAttribute("Color",
                                    getPopulateValue(styleRow, "Color"));
            
                       //  system.out.println("style"+ getPopulateValue(styleRow, "Style") );
                       ciLine2Row.setAttribute("Quantity",
                                             getPopulateValue(styleRow, "InvoiceAmount"));
                       //  system.out.println("Invoice Amount "+ getPopulateValue(styleRow, "InvoiceAmount") );
                        ciLine2Row.setAttribute("UsedFabric",
                                             getPopulateValue(styleRow, "Consumption"));
                        ciLine2Row.setAttribute("Attribute4",
                                             getPopulateValue(styleRow, "Description"));
                 
                        ciLine2Row.setAttribute("Consumption",
                                             getPopulateValue(styleRow, "UdConsumption"));
            
        }
        
        iter.closeRowSetIterator();
        
        AdfFacesContext.getCurrentInstance().addPartialTarget(ciLine2Table);    
        
      
        
    }

    private Row createLine2() {

        ViewObject vo = appM.getCiLines2VO1();
        Row row = vo.createRow();
        vo.insertRow(row);
        row.setNewRowState(Row.STATUS_INITIALIZED);
       //  system.out.println("Out Create Item" );
        return row;
    }

    private Object getPopulateValue(Row styleRow, String string) {

        String value = null;
        try {
            value = styleRow.getAttribute(string).toString();
        } catch (Exception e) {
            ;
        }
        return value;
    }

    public void setCiLine2Table(RichTable ciLine2Table) {
        this.ciLine2Table = ciLine2Table;
    }

    public RichTable getCiLine2Table() {
        return ciLine2Table;
    }

    public void fillStyleAgainstInvoiceAndUdNumber(ActionEvent actionEvent) {
        
        
        /**added by fatin on 14 may 2017
         * filling style againt ud number and invoice no  */
        fillStyle();
        getFillStyleButton().setDisabled(true);
       
    }

    public void generateInvoiceListener(ActionEvent actionEvent) {
       ViewObject  headerVo  = appM.getCiHeaders1();
        Row currentHeaderRow = headerVo.getCurrentRow();
         String trxNo = null ;

         try {
             
           trxNo = currentHeaderRow.getAttribute("TrxNumber").toString();
             
         }
         catch(Exception e ){
             
             trxNo = null ;
     
         }
         
       //invoice not generated 
         if (trxNo==null){
            
               generateInvoice();
            
         }
         
        //invoice  generated   
         else {
             
                 FacesMessage Message = new FacesMessage("Invoice Already Generated ");   
                Message.setSeverity(FacesMessage.SEVERITY_INFO);   
                FacesContext fc = FacesContext.getCurrentInstance();   
                  fc.addMessage(null, Message);  
          }
         
         
         
        refreshQueryKeepingCurrentRow(headerVo ) ;
           
        AdfFacesContext adfFaceContext = AdfFacesContext.getCurrentInstance();
        adfFaceContext.addPartialTarget(trxNumber);
        
    }

/*
 *  Run Procedure to update Invoice number in Inland form matched with Pack List , Style , Order BPO
 */
    public void updateInlandInvoiceListener(ActionEvent actionEvent) {
        ViewObject  headerVo  = appM.getCiHeaders1();
        Row currentHeaderRow = headerVo.getCurrentRow();
        String InvoiceNoVal = null ;
        
        try {            
          InvoiceNoVal = currentHeaderRow.getAttribute("InvoiceNo").toString();   
        System.out.println("[InvoiceNoVal  ] " + InvoiceNoVal);
        }
        catch(Exception e ){            
            InvoiceNoVal = null ;        
        }
        //invoice not generated 
        if (InvoiceNoVal==null)
        {                                 
        }          
        //invoice  generated   
        else {            
            updateInlandInvoiceProcedure();            
        }          
        refreshQueryKeepingCurrentRow(headerVo ) ;
    }
    public void setExp_no(RichInputText exp_no) {
        this.exp_no = exp_no;
    }

    public RichInputText getExp_no() {
        return exp_no;
    }

    public void deleteAllFromFabricConsTable(ActionEvent actionEvent) {
        // Add event code here...
    }

    public void deleteAllFromFabricConsumpTable(ActionEvent actionEvent) {
      
        ViewObject  ciLine2Vo = appM.getCiLines2VO1();
        Row[] rowSet =  ciLine2Vo.getAllRowsInRange();
        for(Row row :rowSet){
           row.remove();
        }
        
        
     
        getFillStyleButton().setDisabled(false);
    }

    public void setFillStyleButton(RichCommandButton fillStyleButton) {
        this.fillStyleButton = fillStyleButton;
    }

    public RichCommandButton getFillStyleButton() {
        return fillStyleButton;
    }
    
    private void generateInvoice() {
        System.out.println("a");
        ViewObject hvo = appM.getCiHeaders1();
        
        String header_ID = null;
        String org_id = null;
        
        
        Row currentRow= null;
        try{
             currentRow = hvo.getCurrentRow();
        }catch(Exception e){
            System.out.println("a");
        }
        try{
            header_ID = currentRow.getAttribute("ExpCiHeaderId").toString();
        }catch(Exception e){
            System.out.println("b");
            ;
        }
        try{
            org_id = currentRow.getAttribute("OrgId").toString();
        }catch(Exception e){
            System.out.println("c");
            ;
        }
        
        
        String stmt = "BEGIN  MNJ_RA_AUTO_INVOICES(:1,:2); end;";
       
        try {
            CallableStatement cs = appM.getDBTransaction().createCallableStatement(stmt, 1);
          //  System.out.println("Set Parameter");
            cs.setInt(1, Integer.parseInt(header_ID));
            cs.setInt(2, Integer.parseInt(org_id));
          //  System.out.println("Execute ");
            cs.execute();
          //  System.out.println("Close");
            cs.close();
        } catch (Exception e) {
            System.out.println(e);
           
            
             showMessage(e.toString(), "error" );
            
            
            
        //    System.out.println("Exception e "+e);
           // System.out.println("Amendment Exception");
            ;
        }   
        
        
        
    }
    private void updateInlandInvoiceProcedure()
    {
        ViewObject hvo   = appM.getCiHeaders1();        
        String header_ID = null;           
        Row currentRow   = null;
        try{
             currentRow = hvo.getCurrentRow();
        }catch(Exception e){
            System.out.println("[ERROR updateInlandInvoiceProcedure #1 ] ");
        }
        try{
            header_ID = currentRow.getAttribute("ExpCiHeaderId").toString();
        }catch(Exception e){
            System.out.println("[ERROR updateInlandInvoiceProcedure #2 ] ");
            ;
        }
       
        String stmt = "BEGIN  EXP_INLAND_FOR_INV_UPDATE(:1, :2); end;";
        String procedureResult = null;
        try {
            CallableStatement cs = appM.getDBTransaction().createCallableStatement(stmt, 1);
            cs.registerOutParameter(2, oracle.jdbc.OracleTypes.VARCHAR);
            cs.setInt(1, Integer.parseInt(header_ID) );                      
            cs.execute();
            procedureResult = cs.getString(2 );
            cs.close();
            System.out.println("[procedureResult--->] " + procedureResult );
        } catch (Exception e) {
            System.out.println(e);                       
            showMessage(e.toString(), "error" );
            ;
        }           
    }

    public void bpoUnitPriceChangeListener(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
      
        ViewObject ciHeaderVo = appM.getCiHeaders1();
        Row currentHeaderRow = ciHeaderVo.getCurrentRow();
        double commisionPer = 0;
         
        
      
        
        
        try{
            commisionPer=  Double.parseDouble(currentHeaderRow.getAttribute("CommissionPercentage").toString());
            
        }catch(Exception e){
            
            FacesMessage Message = new FacesMessage("Set  Commission Percent first .");   
                  Message.setSeverity(FacesMessage.SEVERITY_INFO);   
                  FacesContext fc = FacesContext.getCurrentInstance();   
                  fc.addMessage(null, Message);  
            
            return;
        }
       
        
        ViewObject  ciLineVo = appM.getCiLines1();
        Row currentCiLineRow = ciLineVo.getCurrentRow();
        
        double  unitPrice= Double.parseDouble(valueChangeEvent.getNewValue().toString());
        
        double  shipQty =Double.parseDouble(currentCiLineRow.getAttribute("ShipQty").toString());
        
        
        double goodsValue ;
        double tradeDiscountCommission;
         double invoiceValue  ;
         
          goodsValue = unitPrice*shipQty ;
          
          tradeDiscountCommission = (goodsValue*commisionPer)/100   ;   
           
          invoiceValue=  goodsValue - tradeDiscountCommission;
          
          invoiceValue =     Math.round(invoiceValue*100.0)/100.0;    
         
         currentCiLineRow.setAttribute("GoodsValue", goodsValue);
        currentCiLineRow.setAttribute("TradeDiscountCommission", tradeDiscountCommission);
        currentCiLineRow.setAttribute("InvoiceValue", invoiceValue);
        
         System.out.println("====================  "+invoiceValue);
     
        
    }
    
    
    public void refreshQueryKeepingCurrentRow(ViewObject viewObject )  {
       Row currentRow = viewObject.getCurrentRow();
       Key currentRowKey = currentRow.getKey();
       int rangePosOfCurrentRow = viewObject.getRangeIndexOf(currentRow);
       int rangeStartBeforeQuery = viewObject.getRangeStart();
       viewObject.executeQuery();
       viewObject.setRangeStart(rangeStartBeforeQuery);
       /*
        * In 10.1.2, there is this convenience method we could use
        * instead of the remaining lines of code
        *
        *  findAndSetCurrentRowByKey(currentRowKey,rangePosOfCurrentRow);
        *  
        */
       
         
       Row[] rows = viewObject.findByKey(currentRowKey, 1);
       if (rows != null && rows.length == 1)
       {
          viewObject.scrollRangeTo(rows[0], rangePosOfCurrentRow);
          viewObject.setCurrentRowAtRangeIndex(viewObject.getRangeIndexOf(rows[0]));
       }
       
       
     }


    public void setTrxNumber(RichPanelLabelAndMessage trxNumber) {
        this.trxNumber = trxNumber;
    }

    public RichPanelLabelAndMessage getTrxNumber() {
        return trxNumber;
    }

    public void setTotalCartonQty(RichInputText totalCartonQty) {
        this.totalCartonQty = totalCartonQty;
    }

    public RichInputText getTotalCartonQty() {
        return totalCartonQty;
    }

  
    public  void showMessage(String messege , String severity ) {
        
        
        FacesMessage fm = new FacesMessage(messege);
        
        if(severity.equals("info")){
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
        }
        else if(severity.equals("warn")){
            fm.setSeverity(FacesMessage.SEVERITY_WARN);
        }
        else if(severity.equals("error")){
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        }
        
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, fm);
        
    }


    public void setPackListNo(RichInputText packListNo) {
        this.packListNo = packListNo;
    }

    public RichInputText getPackListNo() {
        return packListNo;
    }

   


    public void searchDocNoForPackList(ActionEvent actionEvent) {
        // Add event code here...
        
         
        String query= "SELECT DISTINCT H.DOC_NO  FROM  IEDOC_EXP_CI_HEADERS H , IEDOC_EXP_CI_LINES L \n" + 
        "WHERE H.EXP_CI_HEADER_ID = L.EXP_CI_HEADER_ID \n" + 
        "AND L.PACK_LIST_NO =?";
        
         ResultSet resultSet=null;            
         
         try{
             PreparedStatement createStatement= appM.getDBTransaction().createPreparedStatement(query, 1);

             createStatement.setString(1, getPackListNo().getValue().toString());
             
             resultSet = createStatement.executeQuery();
             StringBuilder docNo=null;
             docNo = new StringBuilder("");
            
             while(resultSet.next() ){
                
                
                docNo.append(resultSet.getString("DOC_NO") );
                 docNo.append(" , " );
             } 
             if(docNo.lastIndexOf(",")>=0){
                 docNo.deleteCharAt(docNo.lastIndexOf(","));
             }
             
             
             commercilInvDocNo.setValue(docNo.toString());
             resultSet.close();
             createStatement.close();
             
             
         }catch(Exception e){
           e.printStackTrace();
           
         }
        
        AdfFacesContext.getCurrentInstance().addPartialTarget( searchCIDocNoBlock); 
        
    }

    public void setSearchCIDocNoBlock(RichPanelFormLayout searchCIDocNoBlock) {
        this.searchCIDocNoBlock = searchCIDocNoBlock;
    }

    public RichPanelFormLayout getSearchCIDocNoBlock() {
        return searchCIDocNoBlock;
    }

    public void setCommercilInvDocNo(RichOutputText commercilInvDocNo) {
        this.commercilInvDocNo = commercilInvDocNo;
    }

    public RichOutputText getCommercilInvDocNo() {
        return commercilInvDocNo;
    }
}
