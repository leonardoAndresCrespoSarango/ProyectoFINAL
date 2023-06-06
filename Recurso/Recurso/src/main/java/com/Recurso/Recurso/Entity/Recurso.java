package com.Recurso.Recurso.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Recurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int usuarioId;
    private int catalogoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getCatalogoId() {
        return catalogoId;
    }

    public void setCatalogoId(int catalogoId) {
        this.catalogoId = catalogoId;
    }

    public Recurso() {
        super();
    }

}
