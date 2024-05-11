package com.example.buensaboruno.repositories;

import com.example.buensaboruno.domain.entities.ArticuloInsumo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloInsumoRepository extends BaseRepository<ArticuloInsumo,Long> {
    @Query(value = "SELECT ARTICULO_INSUMO.ID, *\n" +
            "FROM ARTICULO_INSUMO\n" +
            "INNER JOIN ARTICULO\n" +
            "ON ARTICULO_INSUMO.ID = ARTICULO.ID\n" +
            "WHERE ARTICULO_INSUMO.ES_PARA_ELABORAR = TRUE;", nativeQuery = true)
    public List<ArticuloInsumo> getElaborados();
}
