import dao.Asociaciones.AsociacionesDAO;
import dao.Asociaciones.AsociacionesDAOInterface;
import dao.Inventario.InventarioDAO;
import dao.Inventario.InventarioDAOInterface;
import dao.Productos.ProductosDAO;
import dao.Productos.ProductosDAOInterface;
import dao.Proveedores.ProveedoresDAO;
import dao.Proveedores.ProveedoresDAOInterface;
import servicios.ProductosAPIREST;

public class Servidor {

    public static void main (String[] args) throws Exception {

        ProductosAPIREST api=new ProductosAPIREST(new AsociacionesDAO(),new InventarioDAO(),new ProveedoresDAO(),new ProductosDAO());
    }
}
