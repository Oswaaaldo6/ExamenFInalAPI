/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.examenfinal.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.uv.examenfinal.entidades.Materia;
import org.uv.examenfinal.repositorios.MateriaRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/materias")
public class MateriaController {

    private final MateriaRepository materiaRepository;

    @Autowired
    public MateriaController(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Materia>> getAllMaterias() {
        List<Materia> materias = materiaRepository.findAll();
        return new ResponseEntity<>(materias, HttpStatus.OK);
    }

    @GetMapping("/{clave}")
    public ResponseEntity<Materia> getMateria(@PathVariable String clave) {
        Optional<Materia> materia = materiaRepository.findById(clave);
        return materia.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Materia> createMateria(@RequestBody Materia materia) {
        Materia createdMateria = materiaRepository.save(materia);
        return new ResponseEntity<>(createdMateria, HttpStatus.CREATED);
    }

    @PutMapping("/{clave}")
    public ResponseEntity<Materia> updateMateria(@PathVariable String clave, @RequestBody Materia materia) {
        if (!materiaRepository.existsById(clave)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        materia.setClave(clave);
        Materia updatedMateria = materiaRepository.save(materia);
        return new ResponseEntity<>(updatedMateria, HttpStatus.OK);
    }

    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> deleteMateria(@PathVariable String clave) {
        if (!materiaRepository.existsById(clave)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        materiaRepository.deleteById(clave);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}