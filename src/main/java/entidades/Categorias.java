package entidades;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Categoria")
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id_categoria;

    @Column(name = "Nombre")
    @Expose
    private String nombre;

    @ManyToMany(mappedBy = "cat")
    private List<Productos> cat = new ArrayList<>();


    public List<Productos> getCat() {
        return cat;
    }

    public Categorias() {
    }

    public Categorias(Long id, String nombre) {
        this.id_categoria = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id_categoria;
    }

    public void setId(Long id) {
        this.id_categoria = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Productos> getCateg() {
        return cat;
    }

    public void setCat(List<Productos> cat) {
        this.cat = cat;
    }
}
