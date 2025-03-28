package co.edu.unbosque.model.mappers;

import co.edu.unbosque.model.Cliente;
import co.edu.unbosque.model.Pago;
import co.edu.unbosque.model.dto.PagoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PagoMapper {


    public static PagoDTO toDTO(Pago pago) {
        if (pago == null) {
            return null;
        }
        return new PagoDTO(
                pago.getId(),
                pago.getCliente().getId(),
                pago.getCliente().getNombres() + " " + pago.getCliente().getApellidos(),
                pago.getFechaHora(),
                pago.getEstado(),
                pago.getUltimosDigitosTarjeta(),
                pago.getNombreComercio(),
                pago.getValorTotal(),
                pago.getValorIVA(),
                pago.getNumeroTransaccion()
        );
    }


    public static Pago toEntity(PagoDTO pagoDTO) {
        if (pagoDTO == null) {
            return null;
        }
        Pago pago = new Pago();
        pago.setId(pagoDTO.getId());

        // Asignar cliente (Se necesita el ID del cliente para buscar la entidad en la BD)
        Cliente cliente = new Cliente();
        cliente.setId(pagoDTO.getIdCliente());
        pago.setCliente(cliente);

        pago.setFechaHora(pagoDTO.getFechaHora());
        pago.setEstado(pagoDTO.getEstado());
        pago.setUltimosDigitosTarjeta(pagoDTO.getUltimosDigitosTarjeta());
        pago.setNombreComercio(pagoDTO.getNombreComercio());
        pago.setValorTotal(pagoDTO.getValorTotal());
        pago.setValorIVA(pagoDTO.getValorIVA());
        pago.setNumeroTransaccion(pagoDTO.getNumeroTransaccion());

        return pago;
    }


    public static List<PagoDTO> toDTOList(List<Pago> pagos) {
        return pagos.stream().map(PagoMapper::toDTO).collect(Collectors.toList());
    }


    public static List<Pago> toEntityList(List<PagoDTO> pagoDTOs) {
        return pagoDTOs.stream().map(PagoMapper::toEntity).collect(Collectors.toList());
    }
}
