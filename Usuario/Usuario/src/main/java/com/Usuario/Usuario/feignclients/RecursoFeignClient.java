package com.Usuario.Usuario.feignclients;


import java.util.List;

import com.Usuario.Usuario.modelo.Recurso;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "Recurso", url = "http://localhost:8003")
//@RequestMapping("/recursos")
public interface RecursoFeignClient {

    @PostMapping
    Recurso save(@RequestBody Recurso recurso);

    @GetMapping("/usuario/{usuarioId}")
    List<Recurso> getRecursos(@PathVariable("usuarioId") int usuarioId);
}
