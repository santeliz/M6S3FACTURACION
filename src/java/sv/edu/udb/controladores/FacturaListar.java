/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sv.edu.udb.entidades.Factura;
import sv.edu.udb.modelo.FacturaFacade;

@ManagedBean
@SessionScoped
public class FacturaListar implements Serializable {

  
    @EJB
    FacturaFacade facturaFacade;

    private List<Factura> facturaLst;
    private Factura facturaSelected;
        
    public FacturaListar() {
        facturaLst = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        listarFacturas();
    }    
    
    public String borrarFactura() {
        try {
        facturaFacade.remove(facturaSelected);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Factura Borrada", "Se borró la factura correspondiente."));
        } catch (Exception e) {
            System.out.println("ERROR al borrar factura " + e);
        }
        
        return "facturaLista.xhtml?faces-redirect=true";
    }
    
    public List<Factura> listarFacturas(){
        facturaLst = facturaFacade.findAll();
        return facturaLst;
    }

    public List<Factura> getFacturaLst() {
        return facturaLst;
    }

    public void setFacturaLst(List<Factura> facturaLst) {
        this.facturaLst = facturaLst;
    }

    public Factura getFacturaSelected() {
        return facturaSelected;
    }

    public void setFacturaSelected(Factura facturaSelected) {
        this.facturaSelected = facturaSelected;
    }
    
    
    
}
