package dao.Asociaciones;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class AsociacionesDAOTest {

    @Test
    void inventarioCategorias() {
        long expected = 6L;
        long real = new AsociacionesDAO().almacencantidad(1L);
        assertEquals(expected,real);
    }
}