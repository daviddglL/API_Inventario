package entidades;


import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Productos")
public class Productos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id_producto;

    @Column(name = "Nombre",length = 100,nullable = false)
    @Expose
    private String nombre;

    @Column(name = "Numero_Serie",unique = true)
    @Expose
    private String ns;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_almacen",
            joinColumns = @JoinColumn(name = "id_producto"),
            inverseJoinColumns = @JoinColumn(name = "id_almacen")
    )
    private List<Almacen> alma=new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="proveedor_key",foreignKey = @ForeignKey(name = "fk_mueble_prov"))
    private Proveedores prod;

    @Column(name = "Precio_Unitario",length = 10,nullable = false)
    @Expose
    private Double precio_uni;

    @Column(name = "Imagen")
    @Expose
    private String imagen;

    public Productos() {}

    public Productos(Long id, String nombre, Double precio_uni, String imagen,String ns) {
        this.id_producto = id;
        this.nombre = nombre;
        this.precio_uni = precio_uni;
        this.imagen = imagen;
        this.ns=ns;
    }

    public Proveedores getProd() {
        return prod;
    }

    public void setProd(Proveedores prod) {
        this.prod = prod;
    }

    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio_uni() {
        return precio_uni;
    }

    public void setPrecio_uni(Double precio_uni) {
        this.precio_uni = precio_uni;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public List<Almacen> getAlma() {
        return alma;
    }

    public void setAlma(List<Almacen> alma) {
        this.alma = alma;
    }

    @Override
    public String toString() {
        return "Productos{" +
                "id_producto=" + id_producto +
                ", nombre='" + nombre + '\'' +
                ", ns='" + ns + '\'' +
                ", precio_uni=" + precio_uni +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
