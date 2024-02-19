package entidades;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Inventarios")
public class Inventarios implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Column(name = "Nombre")
    @Expose
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="proveedor_key",foreignKey = @ForeignKey(name = "fk_inv_prov"))
    private Proveedores prov;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="producto_key",foreignKey = @ForeignKey(name = "fk_inv_prod"))
    private Productos producto;

    @Column(name = "Fecha_Inscripcion",nullable = false)
    @Expose
    private LocalDate fecha_inscripcion;


    public Inventarios() {
    }

    public Inventarios(Long id, String nombre, Proveedores prov, Productos producto, LocalDate fecha_inscripcion) {
        this.id = id;
        this.nombre = nombre;
        this.prov = prov;
        this.producto = producto;
        this.fecha_inscripcion = fecha_inscripcion;
    }

    public LocalDate getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public void setFecha_inscripcion(LocalDate fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Proveedores getProv() {
        return prov;
    }

    public void setProv(Proveedores prov) {
        this.prov = prov;
    }

    public Productos getProd() {
        return producto;
    }

    public void setProd(Productos producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Inventarios{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", prov=" + prov +
                ", producto=" + producto +
                ", fecha_inscripcion=" + fecha_inscripcion +
                '}';
    }
}
