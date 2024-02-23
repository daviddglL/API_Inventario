package dao.Asociaciones;


import entidades.Productos;
import entidades.Proveedores;

import java.util.ArrayList;

public interface AsociacionesDAOInterface {

    Proveedores obtenerProvedorProducto(Productos p);

    Double mediaPreciosProveedor(String proveedor);

    Long almacencantidad(Long almacen);
    ArrayList<Productos> obtenerProductosProveedor(Proveedores p);
}
