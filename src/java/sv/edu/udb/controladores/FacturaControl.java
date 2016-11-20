package sv.edu.udb.controladores;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import sv.edu.udb.entidades.Cliente;
import sv.edu.udb.entidades.DetalleFactura;
import sv.edu.udb.entidades.Empleado;
import sv.edu.udb.entidades.Factura;
import sv.edu.udb.entidades.FormaPago;
import sv.edu.udb.entidades.Producto;
import sv.edu.udb.modelo.ClienteFacade;
import sv.edu.udb.modelo.EmpleadoFacade;
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
            detalleFactura.setIva(subtotal.multiply(new BigDecimal(0.13).setScale(2, RoundingMode.UP)));
            detalleFactura.setTotal(subtotal.subtract(detalleFactura.getIva()).setScale(2, RoundingMode.UP));
        }
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
