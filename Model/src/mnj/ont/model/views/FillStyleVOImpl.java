package mnj.ont.model.views;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun May 14 16:32:52 BDT 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class FillStyleVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public FillStyleVOImpl() {
    }


    /**
     * Returns the bind variable value for INVOICE_NO.
     * @return bind variable value for INVOICE_NO
     */
    public String getINVOICE_NO() {
        return (String)getNamedWhereClauseParam("INVOICE_NO");
    }

    /**
     * Sets <code>value</code> for bind variable INVOICE_NO.
     * @param value value to bind as INVOICE_NO
     */
    public void setINVOICE_NO(String value) {
        setNamedWhereClauseParam("INVOICE_NO", value);
    }

    /**
     * Returns the bind variable value for P_UD_NUM.
     * @return bind variable value for P_UD_NUM
     */
    public String getP_UD_NUM() {
        return (String)getNamedWhereClauseParam("P_UD_NUM");
    }

    /**
     * Sets <code>value</code> for bind variable P_UD_NUM.
     * @param value value to bind as P_UD_NUM
     */
    public void setP_UD_NUM(String value) {
        setNamedWhereClauseParam("P_UD_NUM", value);
    }
}
