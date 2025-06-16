package com.project.demo.rest.producto;

import com.project.demo.logic.entity.categoria.Categoria;
import com.project.demo.logic.entity.categoria.CategoriaRepository;
import com.project.demo.logic.entity.http.GlobalResponseHandler;
import com.project.demo.logic.entity.producto.Producto;
import com.project.demo.logic.entity.producto.ProductoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> createCategoria(@RequestBody Producto prod, HttpServletRequest request) {
        Optional<Categoria> foundCategoria = categoriaRepository.findById(prod.getCategoria().getId());
        if (foundCategoria.isPresent()) {
            //validacion para que los campo nombre, precio y stock sean obligatorios
            if (prod.getNombre()!=null && !prod.getNombre().trim().isEmpty() && prod.getPrecio()>0 &&  prod.getCantidadStock()>=0){
                Producto savedProducto= productoRepository.save(prod);
                return new GlobalResponseHandler().handleResponse("Producto creado correctamente", savedProducto,HttpStatus.CREATED,request);
            }else{
                return new GlobalResponseHandler().handleResponse("Revise que los campos: nombre este completado, el precio no puede ser menor a 0 y stock debe ser mayor o igual a 0",prod,HttpStatus.NOT_ACCEPTABLE,request);
            }
        }else{
            return new GlobalResponseHandler().handleResponse("La Categoria asignada al producto no existe",prod,HttpStatus.NOT_FOUND,request);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducto(HttpServletRequest request) {
        List<Producto> producto = productoRepository.findAll();

        if (producto.isEmpty()) {
            return new GlobalResponseHandler().handleResponse("No se encontraron resultados de productos",null,HttpStatus.NOT_FOUND,request);
        }
            return new GlobalResponseHandler().handleResponse("Lista con todos los productos registrados",producto,HttpStatus.OK,request);
    }

    @PutMapping("/{productoId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> updateProducto(@PathVariable Long productoId, @RequestBody Producto prod, HttpServletRequest request) {

        Optional<Producto> foundProducto = productoRepository.findById(productoId);
        if (foundProducto.isPresent()) {

            //si estos campos no se pasan van a permanecer con el mismo valor que tenian
            if(prod.getNombre()==null || prod.getNombre().trim().isEmpty()) prod.setNombre(foundProducto.get().getNombre());
            if(prod.getDescripcion()==null || prod.getDescripcion().trim().isEmpty()) prod.setDescripcion(foundProducto.get().getDescripcion());
            if(prod.getCategoria() == null || prod.getCategoria().getId() == null)
            { //setea la categoria que tiene originalmente
                prod.setCategoria(foundProducto.get().getCategoria());
            }else {
                Optional<Categoria> foundCategoria = categoriaRepository.findById(prod.getCategoria().getId());
                if (foundCategoria.isPresent()) {
                    prod.setCategoria(foundCategoria.get());
                }else{
                    return new GlobalResponseHandler().handleResponse("La Categoria asignada al producto no existe",prod,HttpStatus.NOT_FOUND,request);
                }

            }

            //validacion de posbles numeros negativos o con ceros para el precio y stock
            if (prod.getPrecio()>0 && prod.getCantidadStock()>=0){
                prod.setId(foundProducto.get().getId());
                productoRepository.save(prod);
                return new GlobalResponseHandler().handleResponse("Producto actualizado correctamente",prod,HttpStatus.OK,request);
            }else{
                return new GlobalResponseHandler().handleResponse("El precio no puede ser menor a cero o stock debe ser mayor o igual a 0",prod,HttpStatus.NOT_ACCEPTABLE,request);
            }

        }else{
            return new GlobalResponseHandler().handleResponse("El producto indicado no existe",prod,HttpStatus.NOT_FOUND,request);

        }

    }

    @DeleteMapping("/{productoId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public ResponseEntity<?> deleteProducto(@PathVariable Long productoId, HttpServletRequest request) {
        Optional<Producto> foundProducto = productoRepository.findById(productoId);
        if (foundProducto.isPresent()) {
            Producto producto = foundProducto.get();
            productoRepository.delete(producto);
            return new GlobalResponseHandler().handleResponse("Producto eliminado correctamente",producto,HttpStatus.OK,request);
        }else{
            return new GlobalResponseHandler().handleResponse("Producto no encontrado",null,HttpStatus.NOT_FOUND,request);
        }

    }
}
