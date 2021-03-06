package mnj.ont.model.lov.views;

import java.sql.ResultSet;

import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.server.ViewRowImpl;
import oracle.jbo.server.ViewRowSetImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Jan 04 14:16:29 BDT 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CustomerVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CustomerVOImpl() {
    }

    /**
     * executeQueryForCollection - overridden for custom java data source support.
     */
    protected void executeQueryForCollection(Object qc, Object[] params,
                                             int noUserParams) {
        super.executeQueryForCollection(qc, params, noUserParams);
    }

    /**
     * hasNextForCollection - overridden for custom java data source support.
     */
    protected boolean hasNextForCollection(Object qc) {
        boolean bRet = super.hasNextForCollection(qc);
        return bRet;
    }

    /**
     * createRowFromResultSet - overridden for custom java data source support.
     */
    protected ViewRowImpl createRowFromResultSet(Object qc,
                                                 ResultSet resultSet) {
        ViewRowImpl value = super.createRowFromResultSet(qc, resultSet);
        return value;
    }

    /**
     * getQueryHitCount - overridden for custom java data source support.
     */
    public long getQueryHitCount(ViewRowSetImpl viewRowSet) {
        long value = super.getQueryHitCount(viewRowSet);
        return value;
    }

    /**
     * Returns the variable value for p_buyer_name.
     * @return variable value for p_buyer_name
     */
    public String getp_buyer_name() {
        return (String)ensureVariableManager().getVariableValue("p_buyer_name");
    }

    /**
     * Sets <code>value</code> for variable p_buyer_name.
     * @param value value to bind as p_buyer_name
     */
    public void setp_buyer_name(String value) {
        ensureVariableManager().setVariableValue("p_buyer_name", value);
    }
}
