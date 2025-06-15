/*
package com.project.demo.logic.entity.producto;

import com.project.demo.logic.entity.categoria.Categoria;
import jakarta.persistence.*;

@Table(name = "producto")
@Entity
public class Producto {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String nombre;
private String descripcion;
private double precio;
private int cantidadStock;
@ManyToOne
@JoinColumn(name = "categoria_id")
private Categoria categoria;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Producto() {
    }

}
*/
