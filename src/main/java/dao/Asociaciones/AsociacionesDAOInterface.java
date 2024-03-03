package dao.Asociaciones;


import entidades.Almacen;
import entidades.Productos;
import entidades.Proveedores;

import java.util.ArrayList;
import java.util.List;

public interface AsociacionesDAOInterface {

    Proveedores obtenerProvedorProducto(Productos p);

    Double mediaPreciosProveedor(String proveedor);

    Long almacencantidad(Long almacen);
    List<Productos> obtenerProductosProveedor(Proveedores p);

    List<Productos> almacenesconProductos(Almacen c);

    List<Almacen> almacendelproducto(Productos p);
    boolean nuevoProductoAlmacen(Productos p, Almacen a);
}
