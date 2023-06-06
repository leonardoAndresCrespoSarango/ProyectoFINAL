package com.Catalogo.Catalogo.feignclients;

import com.Catalogo.Catalogo.modelo.Recurso;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "Recurso", url = "http://localhost:8003")
//@RequestMapping("/recursos")
public interface RecursoFeignClient {

    @PostMapping()
    Recurso save(@RequestBody Recurso recurso);

    @GetMapping("/catalogo/{catalogoId}")
    List<Recurso> getRecursos(@PathVariable("catalogoId") int catalogoId);
}