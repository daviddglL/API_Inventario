package dao.security;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Entity
@Table(name = "Api_Key")
public class Api_key {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Apikey",length = 40)
    private String token;

    @Column(name = "Número de usos")
    private Integer nusos;

    @Column(name = "Actividad")
    private Boolean actividad;

    @Column(name = "Permisos")
    private String permisos;


    public Api_key() {
    }

    public Api_key(Long id, String token, Integer nusos, Boolean actividad, String permisos) {
        this.id = id;
        this.token = token;
        this.nusos = nusos;
        this.actividad = actividad;
        this.permisos = permisos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getNusos() {
        return nusos;
    }

    public void setNusos(Integer nusos) {
        this.nusos = nusos;
    }

    public Boolean getActividad() {
        return actividad;
    }

    public void setActividad(Boolean actividad) {
        this.actividad = actividad;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }
}
