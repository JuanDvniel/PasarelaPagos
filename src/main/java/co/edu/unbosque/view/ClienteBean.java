package co.edu.unbosque.view;

import co.edu.unbosque.model.dto.ClienteDTO;
import co.edu.unbosque.model.mappers.ClienteMapper;
import co.edu.unbosque.services.ClienteService;
import co.edu.unbosque.utils.FacesUtils;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ClienteBean implements Serializable {

    private ClienteDTO clienteDTO;
    private List<ClienteDTO> clientes;

    @Inject
    private ClienteService clienteService;

    @PostConstruct
    public void init() {
        clienteDTO = new ClienteDTO();
        cargarClientes();
    }


    public void registrarCliente() {
        try {
            clienteService.registrarCliente(ClienteMapper.toEntity(clienteDTO));
            FacesUtils.mostrarMensajeExito("Cliente registrado con éxito.");
            limpiarFormulario();
            cargarClientes();
        } catch (Exception e) {
            FacesUtils.mostrarMensajeError("Error al registrar el cliente: " + e.getMessage());
        }
    }

    public void eliminarCliente(int id) {
        try {
            clienteService.eliminarCliente(id);
            FacesUtils.mostrarMensajeExito("Cliente eliminado correctamente.");
            cargarClientes();
        } catch (Exception e) {
            FacesUtils.mostrarMensajeError("Error al eliminar el cliente: " + e.getMessage());
        }
    }

    public void cargarClientes() {
        clientes = ClienteMapper.toDTOList(clienteService.obtenerTodos());
    }

    public void limpiarFormulario() {
        clienteDTO = new ClienteDTO();
    }

    public ClienteDTO getClienteDTO() {
        return clienteDTO;
    }

    public void setClienteDTO(ClienteDTO clienteDTO) {
        this.clienteDTO = clienteDTO;
    }

    public List<ClienteDTO> getClientes() {
        if (clientes == null) {
            cargarClientes();
        }
        return clientes;
    }

    public void setClientes(List<ClienteDTO> clientes) {
        this.clientes = clientes;
    }
}
