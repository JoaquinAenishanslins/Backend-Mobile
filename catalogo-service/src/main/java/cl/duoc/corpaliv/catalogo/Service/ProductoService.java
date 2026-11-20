package cl.duoc.corpaliv.catalogo.Service;

import cl.duoc.corpaliv.catalogo.Model.ProductoModel;
import cl.duoc.corpaliv.catalogo.Repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoModel> listarTodos() {
        return productoRepository.findAll();
    }
    public List<ProductoModel> listarActivos() {
        return productoRepository.findByActivoTrue();
    }
    public Optional<ProductoModel> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }
    public ProductoModel guardar(ProductoModel producto) {
        return productoRepository.save(producto);
    }
    public Optional<ProductoModel> actualizar(Long id, ProductoModel actualizado) {
        return productoRepository.findById(id).map(existente -> {
            existente.setNombreProducto(actualizado.getNombreProducto());
            existente.setDescripcionProducto(actualizado.getDescripcionProducto());
            existente.setPrecioProducto(actualizado.getPrecioProducto());
            existente.setStockProducto(actualizado.getStockProducto());
            existente.setCategoriaProducto(actualizado.getCategoriaProducto());
            existente.setActivo(actualizado.isActivo());
            return productoRepository.save(existente);
        });
    }
    
    public boolean eliminar(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
