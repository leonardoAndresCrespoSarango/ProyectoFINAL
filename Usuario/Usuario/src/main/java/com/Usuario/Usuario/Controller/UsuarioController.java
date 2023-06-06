package com.Usuario.Usuario.Controller;

import com.Usuario.Usuario.Entity.Usuario;

import com.Usuario.Usuario.Service.UsuarioService;
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
    public void crearCatalogo(@RequestBody Usuario usuario) {
        usuService.insertarCatalogo(usuario);
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
}