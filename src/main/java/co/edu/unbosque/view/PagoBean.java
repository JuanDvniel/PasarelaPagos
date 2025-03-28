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
    private Pago transaccionSeleccionada; // Transacción que se va a mostrar en el recibo

    @Inject
    private PagoService pagoService;

    @PostConstruct
    public void init() {
        pagoDTO = new PagoDTO();
        pagoDTO.setValorTotal(BigDecimal.ZERO);
        pagoDTO.setValorIVA(BigDecimal.ZERO);
        cargarPagos();
    }

    public void registrarPago() {
        try {
            System.out.println("📌 Registrando pago con datos: " + pagoDTO);
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

    // Método para seleccionar la transacción y redirigir a recibo.xhtml
    public void seleccionarTransaccion(Pago pago) {
        this.transaccionSeleccionada = pago;
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("recibo.xhtml");
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

    // Getters y Setters
    public PagoDTO getPagoDTO() {
        return pagoDTO;
    }

    public void setPagoDTO(PagoDTO pagoDTO) {
        this.pagoDTO = pagoDTO;
    }

    public List<PagoDTO> getListaPagos() {
        // Garantiza que siempre se tenga la última versión de los pagos
        if (listaPagos == null) {
            cargarPagos();
        }
        return listaPagos;
    }

    public void setListaPagos(List<PagoDTO> listaPagos) {
        this.listaPagos = listaPagos;
    }
}
