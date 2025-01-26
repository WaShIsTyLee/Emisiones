package org.example.Entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "huella", schema = "carbono")
public class Huella {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private org.example.Entities.Usuario idUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_actividad")
    private Actividad idActividad;

    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "unidad", length = 50)
    private String unidad;

    @Column(name = "fecha")
    private LocalDate fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public org.example.Entities.Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(org.example.Entities.Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Actividad getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Actividad idActividad) {
        this.idActividad = idActividad;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

}