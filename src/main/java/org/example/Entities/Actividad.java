package org.example.Entities;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "actividad", schema = "carbono")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private org.example.Entities.Categoria idCategoria;

    @OneToMany(mappedBy = "idActividad")
    private Set<org.example.Entities.Habito> habitos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idActividad")
    private Set<org.example.Entities.Huella> huellas = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public org.example.Entities.Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(org.example.Entities.Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Set<org.example.Entities.Habito> getHabitos() {
        return habitos;
    }

    public void setHabitos(Set<org.example.Entities.Habito> habitos) {
        this.habitos = habitos;
    }

    public Set<org.example.Entities.Huella> getHuellas() {
        return huellas;
    }

    public void setHuellas(Set<org.example.Entities.Huella> huellas) {
        this.huellas = huellas;
    }


    @Override
    public String toString() {
        if (idCategoria != null && idCategoria.getId() == null) {
            Hibernate.initialize(idCategoria);
        }

        return "Actividad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", idCategoria=" + (idCategoria != null ? idCategoria.getId() : "Sin categoría") +  // Accede directamente al id
                ", habitos=" + habitos +
                ", huellas=" + huellas +
                '}';
    }


}