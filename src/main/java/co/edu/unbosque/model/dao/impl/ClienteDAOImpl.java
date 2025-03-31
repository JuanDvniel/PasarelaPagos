package co.edu.unbosque.model.dao.impl;

import co.edu.unbosque.model.Cliente;
import co.edu.unbosque.model.dao.DAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Named
@ApplicationScoped
public class ClienteDAOImpl implements DAO<Cliente, Integer> {

    @PersistenceContext(unitName = "PasarelaPU")
    private EntityManager em;

    @Override
    public void create(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        em.persist(cliente);
    }

    @Override
    public Cliente findById(Integer id) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente con ID " + id + " no encontrado.");
        }
        return cliente;
    }

    @Override
    public List<Cliente> findAll() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    @Override
    public void update(Cliente entity) {
        if (entity == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo.");
        }
        em.merge(entity);
    }

    @Override
    public void delete(Integer id) {
        Cliente cliente = em.find(Cliente.class, id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente con ID " + id + " no encontrado. No se puede eliminar.");
        }
        em.remove(cliente);
    }
}
