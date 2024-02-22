package entidades;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "Arrendatario")
public class Arrendatario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id_arrendatario;

    @Column(name = "n_Serie")
    @Expose
    private String n_Serie;

    @Column(name = "Nombre")
    @Expose
    private String nombre;

    @Column(name = "Telefono")
    @Expose
    private Integer telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "almacen_key", foreignKey = @ForeignKey(name = "fk_cli_alm"))
    private Almacen almacen;

    public Arrendatario() {
    }


    public Arrendatario(Long id_arrendatario, String n_Serie, String nombre, Integer telefono, Almacen almacen) {
        this.id_arrendatario = id_arrendatario;
        this.n_Serie = n_Serie;
        this.nombre = nombre;
        this.telefono = telefono;
        this.almacen = almacen;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public Long getId_arrendatario() {
        return id_arrendatario;
    }

    public void setId_arrendatario(Long id_arrendatario) {
        this.id_arrendatario = id_arrendatario;
    }

    public String getN_Serie() {
        return n_Serie;
    }

    public void setN_Serie(String n_Serie) {
        this.n_Serie = n_Serie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }



    @Override
    public String toString() {
        return "Arrendatario{" +
                "id_arrendatario=" + id_arrendatario +
                ", n_Serie='" + n_Serie + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono=" + telefono +
                ", almacen=" + almacen +
                '}';
    }
}
