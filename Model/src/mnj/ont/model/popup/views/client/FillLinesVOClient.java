package mnj.ont.model.popup.views.client;

import mnj.ont.model.popup.views.common.FillLinesVO;

import oracle.jbo.client.remote.ViewUsageImpl;
import oracle.jbo.domain.Date;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Feb 06 12:01:47 BDT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FillLinesVOClient extends ViewUsageImpl implements FillLinesVO {
    /**
     * This is the default constructor (do not remove).
     */
    public FillLinesVOClient() {
    }

    public void setendDate(Date value) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"setendDate",new String [] {"oracle.jbo.domain.Date"},new Object[] {value});
        return;
    }

    public void setstartDate(Date value) {
        Object _ret =
            getApplicationModuleProxy().riInvokeExportedMethod(this,"setstartDate",new String [] {"oracle.jbo.domain.Date"},new Object[] {value});
        return;
    }
}