package co.edu.unbosque.model.dao.impl;

import co.edu.unbosque.model.Pago;
import co.edu.unbosque.model.dao.DAO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.util.List;

@Named
@ApplicationScoped
public class PagoDAOImpl implements DAO<Pago, Integer> {

    @PersistenceContext(unitName = "PasarelaPU")
    private EntityManager em;

    @Override
    public void create(Pago entity) {
        em.persist(entity);
        em.flush();
    }

    @Override
    public Pago findById(Integer id) {
        return em.find(Pago.class, id);
    }

    @Override
    public List<Pago> findAll() {
        return em.createQuery("SELECT p FROM Pago p", Pago.class).getResultList();
    }

    @Override
    public void update(Pago entity) {
        em.merge(entity);
        em.flush();
    }

    @Override
    public void delete(Integer id) {
        Pago pago = em.find(Pago.class, id);
        if (pago != null) {
            em.remove(pago);
            em.flush();
        }
    }

    public boolean esPrimeraCompra(int idCliente) {
        Long count = em.createQuery(
                        "SELECT COUNT(p) FROM Pago p JOIN p.cliente c WHERE c.id = :idCliente",
                        Long.class)
                .setParameter("idCliente", idCliente)
                .getSingleResult();
        return count == 0;
    }


    public String generarNumeroTransaccion() {
        String fechaActual = LocalDateTime.now().toLocalDate().toString().replace("-", "");

        String lastTransaction = em.createQuery(
                        "SELECT p.numeroTransaccion FROM Pago p " +
                                "WHERE FUNCTION('DATE', p.fechaHora) = CURRENT_DATE " +
                                "ORDER BY p.id DESC",
                        String.class)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);

        int secuencia = 1;
        if (lastTransaction != null) {
            try {
                String[] parts = lastTransaction.split("-");
                secuencia = Integer.parseInt(parts[2]) + 1;
            } catch (NumberFormatException e) {
                secuencia = 1;
            }
        }
        return String.format("TRANS-%s-%05d", fechaActual, secuencia);
    }

}