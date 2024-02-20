package servicios;

import com.appslandia.common.gson.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.Asociaciones.AsociacionesDAOInterface;
import dao.Categorias.CategoriasDAOInterface;
import dao.Inventario.InventarioDAOInterface;
import dao.Productos.ProductosDAOInterface;
import dao.Proveedores.ProveedoresDAOInterface;
import dto.ProductosDTO;
import entidades.Inventarios;
import entidades.Productos;
import entidades.Proveedores;
import spark.Spark;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class ProductosAPIREST {

    private AsociacionesDAOInterface dao_asoc;
    private CategoriasDAOInterface dao_cat;
    private InventarioDAOInterface dao_inv;
    private ProductosDAOInterface dao_prod;
    private ProveedoresDAOInterface dao_prov;

    private Gson gson= new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .excludeFieldsWithoutExposeAnnotation().create();

    public ProductosAPIREST(AsociacionesDAOInterface imple_asoc, InventarioDAOInterface imple_inv, CategoriasDAOInterface imple_cat, ProveedoresDAOInterface imple_prov, ProductosDAOInterface imple_prod) throws Exception {
        Spark.port(8080);
        dao_asoc =imple_asoc;
        dao_cat=imple_cat;
        dao_inv=imple_inv;
        dao_prod=imple_prod;
        dao_prov=imple_prov;


        /**
         * Consultas de la clase productos
         */


//        Mostrar todos los productos
        Spark.get("/productos",((request, response) -> {
            response.type("appication/json");
            List<Productos> productos=dao_prod.mostrarTodos();
            if (productos!=null){
                return gson.toJson(productos);
            }else {
                response.status(404);
                return "Producto no encontrado";
            }
        }));

        //productos por id
        Spark.get("/productos/id_producto/:id",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Productos productos = dao_prod.buscarPorId(id);
            if (productos!=null){
                return gson.toJson(productos);
            }else {
                response.status(404);
                return "Producto no encontrado";
            }
        });

        //buscarporNombre
        Spark.get("/productos/buscar/:nombre",(request, response) -> {
            String nombre =request.params(":nombre");
            List<Productos> productos = dao_prod.buscarporNombre(nombre);
            if (productos!=null){
                return gson.toJson(productos);
            }else {
                response.status(404);
                return "Producto no encontrado";
            }
        });

        /*
        PRECIOS
         */

        //buscarEntrePrecios

        Spark.get("/productos/buscar/:min/:max", (request, response) -> {
            Double min = Double.parseDouble(request.params(":min"));
            Double max = Double.parseDouble(request.params(":max"));
            List<Productos> productos = dao_prod.buscarEntrePrecios(min, max);
            if (productos!=null){
                return gson.toJson(productos);
            }else {
                response.status(404);
                return "Producto no encontrado";
            }
        });

        //buscarEntrePreciosProveedor
        Spark.get("/productos/buscar/:min/:max/:proveedor", (request, response) -> {
            Double min = Double.parseDouble(request.params(":min"));
            Double max = Double.parseDouble(request.params(":max"));
            String proveedor = request.params(":proveedor");
            List<Productos> productos = dao_prod.buscarEntrePreciosProveedor(min, max, proveedor);
            if (productos!=null){
                return gson.toJson(productos);
            }else {
                response.status(404);
                return "Producto no encontrado";
            }
        });

        //buscarEntrePreciosCategoria
        Spark.get("/Productos/buscar_marcas/:min/:max/:listcategorias", (request, response) -> {
            Double min = Double.parseDouble(request.params(":min"));
            Double max = Double.parseDouble(request.params(":max"));
            String categoriasParap = request.params(":listcategorias");

            List<String> categorias = Arrays.asList(categoriasParap.split(","));
            System.out.println(categorias);


            List<Productos> productos = dao_prod.buscarEntrePreciosCategorias(min, max, categorias);
            if (productos!=null){
                return gson.toJson(productos);
            }else {
                response.status(404);
                return "Producto no encontrado";
            }
        });


        //Precio medio de los proveedores
        Spark.get("/productos/mediaprecios_proveedor/:proveedor", (request, response) -> {
            String proveedor = request.params(":proveedor");
            Double media = dao_asoc.mediaPreciosProveedor(proveedor);
            if (media!=null){
                return media.toString();
            }else {
                response.status(404);
                return "Precio no encontrado";
            }
        });

        //Precio medio total
        Spark.get("/Productos/mediaprecios", (request, response) -> {
            Double media = dao_prod.mediaPrecios();
            if (media!=null){
                return media.toString();
            }else {
                response.status(404);
                return "Precio no encontrado";
            }
        });

        //ordenar por precio de mayor a menor
        Spark.get("/Productos/may_men",(request, response) -> {
            response.type("application/json");
            List<Productos> orden=dao_prod.mayormenorPrecios();
            if (orden!=null){
                return gson.toJson(orden);
            }else {
                response.status(404);
                return "Productos no encontrado";
            }
        });

        //ordenar por precio de menor a mayor
        Spark.get("/Productos/men_may",(request, response) -> {
            response.type("application/json");
            List<Productos> orden=dao_prod.menormayorPrecios();
            if (orden!=null){
                return gson.toJson(orden);
            }else {
                response.status(404);
                return "Productos no encontrado";
            }
        });


        // Endpoint para obtener un resumen con solo el nombre el precio y la URL
        Spark.get("/Productos/resumenobjetos", (request, response) -> {
            List<ProductosDTO> resumen = dao_prod.devolverNombreImagenes();
            if (resumen!=null){
                return gson.toJson(resumen);
            }else {
                response.status(404);
                return "Producto no encontrado";
            }
        });


        //Crear un nuevo producto

        Spark.post("/Productos/registrar",((request, response) -> {
            String body = request.body();
            Productos newProd=gson.fromJson(body, Productos.class);

            Productos creado = dao_prod.create(newProd);
            if (creado!=null){
                return gson.toJson(creado);
            }else {
                response.status(404);
                return "Producto no introducido";
            }
        }));

        // Endpoint para actualizar un producto por su ID
        Spark.put("/Productos/update/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            String body = request.body();

            Productos prodActualizado = gson.fromJson(body, Productos.class);
            prodActualizado.setId_producto(id);
            Productos actualizado = dao_prod.update(prodActualizado);

            if (actualizado != null) {
                return gson.toJson(actualizado);
            } else {
                response.status(404);
                return "Producto no encontrado";
            }
        });

        // Endpoint para eliminar un producto por su ID
        Spark.delete("/Productos/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            boolean eliminado = dao_prod.deleteById(id);
            if (eliminado) {
                return "Producto eliminado correctamente";
            } else {
                response.status(404);
                return "Producto no encontrado";
            }
        });

        // Endopoint para eliminar todos los productos
        Spark.delete("/Productos/delete",(request, response) -> {
            boolean arrasar = dao_prod.deleteAll();
            if (arrasar){
                return "Se ha vaciado el inventario";
            }else{
                response.status(404);
                return "Inventario no vaciado";
            }
        });

        //En caso de intentar un endpoint incorrecto
        Spark.notFound((request, response) -> {
            response.type("application/json");
            return "{\"error\": \"Ruta no encontrada\",\"hint1\": \"/productos\"," +
                    "\"hint2\": \"/productos/paginado/:pagina/:tam_pagina\",\"hint3\": \"/productos/id/:id\"}";
        });



        //DEVOLVER DATOS DE MANERA PAGINADA DE TODOS LOS MUEBLES
        Spark.get("/Productos/paginado/:pagina/:tam_pagina", (request, response) -> {

            Integer pagina = Integer.parseInt(request.params("pagina"));
            Integer tamaño_pagina = Integer.parseInt(request.params("tam_pagina"));

            List<Productos> muebles = dao_prod.resumenPaginado(pagina, tamaño_pagina);

            Long totalElementos = dao_prod.totalMuebles(); // Obtener el total de muebles

            RespuestaPaginacion<Productos> paginaResultado = new RespuestaPaginacion<>(muebles, totalElementos, pagina, tamaño_pagina);

            if (paginaResultado!=null){
                return gson.toJson(paginaResultado);
            }else {
                response.status(404);
                return "Paginado no encontrado";
            }

        });


        /**
         * Consultas de la clase Inventarios
         */


        //Mostrar todos los inventarios
        Spark.get("/inventarios",((request, response) -> {
            response.type("appication/json");
            List<Inventarios> inventarios=dao_inv.mostrarTodos();
            if (inventarios!=null){
                return gson.toJson(inventarios);
            }else {
                response.status(404);
                return "Inventario no encontrado";
            }
        }));

        //ordenar por fechas de mayor a menor
        Spark.get("/inventarios/may_men_fech",(request, response) -> {
            response.type("application/json");
            List<Inventarios> orden=dao_inv.mayormenorFecha();
            if (orden!=null){
                return gson.toJson(orden);
            }else {
                response.status(404);
                return "Inventario no encontrado";
            }
        });

        //Buscar fechas antiguas a nuevas
        Spark.get("/inventarios/men_may_fech",(request, response) -> {
            response.type("application/json");
            List<Inventarios> orden=dao_inv.menormayorFecha();
            if (orden!=null){
                return gson.toJson(orden);
            }else {
                response.status(404);
                return "Inventario no encontrado";
            }
        });


        //buscarEntreFechas
        Spark.get("/inventarios/buscar_fechas/:min_fech/:max_fech", (request, response) -> {
            LocalDate min_fech = LocalDate.parse(request.params(":min_fech"));
            LocalDate max_fech = LocalDate.parse(request.params(":max_fech"));

            // Formatear las fechas al formato de la base de datos 'yyyy-MM-dd'
            String formattedMinFech = min_fech.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String formattedMaxFech = max_fech.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<Inventarios> inventarios = dao_inv.buscarporFechas(LocalDate.parse(formattedMinFech), LocalDate.parse(formattedMaxFech));
            if (inventarios!=null){
                return gson.toJson(inventarios);
            }else {
                response.status(404);
                return "Inventario no encontrado";
            }
        });





        /**
         * Consultas de la clase relaciones
         */

        // Endpoint para obtener proveedor mueble

        Spark.get("/productos/proveedor/:id", (request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Productos pr= dao_prod.buscarPorId(id);
            Proveedores p = dao_asoc.obtenerProvedorProducto(pr);
            response.type("application/json");
            return gson.toJson(p);
        });







        Spark.exception(Exception.class, (e, req, res) -> {
            e.printStackTrace(); // Imprime la excepción en la consola
            res.status(500); // Establece el código de estado HTTP 500
            res.body("Excepcion en tu codigo"); // Mensaje de error para el cliente
        });


    }




}
