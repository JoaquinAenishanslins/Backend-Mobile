package cl.duoc.corpaliv.catalogo.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.corpaliv.catalogo.Model.ProductoModel;
import cl.duoc.corpaliv.catalogo.Service.ProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService service;
    public ProductoController(ProductoService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<List<ProductoModel>> listar(@RequestParam(required = false, defaultValue = "false") boolean soloActivos) {
        if (soloActivos) {
            return ResponseEntity.ok(service.listarActivos());
        }
        return ResponseEntity.ok(service.listarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductoModel> obtenerPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<ProductoModel> crear(@RequestBody ProductoModel producto) {
        ProductoModel guardado = service.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductoModel> actualizar(@PathVariable Long id, @RequestBody ProductoModel producto) {
        return service.actualizar(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
