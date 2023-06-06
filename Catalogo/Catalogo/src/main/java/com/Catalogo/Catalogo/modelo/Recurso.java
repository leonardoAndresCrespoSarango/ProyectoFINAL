package com.Catalogo.Catalogo.modelo;


public class Recurso {
    private int usuarioId;
    private int catalogoId;

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

    public Recurso(){
        super();
    }
}
