package dao.Productos;

import dto.ProductosDTO;
import entidades.Productos;

import java.time.LocalDate;
import java.util.List;

public interface ProductosDAOInterface {
    List<Productos> mostrarTodos();

    List<Productos>resumenPaginado(int pagina,int objetos_por_pagina);

    Long totalMuebles();

    List<ProductosDTO> devolverNombreImagenes();

    List<Productos> buscarporNombre(String busqueda);

    List<Productos> buscarEntrePrecios(Double min, Double max);

    List<Productos> buscarEntrePreciosProveedor(Double min, Double max, String proveedor);
    List<Productos> buscarEntrePreciosCategorias(Double min, Double max, List<String> categorias);


    List<String> devolverTodasImages();


    Productos buscarPorId(Long id);


    Double mediaPrecios ();

    List<Productos> mayormenorPrecios();

    List<Productos> menormayorPrecios();

    Productos create(Productos productos);

    Productos update(Productos productos);
    Long totalProductos();

    boolean deleteById(Long id);

    boolean deleteAll();



}
