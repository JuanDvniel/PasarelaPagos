package co.edu.unbosque.services;

import co.edu.unbosque.model.Cliente;
import co.edu.unbosque.model.dao.impl.ClienteDAOImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    private ClienteDAOImpl clienteDAO;

    @Transactional
    public void registrarCliente(Cliente cliente) {
        clienteDAO.create(cliente);
    }

    public Cliente buscarPorIdEntidad(Integer id) {
        return clienteDAO.findById(id);
    }

    public List<Cliente> obtenerTodos() {
        return clienteDAO.findAll();
    }

    @Transactional
    public void actualizarCliente(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    @Transactional
    public void eliminarCliente(Integer id) {
        clienteDAO.delete(id);
    }
}
