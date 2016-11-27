package sv.edu.udb.controladores;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sv.edu.udb.entidades.Cliente;
import sv.edu.udb.entidades.DetalleFactura;
import sv.edu.udb.entidades.Empleado;
import sv.edu.udb.entidades.Factura;
import sv.edu.udb.entidades.FormaPago;
import sv.edu.udb.entidades.Producto;
import sv.edu.udb.modelo.ClienteFacade;
import sv.edu.udb.modelo.EmpleadoFacade;
import sv.edu.udb.modelo.FacturaFacade;
import sv.edu.udb.modelo.FormaPagoFacade;
import sv.edu.udb.modelo.ProductoFacade;

@ManagedBean
@SessionScoped
public class FacturaControl implements Serializable {

    @EJB
    EmpleadoFacade empleadoFacade;

    @EJB
    ClienteFacade clienteFacade;

    @EJB
    FormaPagoFacade formaPagoFacade;

    @EJB
    ProductoFacade productoFacade;
    
    @EJB
    FacturaFacade facturaFacade;

    private Factura factura;
    private List<Empleado> lstEmpleado;
    private List<Cliente> lstCliente;
    private List<FormaPago> lstFormaPago;
    private List<Producto> lstProducto;
    private DetalleFactura detalleFactura;

    @PostConstruct
    public void init() {
        factura = new Factura();
        factura.setFecha(new Date());
        factura.setDetalleFacturaList(new ArrayList<DetalleFactura>());
        listarEmpleados();
        listarClientes();
        listarFormaPago();
        listarProductos();
        detalleFactura = new DetalleFactura();
    }

    public void calcularTotales() {
        if ((detalleFactura.getCantidad() != null) && (detalleFactura.getIdProducto().getPrecio() !=null)) {
            BigDecimal subtotal = detalleFactura.getIdProducto().getPrecio().multiply(new BigDecimal(detalleFactura.getCantidad()));
//            subtotal = subtotal.set
            detalleFactura.setIva(subtotal.multiply(new BigDecimal(0.13).setScale(2, RoundingMode.UP)));
            detalleFactura.setTotal(subtotal.subtract(detalleFactura.getIva()).setScale(2, RoundingMode.UP));
        }
    }

    
    public String agregarFactura() {
        factura = new Factura();
        factura.setFecha(new Date());
        factura.setDetalleFacturaList(new ArrayList<DetalleFactura>());
        detalleFactura = new DetalleFactura();
        return "factura.xhtml?faces-redirect=true";
    }
    
    public String agregarDetalle() {
    
        try {
            detalleFactura.setPrecio(detalleFactura.getIdProducto().getPrecio());
            detalleFactura.setIdFactura(factura);
            factura.getDetalleFacturaList().add(detalleFactura);
            factura = facturaFacade.edit(factura);
            detalleFactura = new DetalleFactura();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos Guardados", "Se agregó el producto correspondiente."));
        } catch (Exception e) {
            System.out.println("ERROR al agregar el detalle " + e);
        }
        return "factura.xhtml?faces-redirect=true";
    }
    
        public String borrarDetalle() {
    
        try {
            factura.getDetalleFacturaList().remove(detalleFactura);
            factura = facturaFacade.edit(factura);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos Borrado", "Se borro el producto correspondiente."));
        } catch (Exception e) {
            System.out.println("ERROR al borrar el detalle " + e);
        }
        return "factura.xhtml?faces-redirect=true";
    }
    
    public List<Empleado> listarEmpleados() {
        lstEmpleado = empleadoFacade.findAll();
        return lstEmpleado;
    }

    public List<Cliente> listarClientes() {
        lstCliente = clienteFacade.findAll();
        return lstCliente;
    }

    public List<FormaPago> listarFormaPago() {
        lstFormaPago = formaPagoFacade.findAll();
        return lstFormaPago;
    }

    public List<Producto> listarProductos() {
        lstProducto = productoFacade.findAll();
        return lstProducto;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public List<Empleado> getLstEmpleado() {
        return lstEmpleado;
    }

    public void setLstEmpleado(List<Empleado> lstEmpleado) {
        this.lstEmpleado = lstEmpleado;
    }

    public List<Cliente> getLstCliente() {
        return lstCliente;
    }

    public void setLstCliente(List<Cliente> lstCliente) {
        this.lstCliente = lstCliente;
    }

    public List<FormaPago> getLstFormaPago() {
        return lstFormaPago;
    }

    public void setLstFormaPago(List<FormaPago> lstFormaPago) {
        this.lstFormaPago = lstFormaPago;
    }

    public List<Producto> getLstProducto() {
        return lstProducto;
    }

    public void setLstProducto(List<Producto> lstProducto) {
        this.lstProducto = lstProducto;
    }

    public DetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(DetalleFactura detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

}
