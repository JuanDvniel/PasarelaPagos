package co.edu.unbosque.services;

import co.edu.unbosque.model.Cliente;
import co.edu.unbosque.model.Pago;
import co.edu.unbosque.model.dao.impl.PagoDAOImpl;
import co.edu.unbosque.model.dto.PagoDTO;
import co.edu.unbosque.model.mappers.PagoMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class PagoService {

    @Inject
    private PagoDAOImpl pagoDAO;

    @Inject
    private ClienteService clienteService;

    @Transactional
    public PagoDTO registrarPago(PagoDTO pagoDTO) {
        System.out.println("Registro de pago para el cliente id: " + pagoDTO.getIdCliente());

        Cliente cliente = clienteService.buscarPorIdEntidad(pagoDTO.getIdCliente());
        if (cliente == null) {
            System.out.println("Cliente no encontrado: " + pagoDTO.getIdCliente());
            throw new IllegalArgumentException("El cliente con ID " + pagoDTO.getIdCliente() + " no existe.");
        }

        System.out.println("Cliente encontrado: " + cliente.getNombres());

        if (pagoDTO.getUltimosDigitosTarjeta() == null || pagoDTO.getUltimosDigitosTarjeta().length() < 4) {
            throw new IllegalArgumentException("Los ultimos 4 dígitos de la tarjeta son obligatorios.");
        }

        if (pagoDTO.getBinTarjeta() == null || pagoDTO.getBinTarjeta().length() < 6) {
            throw new IllegalArgumentException("Los primeros 6 dígitos de la tarjeta son obligatorios.");
        }

        boolean esPrimerPago = pagoDAO.esPrimeraCompra(pagoDTO.getIdCliente());
        String numeroTransaccion = pagoDAO.generarNumeroTransaccion();
        String estado = simularEstadoPago();

        BigDecimal valorBase = pagoDTO.getValorTotal() != null ? pagoDTO.getValorTotal() : BigDecimal.ZERO;
        BigDecimal valorTotalConDescuento = esPrimerPago ? valorBase.multiply(BigDecimal.valueOf(0.98)) : valorBase;
        BigDecimal valorIVA = valorTotalConDescuento.multiply(BigDecimal.valueOf(0.16));

        Pago pago = new Pago();
        pago.setCliente(cliente);
        pago.setFechaHora(LocalDateTime.now());
        pago.setEstado(estado);
        pago.setBinTarjeta(pagoDTO.getBinTarjeta());
        pago.setUltimosDigitosTarjeta(pagoDTO.getUltimosDigitosTarjeta());
        pago.setNombreComercio(pagoDTO.getNombreComercio());
        pago.setValorTotal(valorTotalConDescuento);
        pago.setValorIVA(valorIVA);
        pago.setNumeroTransaccion(numeroTransaccion);

        System.out.println("Guardando estado: " + estado);

        pagoDAO.create(pago);

        System.out.println("Pago registrado con éxito. Número de transacción: " + numeroTransaccion);

        return PagoMapper.toDTO(pago);
    }

    public Pago buscarPorId(Integer id) {
        System.out.println("Buscando id: " + id);
        Pago pago = pagoDAO.findById(id);
        if (pago == null) {
            System.out.println("No se encontró el pago con ID: " + id);
        } else {
            System.out.println("Pago encontrado: " + pago.getNumeroTransaccion());
        }
        return pago;
    }


    public List<PagoDTO> obtenerPagos() {
        List<Pago> pagos = pagoDAO.findAll();
        return PagoMapper.toDTOList(pagos);
    }

    private String simularEstadoPago() {
        return new Random().nextBoolean() ? "ACEPTADO" : "RECHAZADO";
    }
}
