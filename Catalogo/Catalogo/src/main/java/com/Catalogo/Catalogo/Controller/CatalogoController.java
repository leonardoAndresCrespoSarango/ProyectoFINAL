package com.Catalogo.Catalogo.Controller;

import com.Catalogo.Catalogo.Entity.Catalogo;
import com.Catalogo.Catalogo.Service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    private CatalogoService catService;

    @Autowired
    public CatalogoController(CatalogoService catService) {
        this.catService = catService;
    }

    @GetMapping("/todos")
    public List<Catalogo> obtenerTodosLosItems() {
        return catService.getListaTodo();
    }

    @PostMapping("/crear")
    public void crearCatalogo(@RequestBody Catalogo catalogo) {
        catService.insertarCatalogo(catalogo);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarCatalogo(@PathVariable("id") int id, @RequestBody Catalogo catalogoActualizado) {
        Catalogo catalogoActualizadoResultado = catService.actualizarCatalogo(id, catalogoActualizado);
        if (catalogoActualizadoResultado != null) {
            return ResponseEntity.ok("Catálogo actualizado exitosamente");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCatalogo(@PathVariable("id") int id) {
        catService.eliminarCatalogo(id);
        return ResponseEntity.ok("Catálogo eliminado exitosamente");
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Catalogo> buscarPorId(@PathVariable("id") int id) {
        Catalogo catalogoEncontrado = catService.buscarPorId(id);
        if (catalogoEncontrado != null) {
            return ResponseEntity.ok(catalogoEncontrado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
