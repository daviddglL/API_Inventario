package dao.Proveedores;

import entidades.Proveedores;

public interface ProveedoresDAOInterface {
    Proveedores crearProveedor(Proveedores p);
    Proveedores buscarPorId(Long id);
}
