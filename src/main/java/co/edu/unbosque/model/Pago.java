package co.edu.unbosque.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "estado", nullable = false, length = 10)
    private String estado;

    @Column(name = "bin_tarjeta", nullable = false, length = 6)
    private String binTarjeta;

    @Column(name = "ultimos_digitos_tarjeta", nullable = true, length = 4)
    private String ultimosDigitosTarjeta;

    @Column(name = "nombre_comercio", nullable = false, length = 100)
    private String nombreComercio;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "valor_iva", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorIVA;

    @Column(name = "numero_transaccion", nullable = false, unique = true, length = 25)
    private String numeroTransaccion;

    public Pago() {

    }

    public Pago(int id, Cliente cliente, LocalDateTime fechaHora, String estado, String binTarjeta,
                String ultimosDigitosTarjeta, String nombreComercio, BigDecimal valorBase,
                boolean esPrimerPago, String numeroTransaccion) {
        this.id = id;
        this.cliente = cliente;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.binTarjeta = binTarjeta;
        this.ultimosDigitosTarjeta = ultimosDigitosTarjeta;
        this.nombreComercio = nombreComercio;
        this.valorTotal = aplicarDescuento(valorBase, esPrimerPago);
        this.valorIVA = calcularIVA(this.valorTotal);
        this.numeroTransaccion = numeroTransaccion;
    }

    private BigDecimal calcularIVA(BigDecimal valorTotal) {
        return valorTotal.multiply(BigDecimal.valueOf(0.16));
    }

    private BigDecimal aplicarDescuento(BigDecimal valorBase, boolean esPrimerPago) {
        if (esPrimerPago) {
            return valorBase.multiply(BigDecimal.valueOf(0.98));
        }
        return valorBase;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public String getBinTarjeta() {
        return binTarjeta;
    }

    public void setBinTarjeta(String binTarjeta) {
        this.binTarjeta = binTarjeta;
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
}