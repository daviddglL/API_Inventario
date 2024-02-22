package entidades;


import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Almacen")
public class Almacen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id_almacen;


    @ManyToMany(mappedBy = "alma")
    private List<Productos> producto=new ArrayList<>();
    @OneToMany(mappedBy = "almacen_key",fetch = FetchType.LAZY)
    private List<Arrendatario> almacen=new ArrayList<>();
    @Column(name = "Fecha_Inscripcion",nullable = false)
    @Expose
    private LocalDate fecha_inscripcion;

    public Almacen() {
    }

    public Almacen(Long id, LocalDate fecha_inscripcion) {
        this.id_almacen = id;
        this.fecha_inscripcion = fecha_inscripcion;
    }


    public void setProducto(List<Productos> producto) {
        this.producto = producto;
    }

    public Long getId_almacen() {
        return id_almacen;
    }

    public void setId_almacen(Long id_almacen) {
        this.id_almacen = id_almacen;
    }

    public List<Arrendatario> getAlmacen() {
        return almacen;
    }

    public void setAlmacen(List<Arrendatario> almacen) {
        this.almacen = almacen;
    }

    public LocalDate getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public void setFecha_inscripcion(LocalDate fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }

    @Override
    public String toString() {
        return "Inventarios{" +
                "id=" + id_almacen +
                ", producto=" + producto +
                ", fecha_inscripcion=" + fecha_inscripcion +
                '}';
    }}