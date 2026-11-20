package cl.duoc.corpaliv.catalogo.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos_catalogo")
public class ProductoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productoId;

    @Column(nullable = false)
    private String nombreProducto;

    @Column(nullable = false)
    private String descripcionProducto;

    @Column(nullable = false)
    private double precioProducto;

    @Column(nullable = false)
    private int stockProducto;

    @Column(nullable = false)
    private String categoriaProducto;

    @Column(nullable = false)
    private boolean activo = true;

}
