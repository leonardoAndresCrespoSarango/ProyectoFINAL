package com.Catalogo.Catalogo.Service;

import com.Catalogo.Catalogo.Entity.Catalogo;
import com.Catalogo.Catalogo.modelo.Recurso;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import javax.transaction.Transactional;

@Service
@Transactional
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
        Catalogo catalogoExistente = buscarPorTtitulo(catalogo.getTitulo());
        if (catalogoExistente == null) {
            entityManager.persist(catalogo);
        } else {
            throw new RuntimeException("El Catalogo ya existe");
        }
    }

    public Catalogo actualizarCatalogo(int id, Catalogo catalogoActualizado) {
        Catalogo catalogoExistente = entityManager.find(Catalogo.class, id);
        if (catalogoExistente != null) {
            catalogoExistente.setTitulo(catalogoActualizado.getTitulo());
            catalogoExistente.setDescripcion(catalogoActualizado.getDescripcion());
            catalogoExistente.setUrl(catalogoActualizado.getUrl());
            catalogoExistente.setPrecio(catalogoActualizado.getPrecio());

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

    public Catalogo buscarPorTtitulo(String titulo) {
        String jpql = "SELECT u FROM Catalogo u WHERE u.titulo = :titulo";
        Query query = entityManager.createQuery(jpql, Catalogo.class);
        query.setParameter("titulo", titulo);
        List<Catalogo> titulos = query.getResultList();
        if (!titulos.isEmpty()) {
            return titulos.get(0);
        }
        return null;
    }
    ///AQUI EMPIEZA EL ENLACE DE MICROSERVICIOS
    public List<Recurso> getRecursos(int catalogoId) {
        String url = "http://localhost:8003/recursos/catalogo/" + catalogoId;
        ResponseEntity<List<Recurso>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Recurso>>() {}
        );
        List<Recurso> recursos = response.getBody();
        return recursos;
    }

}