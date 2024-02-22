package dao.Almacen;

import entidades.Almacen;

import java.time.LocalDate;
import java.util.List;

public interface AlmacenDAOInterface {

    List<Almacen> mostrarTodos();
    List<Almacen> menormayorFecha();

    List<Almacen> mayormenorFecha();

    List<Almacen> buscarporFechas(LocalDate min_fech, LocalDate max_fech);
}
