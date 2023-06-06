package com.Recurso.Recurso.Service;

import com.Recurso.Recurso.Entity.Recurso;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;
import org.springframework.boot.web.client.RestTemplateBuilder;


@Service

public class RecursoService {
    @PersistenceContext
    private EntityManager entityManager;
    private final RestTemplate restTemplate;


    public RecursoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Recurso> getListaTodo() {
        String jpql = "SELECT c FROM recurso c";
        Query query = entityManager.createQuery(jpql, Recurso.class);
        List<Recurso> lista = query.getResultList();
        return lista;
    }

    public void insertarRegistro(Recurso registro) {
        entityManager.persist(registro);
        int catalogoId = registro.getCatalogoId();



    }

    public Recurso actualizarRegistro(int id, Recurso registroActualizado) {
        Recurso registroExistente = entityManager.find(Recurso.class, id);
        if (registroExistente != null) {
            registroExistente.setDetalle(registroExistente.getDetalle());

            entityManager.merge(registroExistente);
        }
        return registroExistente;
    }


    public void eliminarRegistro(int id) {
        Recurso registroExistente = entityManager.find(Recurso.class, id);
        if (registroExistente != null) {
            entityManager.remove(registroExistente);
        }
    }

    public Recurso buscarPorId(int id) {
        return entityManager.find(Recurso.class, id);
    }
}
