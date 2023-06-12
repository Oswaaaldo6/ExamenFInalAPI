/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.examenfinal.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.uv.examenfinal.entidades.Alumno;
import org.uv.examenfinal.repositorios.AlumnoRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoRepository alumnoRepository;

    @Autowired
    public AlumnoController(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Alumno>> getAllAlumnos() {
        List<Alumno> alumnos = alumnoRepository.findAll();
        return new ResponseEntity<>(alumnos, HttpStatus.OK);
    }

    @GetMapping("/{clave}")
    public ResponseEntity<Alumno> getAlumno(@PathVariable String clave) {
        Optional<Alumno> alumno = alumnoRepository.findById(clave);
        return alumno.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Alumno> createAlumno(@RequestBody Alumno alumno) {
        Alumno createdAlumno = alumnoRepository.save(alumno);
        return new ResponseEntity<>(createdAlumno, HttpStatus.CREATED);
    }

    @PutMapping("/{clave}")
    public ResponseEntity<Alumno> updateAlumno(@PathVariable String clave, @RequestBody Alumno alumno) {
        if (!alumnoRepository.existsById(clave)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        alumno.setClave(clave);
        Alumno updatedAlumno = alumnoRepository.save(alumno);
        return new ResponseEntity<>(updatedAlumno, HttpStatus.OK);
    }

    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> deleteAlumno(@PathVariable String clave) {
        if (!alumnoRepository.existsById(clave)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        alumnoRepository.deleteById(clave);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
