package dao.Asociaciones;

import dto.ProductosDTO;
import entidades.Categorias;
import entidades.Productos;
import entidades.Proveedores;

import java.time.LocalDate;
import java.util.List;

public interface AsociacionesDAOInterface {

    Proveedores obtenerProvedorProducto(Productos p);

    Double mediaPreciosProveedor(String proveedor);
    List<Categorias> productoCategorias(Long idProducto);
}
