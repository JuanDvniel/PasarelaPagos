package co.edu.unbosque.model.mappers;

import co.edu.unbosque.model.Cliente;
import co.edu.unbosque.model.dto.ClienteDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return new ClienteDTO(cliente.getId(),
                cliente.getCedula(),
                cliente.getNombres(),
                cliente.getApellidos());
    }

    public static Cliente toEntity(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setCedula(clienteDTO.getCedula());

        String[] partes = clienteDTO.getNombreCompleto().split(" ", 2);
        cliente.setNombres(partes[0]);
        cliente.setApellidos(partes.length > 1 ? partes[1] : "");

        return cliente;
    }

    public static List<ClienteDTO> toDTOList(List<Cliente> clientes) {
        return clientes.stream().map(ClienteMapper::toDTO).collect(Collectors.toList());
    }


    public static List<Cliente> toEntityList(List<ClienteDTO> clienteDTOs) {
        return clienteDTOs.stream().map(ClienteMapper::toEntity).collect(Collectors.toList());
    }
}
