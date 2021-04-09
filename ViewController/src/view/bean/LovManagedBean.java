package view.bean;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

import oracle.adf.view.rich.component.rich.input.RichInputListOfValues;

public class LovManagedBean {
    private RichInputListOfValues buyerName;

    public LovManagedBean() {
        super();
    }

    public void setBuyerName(RichInputListOfValues buyerName) {
        this.buyerName = buyerName;
        FacesContext facesContext  = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpSession httpSession = (HttpSession)externalContext.getSession(false);
        httpSession.setAttribute("sessionBuyerValue", buyerName.getValue());

    }

    public RichInputListOfValues getBuyerName() {
        return buyerName;
    }
}
