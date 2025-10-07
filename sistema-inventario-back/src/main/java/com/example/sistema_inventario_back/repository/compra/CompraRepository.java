package com.example.sistema_inventario_back.repository.compra;

import com.example.sistema_inventario_back.dto.compra.CompraListarDTO;
import com.example.sistema_inventario_back.entity.compra.Compra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Integer>, JpaSpecificationExecutor<Compra> {

    @Query("""
            SELECT new com.example.sistema_inventario_back.dto.compra.CompraListarDTO(
                c.idCompra,
                c.totalCompra,
                c.proveedor.nombreComercial,
                cd.materiaPrima.nombreMateriaPrima,
                cd.materiaPrima.unidadMedida,
                cd.cantidadUnidadMedida
            )
            FROM Compra c
            JOIN c.compraDetalle cd
            """)
    Page<CompraListarDTO> findAllCompra(Pageable pageable);

    List<Compra> findByProveedor_IdProveedor(Integer idProveedor);

    @Query("SELECT c FROM Compra c JOIN c.compraDetalle d WHERE d.materiaPrima.idMateriaPrima = :idMateriaPrima")
    List<Compra> findByMateriaPrima(@Param("idMateriaPrima") Integer idMateriaPrima);
}