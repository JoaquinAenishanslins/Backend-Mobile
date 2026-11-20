package cl.duoc.corpaliv.catalogo.Repository;

import cl.duoc.corpaliv.catalogo.Model.ProductoModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoModel, Long> {
    List<ProductoModel> findByActivoTrue();
}
