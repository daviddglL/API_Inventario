package dto;

import com.google.gson.annotations.Expose;

public class CategoriasDTO {
    @Expose
    private String inventario,categoria;

    public CategoriasDTO() {
    }

    public CategoriasDTO(String inventario, String categoria) {
        this.inventario = inventario;
        this.categoria = categoria;
    }

    public String getInventario() {
        return inventario;
    }

    public void setInventario(String inventario) {
        this.inventario = inventario;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "CategoriasDTO{" +
                "inventario='" + inventario + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
