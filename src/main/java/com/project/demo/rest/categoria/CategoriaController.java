package com.project.demo.rest.categoria;

import com.project.demo.logic.entity.categoria.Categoria;
import com.project.demo.logic.entity.categoria.CategoriaRepository;
import com.project.demo.logic.entity.http.GlobalResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;


    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> createCategoria(@RequestBody Categoria categoria, HttpServletRequest request) {
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return new GlobalResponseHandler().handleResponse("Categoria creada correctamente", savedCategoria,HttpStatus.CREATED,request);
    }


    @GetMapping
    public List<?> getAllCategoria() {
        return categoriaRepository.findAll();
    }


    @PutMapping("/{categoriaId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateCategoria(@PathVariable Long categoriaId, @RequestBody Categoria categoria,HttpServletRequest request) {
        Optional<Categoria> foundCategoria = categoriaRepository.findById(categoriaId);
        if (foundCategoria.isPresent()) {
            categoria.setId(foundCategoria.get().getId());
            categoriaRepository.save(categoria);
            return new GlobalResponseHandler().handleResponse("Categoria actualizada correctamente", categoria,HttpStatus.OK,request);
        }else{
            return new GlobalResponseHandler().handleResponse("Categoria no encontrada",categoria,HttpStatus.NOT_FOUND,request);
        }
    }

    @DeleteMapping("/{categoriaId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteCategoria(@PathVariable Long categoriaId, HttpServletRequest request) {
        Optional<Categoria> foundCategoria = categoriaRepository.findById(categoriaId);

        if (foundCategoria.isPresent()) {
            Categoria categoria=foundCategoria.get();
            categoriaRepository.deleteById(categoriaId);

            return new GlobalResponseHandler().handleResponse("Categoria eliminada correctamente", categoria,HttpStatus.OK,request);
        }else{
            return new GlobalResponseHandler().handleResponse("Categoria no encontrada",null,HttpStatus.NOT_FOUND,request);
        }


    }



}
