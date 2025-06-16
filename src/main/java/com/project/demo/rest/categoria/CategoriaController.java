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
       if (categoria.getNombre() !=null && !categoria.getNombre().trim().isEmpty() && categoria.getDescripcion() !=null && !categoria.getDescripcion().trim().isEmpty()){
           Categoria savedCategoria = categoriaRepository.save(categoria);
           return new GlobalResponseHandler().handleResponse("Categoria creada correctamente", categoria,HttpStatus.CREATED,request);
       }else{
           return new GlobalResponseHandler().handleResponse("el nombre y descripcion son requeridos", categoria,HttpStatus.NOT_ACCEPTABLE,request);
       }

    }

    @GetMapping
    public ResponseEntity<?> getAllCategoria(HttpServletRequest request) {
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()){
            return new GlobalResponseHandler().handleResponse("No se encontraron resultados de categorias",null,HttpStatus.NOT_FOUND,request);
        }
        return new GlobalResponseHandler().handleResponse("Lista con todos las categorias registradas",categorias,HttpStatus.OK,request);
    }


    @PutMapping("/{categoriaId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateCategoria(@PathVariable Long categoriaId, @RequestBody Categoria categoria,HttpServletRequest request) {
        Optional<Categoria> foundCategoria = categoriaRepository.findById(categoriaId);
        if (foundCategoria.isPresent()) {
            //si estos campos no se pasan van a permanecer con el mismo valor que tenian
            if (categoria.getNombre()==null || categoria.getNombre().trim().isEmpty()) categoria.setNombre(foundCategoria.get().getNombre());
            if (categoria.getDescripcion()==null || categoria.getDescripcion().trim().isEmpty()) categoria.setDescripcion(foundCategoria.get().getDescripcion());

            categoria.setId(foundCategoria.get().getId());
            categoriaRepository.save(categoria);
            return new GlobalResponseHandler().handleResponse("Categoria actualizada correctamente", categoria,HttpStatus.OK,request);
        }else{
            return new GlobalResponseHandler().handleResponse("La Categoria indicada no existe",categoria,HttpStatus.NOT_FOUND,request);
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
