package com.Usuario.Usuario.Service;

import com.Usuario.Usuario.Entity.Usuario;
import com.Usuario.Usuario.modelo.Recurso;
import com.Usuario.Usuario.feignclients.RecursoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UsuarioService {
    private final RestTemplate restTemplate;

    @Autowired
    private RecursoFeignClient recursoFeignClient;
    @PersistenceContext
    private EntityManager entityManager;


    public UsuarioService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public List<Usuario> getListaTodo() {
        String jpql = "SELECT c FROM Usuario c";
        Query query = entityManager.createQuery(jpql, Usuario.class);
        List<Usuario> lista = query.getResultList();
        return lista;
    }

    public void insertarCatalogo(Usuario usuario) {
        Usuario usuarioExistente = buscarPorUsuario(usuario.getUsu());
        if (usuarioExistente == null) {
            entityManager.persist(usuario);
        } else {
            throw new RuntimeException("El usuario ya existe");
        }
    }

    public Usuario actualizarUsuario(int id, Usuario usuarioActualizado) {
        Usuario usuarioExistente = entityManager.find(Usuario.class, id);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setApellido(usuarioActualizado.getApellido());
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
            usuarioExistente.setUsu(usuarioActualizado.getUsu());
            usuarioExistente.setContrasenia(usuarioActualizado.getContrasenia());
            entityManager.merge(usuarioExistente);
        }
        return usuarioExistente;
    }

    public void eliminarUsuario(int id) {
        Usuario usuarioExistente = entityManager.find(Usuario.class, id);
        if (usuarioExistente != null) {
            entityManager.remove(usuarioExistente);
        }
    }

    public Usuario buscarPorId(int id) {
        return entityManager.find(Usuario.class, id);
    }



    public Usuario buscarPorUsuario(String usu) {
        String jpql = "SELECT u FROM Usuario u WHERE u.usu = :usu";
        Query query = entityManager.createQuery(jpql, Usuario.class);
        query.setParameter("usu", usu);
        List<Usuario> usuarios = query.getResultList();
        if (!usuarios.isEmpty()) {
            return usuarios.get(0);
        }
        return null;
    }

    public Usuario buscarPorUsuarioYContrasenia(String usu, String contrasenia) {
        String jpql = "SELECT u FROM Usuario u WHERE u.usu = :usu AND u.contrasenia = :contrasenia";
        Query query = entityManager.createQuery(jpql, Usuario.class);
        query.setParameter("usu", usu);
        query.setParameter("contrasenia", contrasenia);
        List<Usuario> usuarios = query.getResultList();
        if (!usuarios.isEmpty()) {
            return usuarios.get(0);
        }
        return null;
    }

    //////////AQUI VAN LOS SERVICIOS QUE ENLAZAN LOS MICROSERVICIOS
    public List<Recurso> getRecursos(int usuarioId) {
        String url = "http://localhost:8003/recursos/usuario/" + usuarioId;
        ResponseEntity<List<Recurso>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Recurso>>() {}
        );
        List<Recurso> recursos = response.getBody();
        return recursos;
    }

    public Recurso saveRecurso(int usuarioId, Recurso recurso) {
        recurso.setUsuarioId(usuarioId);
        Recurso nuevoRecurso = recursoFeignClient.save(recurso);
        return nuevoRecurso;
    }


}