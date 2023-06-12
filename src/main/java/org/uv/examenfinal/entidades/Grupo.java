/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uv.examenfinal.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "grupos")
public class Grupo {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name = "nombre")
    private String nombre;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "alumnos", referencedColumnName = "id")
    List <Alumno> alumnos = new ArrayList <> ();
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "alumnos", referencedColumnName = "id")
    List <Materia> materias = new ArrayList <> ();

    public Grupo() {}

    public Grupo(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List <Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List <Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
    
}
