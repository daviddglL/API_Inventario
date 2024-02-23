package entidades;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Proveedores")
public class Proveedores implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private Long id;

    @Column(name = "Nombre")
    @Expose
    private String nombre;

    @Column(name = "Direccion",length = 100,unique = true)
    @Expose
    private String origen;

    @Column(name = "Telefono",length = 100,unique = true)
    @Expose
    private  String telefono;

    @Column(name = "Email",length = 100,nullable = false)
    @Expose
    private String email;

    @OneToMany(mappedBy = "prod",fetch = FetchType.LAZY)
    private List<Productos> prod=new ArrayList<>();


    public Proveedores() {
    }

    public Proveedores( String nombre, String origen, String telefono, String email) {
        this.nombre = nombre;
        this.origen = origen;
        this.telefono = telefono;
        this.email = email;
    }


    public List<Productos> getProd() {
        return prod;
    }

    public void setProd(List<Productos> prod) {
        this.prod = prod;
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

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
