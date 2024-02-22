package dao.Asociaciones;


import entidades.Productos;
import entidades.Proveedores;

import java.util.List;

public interface AsociacionesDAOInterface {

    Proveedores obtenerProvedorProducto(Productos p);

    Double mediaPreciosProveedor(String proveedor);

}
