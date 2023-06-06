package com.Usuario.Usuario.Controller;

import com.Usuario.Usuario.Entity.Usuario;

import com.Usuario.Usuario.Service.UsuarioService;
import com.Usuario.Usuario.modelo.Recurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private UsuarioService usuService;

    @Autowired
    public UsuarioController(UsuarioService usuService) {
        this.usuService = usuService;
    }

    @GetMapping("/todos")
    public List<Usuario> obtenerTodosLosItems() {

        return usuService.getListaTodo();
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearCatalogo(@RequestBody Usuario usuario) {
        try {
            usuService.insertarCatalogo(usuario);
            return ResponseEntity.ok("Usuario creado exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("No se pudo crear el usuario: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable("id") int id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuarioActualizadoResultado = usuService.actualizarUsuario(id, usuarioActualizado);
        if (usuarioActualizadoResultado != null) {
            return ResponseEntity.ok("Usuario actualizado exitosamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable("id") int id) {
        usuService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado exitosamente");
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable("id") int id) {
        Usuario usuarioEncontrado = usuService.buscarPorId(id);
        if (usuarioEncontrado != null) {
            return ResponseEntity.ok(usuarioEncontrado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    ////AQUI VAN LOS ENLACES DE MICROSERVICIOS
    @GetMapping("/recursos/{usuarioId}")
    public ResponseEntity<List<Recurso>> obtenerRecursosUsuario(@PathVariable("usuarioId") int usuarioId) {
        List<Recurso> recursos = usuService.getRecursos(usuarioId);
        return ResponseEntity.ok(recursos);
    }



}