package co.edu.unbosque.view;

import co.edu.unbosque.model.Pago;
import co.edu.unbosque.model.dto.PagoDTO;
import co.edu.unbosque.services.PagoService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Named
@RequestScoped
public class PagoBean implements Serializable {

    private PagoDTO pagoDTO;
    private List<PagoDTO> listaPagos;
    private Pago transaccionSeleccionada;

    @Inject
    private PagoService pagoService;

    @PostConstruct
    public void init() {
        pagoDTO = new PagoDTO();
        pagoDTO.setValorTotal(BigDecimal.ZERO);
        pagoDTO.setValorIVA(BigDecimal.ZERO);

        transaccionSeleccionada = (Pago) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("transaccionSeleccionada");

        cargarPagos();

        cargarPagos();
    }

    public void registrarPago() {
        try {
            System.out.println("Registrando pago con datos: " + pagoDTO);
            pagoDTO = pagoService.registrarPago(pagoDTO);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Éxito", "Pago registrado con estado: " + pagoDTO.getEstado()));
            cargarPagos();
            limpiarFormulario();
        } catch (Exception e) {
            e.printStackTrace(); // Imprime toda la pila de errores
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", "No se pudo registrar el pago: " + e.getClass().getName() + " - " + e.getMessage()));
        }
    }

    public void seleccionarTransaccion(Pago pago) {
        this.transaccionSeleccionada = pago;
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("recibo.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void verRecibo(Integer id) {
        System.out.println("📌 Cargando recibo para el pago ID: " + id);

        transaccionSeleccionada = pagoService.buscarPorId(id);

        if (transaccionSeleccionada == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el pago."));
            return;
        }

        // Almacenar el pago en sesión para que persista después del redirect
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("transaccionSeleccionada", transaccionSeleccionada);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("recibo.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void cargarPagos() {
        listaPagos = pagoService.obtenerPagos();
    }

    public void limpiarFormulario() {
        pagoDTO.setIdCliente(0);
        pagoDTO.setUltimosDigitosTarjeta(null);
        pagoDTO.setNombreComercio(null);
        pagoDTO.setValorTotal(BigDecimal.ZERO);
        pagoDTO.setValorIVA(BigDecimal.ZERO);
    }

    public String formatearFecha(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHora.format(formatter);
    }

    public PagoDTO getPagoDTO() {
        return pagoDTO;
    }

    public void setPagoDTO(PagoDTO pagoDTO) {
        this.pagoDTO = pagoDTO;
    }

    public List<PagoDTO> getListaPagos() {
        if (listaPagos == null) {
            cargarPagos();
        }
        return listaPagos;
    }

    public void setListaPagos(List<PagoDTO> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public Pago getTransaccionSeleccionada() {
        return transaccionSeleccionada;
    }

    public void setTransaccionSeleccionada(Pago transaccionSeleccionada) {
        this.transaccionSeleccionada = transaccionSeleccionada;
    }

    public PagoService getPagoService() {
        return pagoService;
    }

    public void setPagoService(PagoService pagoService) {
        this.pagoService = pagoService;
    }
}
