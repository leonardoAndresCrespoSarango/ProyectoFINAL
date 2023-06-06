package com.Catalogo.Catalogo.Service;

import com.Catalogo.Catalogo.Entity.Catalogo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service


public class CatalogoService {
    private final RestTemplate restTemplate;
    @PersistenceContext
    private EntityManager entityManager;

    public CatalogoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void insert(Catalogo catalogo) {
        entityManager.persist(catalogo);

    }


    public List<Catalogo> getListaTodo() {
        String jpql = "SELECT c FROM Catalogo c";
        Query query = entityManager.createQuery(jpql, Catalogo.class);
        List<Catalogo> lista = query.getResultList();
        return lista;
    }

    public void insertarCatalogo(Catalogo catalogo) {
        entityManager.persist(catalogo);
    }

    public Catalogo actualizarCatalogo(int id, Catalogo catalogoActualizado) {
        Catalogo catalogoExistente = entityManager.find(Catalogo.class, id);
        if (catalogoExistente != null) {
            catalogoExistente.setNombre(catalogoActualizado.getNombre());
            catalogoExistente.setDescripccion(catalogoActualizado.getDescripccion());

            entityManager.merge(catalogoExistente);
        }
        return catalogoExistente;
    }

    public void eliminarCatalogo(int id) {
        Catalogo catalogoExistente = entityManager.find(Catalogo.class, id);
        if (catalogoExistente != null) {
            entityManager.remove(catalogoExistente);
        }
    }

    public Catalogo buscarPorId(int id) {
        return entityManager.find(Catalogo.class, id);
    }
}