package mnj.ont.managed;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import mnj.ont.model.services.MainAMImpl;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DialogEvent.Outcome;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.event.PopupFetchEvent;
import oracle.adf.view.rich.event.QueryEvent;
import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;
import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.ViewObject;

public class PopupManagedBean {
    private RichTable fillLinesBean;
    private RichTable linesTableBean;
    private RichTable fillDataPopupTable;
    private RichInputText headerId;
    private RichInputText dcpo;
    private RichInputListOfValues consignee;
    private RichInputText consigneeAddress;
    private RichInputListOfValues notifyTo;
    private RichInputText notifyAddress;
    private RichInputListOfValues shipTo;
    private RichInputText shipToAddress;
    private RichInputListOfValues country;
    
    MainAMImpl appM = getAppModuleImpl();
    private RichPanelFormLayout headerForm;

    public PopupManagedBean() {
        super();
    }


//    public void selectAllButttonAction(DialogEvent dialogEvent) {
//        // Add event code here...
//        
// 
// 
// 
//    }

    public void deselectAllButtonAction(ActionEvent actionEvent) {
        // Add event code here...
        BindingContext bindingContext = BindingContext.getCurrent();
        BindingContainer bindingContainer = bindingContext.getCurrentBindingsEntry();
        OperationBinding operationBinding = bindingContainer.getOperationBinding("popupDeselectActionListener");
        //  system.out.println("OK....");
        operationBinding.execute();
        /*
         AdfFacesContext Object contain the all the facet and Components on the current page. 
        */
        AdfFacesContext adfFaces = AdfFacesContext.getCurrentInstance();
        /*
         addPartialTarget("") Method of AdfFacesContext object Refresh the component. 
         Accepts String in parameter (Name of the bean of component).      
        */
        adfFaces.addPartialTarget(fillDataPopupTable);
    }

    public void selectAllButtonAction(ActionEvent actionEvent) {
        // Add event code here...
        //  system.out.println(" Action Source " + actionEvent.getSource());
        BindingContext bindingContext = BindingContext.getCurrent();
        BindingContainer bindingContainer =  bindingContext.getCurrentBindingsEntry();
        //  system.out.println("OK");
        OperationBinding ob = bindingContainer.getOperationBinding("popupSelectActionListener");
        ob.execute();
        AdfFacesContext adfFaceContext = AdfFacesContext.getCurrentInstance();
        adfFaceContext.addPartialTarget(fillDataPopupTable);
    }

    public void setFillLinesBean(RichTable fillLinesBean) {
        this.fillLinesBean = fillLinesBean;
    }

    public RichTable getFillLinesBean() {
        return fillLinesBean;
    }

    public void FillLinesPopupFetchListener(PopupFetchEvent popupFetchEvent) {
        // Add event code here...
        //  system.out.println("FillLinesPopupFetchListener");
        //  system.out.println("Action");
        BindingContext bindingContext = BindingContext.getCurrent();
        BindingContainer bindingContainer = bindingContext.getCurrentBindingsEntry();
        OperationBinding operationBinding = bindingContainer.getOperationBinding("popupDeselectActionListener");
        operationBinding.execute();
        AdfFacesContext adfFaces = AdfFacesContext.getCurrentInstance();
        adfFaces.addPartialTarget(fillDataPopupTable);
    }

    public void popupFillLinesDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
      //  //  system.out.println("Dialog Event");
        Outcome outcome = dialogEvent.getOutcome();
        //  system.out.println(outcome.name());
        if(outcome.name().equals("ok")){
            //  system.out.println("OK Button");
            OperationBinding operattionBinding = getOperationBinding("fetchLines");
            operattionBinding.execute();
           // BindingContext bindingContext = BindingContext.getCurrent();
            //BindingContainer bindingContainer = bindingContext.getCurrentBindingsEntry();
            operattionBinding = getOperationBinding("popupDeselectActionListener");
            operattionBinding.execute();
            AdfFacesContext adfFaces = AdfFacesContext.getCurrentInstance();
            adfFaces.addPartialTarget(fillDataPopupTable);
            //AdfFacesContext adfFaces = AdfFacesContext.getCurrentInstance();
            adfFaces.addPartialTarget(dcpo);
          //  adfFaces.addPartialTarget(consignee);
            adfFaces.addPartialTarget(consigneeAddress);
          //  adfFaces.addPartialTarget(notifyTo);
            adfFaces.addPartialTarget(notifyAddress);
         //   adfFaces.addPartialTarget(shipTo);
            adfFaces.addPartialTarget(shipToAddress);
            adfFaces.addPartialTarget(country);
        }else {//if(outcome.name().equals("cancel")){
            //  system.out.println("Cancel");
            OperationBinding operattionBinding = getOperationBinding("popupDeselectActionListener");
            operattionBinding.execute();
            AdfFacesContext adfFaces = AdfFacesContext.getCurrentInstance();
            adfFaces.addPartialTarget(fillDataPopupTable);
        }
        AdfFacesContext adfFaceContext = AdfFacesContext.getCurrentInstance();
        adfFaceContext.addPartialTarget(linesTableBean);
        
     //   updateConsigneeNotifyShipToNameAndAddress();
       
          
          }


    private void updateConsigneeNotifyShipToNameAndAddress() {
        
        
        
        ViewObject headerVO  = appM.getCiHeaders1();
        Row currentHeaderRow = headerVO.getCurrentRow();
        
        String currentBuyer = currentHeaderRow.getAttribute("Buyer").toString();
        
        RowSet clLinesRows = (RowSet)currentHeaderRow.getAttribute("CiLines");
        String currentCountry =""; 
        String currentDcpo =null;
        if(clLinesRows.getAllRowsInRange().length>0){
              Row firstLineRow =   clLinesRows.first();  
              try{
                currentCountry  = firstLineRow.getAttribute("Country").toString();
              }catch(Exception e){
                  currentCountry="";
              }
              
            try{
              currentDcpo  = firstLineRow.getAttribute("Dcpo").toString();
            }catch(Exception e){
                currentDcpo=null;
            }
            
           currentHeaderRow =  setCosigneeNotifyToShipToAddress(currentBuyer,currentCountry,currentDcpo ,currentHeaderRow);
              AdfFacesContext adfFaces = AdfFacesContext.getCurrentInstance();
              adfFaces.addPartialTarget(consigneeAddress);
              adfFaces.addPartialTarget(shipToAddress);
              adfFaces.addPartialTarget(notifyAddress);
              
           currentHeaderRow =  setCosigneeNotifyToShipToName(currentBuyer,currentCountry,currentDcpo ,currentHeaderRow);
              
              adfFaces.addPartialTarget(headerForm);
    }
}
    
    
    public OperationBinding getOperationBinding(String methodName){
        //  system.out.println("Operation Binding");
        BindingContext bindingContext = BindingContext.getCurrent();
        BindingContainer bindingContainer = bindingContext.getCurrentBindingsEntry();
        OperationBinding operationBinding = bindingContainer.getOperationBinding(methodName);
        return  operationBinding;
    }

    public void setLinesTableBean(RichTable linesTableBean) {
        this.linesTableBean = linesTableBean;
    }

    public RichTable getLinesTableBean() {
        return linesTableBean;
    }


    public void setFillDataPopupTable(RichTable fillDataPopupTable) {
        this.fillDataPopupTable = fillDataPopupTable;
    }

    public RichTable getFillDataPopupTable() {
        return fillDataPopupTable;
        
    }

    public void fillLinesPopupCancelledListener(PopupCanceledEvent popupCanceledEvent) {
        // Add event code here...
    }
    
    
    public void customQuery(QueryEvent queryEvent) {
        // Add event code here...
        BindingContext bctx = BindingContext.getCurrent();
        DCBindingContainer bindings = (DCBindingContainer) bctx.getCurrentBindingsEntry();
     
        //access the method bindings to set the bind variables on the ViewCriteria
        OperationBinding rangeStartOperationBinding = bindings.getOperationBinding("setstartDate");
        OperationBinding rangeEndOperationBinding = bindings.getOperationBinding("setendDate");
     
        // get the start date and end date from the temporary valiables
        AttributeBinding attr = (AttributeBinding) bindings.getControlBinding("startDate1");
        oracle.jbo.domain.Date sd = (oracle.jbo.domain.Date) attr.getInputValue();
        //  system.out.println(sd);
        attr = (AttributeBinding) bindings.getControlBinding("endDate1");
        oracle.jbo.domain.Date ed = (oracle.jbo.domain.Date) attr.getInputValue();
        //  system.out.println(ed);
     
        //set the start and end date of the range to search
        if(sd !=null && ed !=null){
            rangeStartOperationBinding.getParamsMap().put("value", sd);
            rangeEndOperationBinding.getParamsMap().put("value", ed);
           
        }else{

            invokeQueryEventMethodExpression("#{bindings.FillLinesVO12Query.processQuery}",queryEvent);
        }
        //set bind variable on the business service
        rangeStartOperationBinding.execute();
        rangeEndOperationBinding.execute();
        invokeMethodExpression("#{bindings.FillLinesVO12Query.processQuery}", Object.class, QueryEvent.class, queryEvent);

    }
    public Object invokeMethodExpression(String expr, Class returnType, Class[] argTypes, Object[] args) {

            FacesContext fc = FacesContext.getCurrentInstance();
            ELContext elctx = fc.getELContext();
            ExpressionFactory elFactory = fc.getApplication().getExpressionFactory();
            MethodExpression methodExpr = elFactory.createMethodExpression(elctx, expr, returnType, argTypes);
            return methodExpr.invoke(elctx, args);
        }

        public Object invokeMethodExpression(String expr, Class returnType, Class argType, Object argument) {
            return invokeMethodExpression(expr, returnType, new Class[] { argType }, new Object[] { argument });
        }
    private void invokeQueryEventMethodExpression(
        String expression, QueryEvent queryEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ELContext elctx = fctx.getELContext();
        ExpressionFactory efactory =fctx.getApplication().getExpressionFactory();
        MethodExpression me =efactory.createMethodExpression(elctx, expression, Object.class, new Class[] { QueryEvent.class });
        me.invoke(elctx, new Object[] { queryEvent });

    }

    public void setHeaderId(RichInputText headerId) {
        this.headerId = headerId;
        FacesContext facesContext  = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession httpSession = (HttpSession)externalContext.getSession(false);
        httpSession.setAttribute("headerId", headerId.getValue());
    }

    public RichInputText getHeaderId() {
        return headerId;
    }

    public void setDcpo(RichInputText dcpo) {
        this.dcpo = dcpo;
    }

    public RichInputText getDcpo() {
        return dcpo;
    }

    public void setConsignee(RichInputListOfValues consignee) {
        this.consignee = consignee;
    }

    public RichInputListOfValues getConsignee() {
        return consignee;
    }

    public void setConsigneeAddress(RichInputText consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public RichInputText getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setNotifyTo(RichInputListOfValues notifyTo) {
        this.notifyTo = notifyTo;
    }

    public RichInputListOfValues getNotifyTo() {
        return notifyTo;
    }

    public void setNotifyAddress(RichInputText notifyAddress) {
        this.notifyAddress = notifyAddress;
    }

    public RichInputText getNotifyAddress() {
        return notifyAddress;
    }

    public void setShipTo(RichInputListOfValues shipTo) {
        this.shipTo = shipTo;
    }

    public RichInputListOfValues getShipTo() {
        return shipTo;
    }

    public void setShipToAddress(RichInputText shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public RichInputText getShipToAddress() {
        return shipToAddress;
    }

    public void setCountry(RichInputListOfValues country) {
        this.country = country;
    }

    public RichInputListOfValues getCountry() {
        return country;
    }
    
    
    public MainAMImpl getAppModuleImpl() {
        DCBindingContainer bindingContainer =
            (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        //BindingContext bindingContext = BindingContext.getCurrent();
        DCDataControl dc =
            bindingContainer.findDataControl("MainAMDataControl"); // Name of application module in datacontrolBinding.cpx
        MainAMImpl appM = (MainAMImpl)dc.getDataProvider();
        return appM;
    }

    private Row setCosigneeNotifyToShipToAddress(String currentBuyer,String currentCountry ,String currentDcpo ,Row currentHeaderRow ) {
        
        ViewObject consigneeAddressVO = appM.getConsigneeAddressVO1();
        ViewObject notifyAddressVO = appM.getNotifyAddressVO1();
        ViewObject shipAddressVO = appM.getShipAddressVO1();
        
        if(currentBuyer.equals("The Gap, Inc.")){
            
           consigneeAddressVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "' AND DCPO = '"+currentDcpo+"'");
            notifyAddressVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "' AND DCPO = '"+currentDcpo+"'"); 
            shipAddressVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "' AND DCPO = '"+currentDcpo+"'");
        }
        else{
            consigneeAddressVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "'");
             notifyAddressVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "'"); 
             shipAddressVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "'");
        }
        
        //  system.out.println(" filtering consignee_notify_ship_vo  "+"BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "' AND DCPO = '"+currentDcpo+"'" );
        
      //  consigneeAddressVO.setRangeSize(20);
        consigneeAddressVO.executeQuery(); 
        
      //  notifyAddressVO.setRangeSize(20);
        notifyAddressVO.executeQuery(); 
        
        // shipAddressVO.setRangeSize(20);
        shipAddressVO.executeQuery();
        
        StringBuilder  consignees=null , notifys=null , ships=null ; 
        
        consignees =  new StringBuilder("");
         for (Row row :consigneeAddressVO.getAllRowsInRange() ){
           
             try{
                 consignees.append(row.getAttribute("ConsigneeAddress").toString());
             }
             catch(Exception e){
                 ;
             }
            consignees.append("    \n       "); 
            consignees.append(System.getProperty("line.separator")); 
        }
        currentHeaderRow.setAttribute("ConsigneeAddress", consignees.toString());
        
        
        notifys =  new StringBuilder("");
        for (Row row :notifyAddressVO.getAllRowsInRange() ){
           
           
            try{
                notifys.append(row.getAttribute("NotifyAddress").toString());
            }
            catch(Exception e){
                ;
            }
            notifys.append("      \n       ");
            notifys.append(System.getProperty("line.separator")); 
           
        }
         currentHeaderRow.setAttribute("NotifyAddress", notifys.toString());
         
        ships =  new StringBuilder("");
        for (Row row :shipAddressVO.getAllRowsInRange() ){
           
            
            try{
                ships.append(row.getAttribute("ShipAddress").toString());
            }
            catch(Exception e){
                ;
            }
           
           ships.append("         \n        ");
            ships.append(System.getProperty("line.separator")); 
           
        }
        currentHeaderRow.setAttribute("ShipToAddress", ships);
        
        
        return currentHeaderRow;
        
    }

    private Row setCosigneeNotifyToShipToName(String currentBuyer, String currentCountry, String currentDcpo,Row currentHeaderRow) {
      ViewObject consigneeNameVO = appM.getConsigneeNameVO1();
        ViewObject notifytoNameVO = appM.getNotifyToNameVO1();
        ViewObject shipToNameVO = appM.getShipToNameVO1();
        
        if(currentBuyer.equals("The Gap, Inc.")){
            
           consigneeNameVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "' AND DCPO = '"+currentDcpo+"'");
             notifytoNameVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "' AND DCPO = '"+currentDcpo+"'"); 
             shipToNameVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "' AND DCPO = '"+currentDcpo+"'");
        }
        else{
             consigneeNameVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "'");
             notifytoNameVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "'"); 
             shipToNameVO.setWhereClause("BUYER =  '"+currentBuyer + "' AND COUNTRY = '" +currentCountry + "'");
        }
        //  consigneeAddressVO.setRangeSize(20);
          consigneeNameVO.executeQuery(); 
         //  notifyAddressVO.setRangeSize(20);
          notifytoNameVO.executeQuery(); 
         // shipAddressVO.setRangeSize(20);
          shipToNameVO.executeQuery();
          
       
        
        StringBuilder  consigneeName=null , notifyToName=null , shipToName=null ; 
        
        consigneeName =  new StringBuilder("");
         for (Row row :consigneeNameVO.getAllRowsInRange() ){
           
             try{
                 consigneeName.append(row.getAttribute("ConsigneeNameCountry").toString());
             }
             catch(Exception e){
                 ;
             }
            consigneeName.append("    \n       "); 
           consigneeName.append(System.getProperty("line.separator")); 
        }
        currentHeaderRow.setAttribute("Consignee", consigneeName.toString());
        
        
        notifyToName =  new StringBuilder("");
        for (Row row : notifytoNameVO.getAllRowsInRange() ){
           
           
            try{
                notifyToName.append(row.getAttribute("NotifyToNameCountry").toString());
            }
            catch(Exception e){
                ;
            }
           notifyToName.append("      \n       ");
            notifyToName.append(System.getProperty("line.separator")); 
           
        }
         currentHeaderRow.setAttribute("NotifyTo", notifyToName.toString());
         
        shipToName =  new StringBuilder("");
        for (Row row :shipToNameVO.getAllRowsInRange() ){
           
            
            try{
                shipToName.append(row.getAttribute("ShipToNameCountry").toString());
            }
            catch(Exception e){
                ;
            }
           
           shipToName.append("         \n        ");
            shipToName.append(System.getProperty("line.separator")); 
           
        }
        currentHeaderRow.setAttribute("ShipTo", shipToName);
        
        
        
        return currentHeaderRow;
    }

    public void setHeaderForm(RichPanelFormLayout headerForm) {
        this.headerForm = headerForm;
    }

    public RichPanelFormLayout getHeaderForm() {
        return headerForm;
    }
}
