package co.edu.unbosque.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoDTO {

    private int id;
    private int idCliente;
    private String nombreCliente;
    private LocalDateTime fechaHora = LocalDateTime.now();
    private String estado;
    private String binTarjeta;
    private String ultimosDigitosTarjeta;
    private String nombreComercio;
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private BigDecimal valorIVA = BigDecimal.ZERO;
    private String numeroTransaccion;

    public PagoDTO() {
    }

    public PagoDTO(int id, int idCliente, String nombreCliente, LocalDateTime fechaHora, String estado,
                   String binTarjeta ,String ultimosDigitosTarjeta, String nombreComercio, BigDecimal valorTotal,
                   BigDecimal valorIVA, String numeroTransaccion) {
        this.id = id;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.binTarjeta = binTarjeta;
        this.ultimosDigitosTarjeta = ultimosDigitosTarjeta;
        this.nombreComercio = nombreComercio;
        this.valorTotal = valorTotal;
        this.valorIVA = valorIVA;
        this.numeroTransaccion = numeroTransaccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUltimosDigitosTarjeta() {
        return ultimosDigitosTarjeta;
    }

    public void setUltimosDigitosTarjeta(String ultimosDigitosTarjeta) {
        this.ultimosDigitosTarjeta = ultimosDigitosTarjeta;
    }

    public String getNombreComercio() {
        return nombreComercio;
    }

    public void setNombreComercio(String nombreComercio) {
        this.nombreComercio = nombreComercio;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorIVA() {
        return valorIVA;
    }

    public void setValorIVA(BigDecimal valorIVA) {
        this.valorIVA = valorIVA;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getBinTarjeta() {
        return binTarjeta;
    }

    public void setBinTarjeta(String binTarjeta) {
        this.binTarjeta = binTarjeta;
    }
}
