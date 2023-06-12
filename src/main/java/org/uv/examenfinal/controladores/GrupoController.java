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
import org.uv.examenfinal.entidades.Grupo;
import org.uv.examenfinal.entidades.Materia;
import org.uv.examenfinal.repositorios.AlumnoRepository;
import org.uv.examenfinal.repositorios.GrupoRepository;
import org.uv.examenfinal.repositorios.MateriaRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoRepository grupoRepository;
    private final AlumnoRepository alumnoRepository;
    private final MateriaRepository materiaRepository;

    @Autowired
    public GrupoController(GrupoRepository grupoRepository, AlumnoRepository alumnoRepository, MateriaRepository materiaRepository) {
        this.grupoRepository = grupoRepository;
        this.alumnoRepository = alumnoRepository;
        this.materiaRepository = materiaRepository;
    }

    @GetMapping
    public ResponseEntity<List<Grupo>> getAllGrupos() {
        List<Grupo> grupos = grupoRepository.findAll();
        return new ResponseEntity<>(grupos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupo> getGrupo(@PathVariable long id) {
        Optional<Grupo> grupo = grupoRepository.findById(id);
        return grupo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Grupo> createGrupo(@RequestBody Grupo grupo) {
        Grupo createdGrupo = grupoRepository.save(grupo);
        return new ResponseEntity<>(createdGrupo, HttpStatus.CREATED);
    }
    
    @PostMapping("/{grupoId}/alumnos/{alumnoId}")
    public ResponseEntity<Grupo> addAlumnoToGrupo(@PathVariable long grupoId, @PathVariable String alumnoId) {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        Optional<Alumno> alumnoOptional = alumnoRepository.findById(alumnoId);

        if (grupoOptional.isPresent() && alumnoOptional.isPresent()) {
            Grupo grupo = grupoOptional.get();
            Alumno alumno = alumnoOptional.get();

            grupo.getAlumnos().add(alumno);
            Grupo groupUpdated = grupoRepository.save(grupo);

            return new ResponseEntity<>(groupUpdated, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/{grupoId}/materias/{materiaId}")
    public ResponseEntity<Grupo> addMateriaToGrupo(@PathVariable long grupoId, @PathVariable String materiaId) {
        Optional<Grupo> grupoOptional = grupoRepository.findById(grupoId);
        Optional<Materia> materiaOptional = materiaRepository.findById(materiaId);

        if (grupoOptional.isPresent() && materiaOptional.isPresent()) {
            Grupo grupo = grupoOptional.get();
            Materia materia = materiaOptional.get();

            grupo.getMaterias().add(materia);
            Grupo groupUpdated = grupoRepository.save(grupo);

            return new ResponseEntity<>(groupUpdated, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Grupo> updateGrupo(@PathVariable long id, @RequestBody Grupo grupo) {
        if (!grupoRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        grupo.setId(id);
        Grupo updatedGrupo = grupoRepository.save(grupo);
        return new ResponseEntity<>(updatedGrupo, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrupo(@PathVariable long id) {
        if (!grupoRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        grupoRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}