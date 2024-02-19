package dao.Categorias;

import entidades.Categorias;

import java.util.List;

public interface CategoriasDAOInterface {

    Categorias crearCategoria(Categorias c);
    Categorias buscarPorId(Long id);
    List<Categorias> mostrarTodos();
}
