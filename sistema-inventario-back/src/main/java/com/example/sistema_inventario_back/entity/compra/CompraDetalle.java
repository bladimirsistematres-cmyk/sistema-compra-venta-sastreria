    package com.example.sistema_inventario_back.entity.compra;

    import com.example.sistema_inventario_back.entity.proveedor.UnidadCompra;
    import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class CompraDetalle {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idCompraDetalle;

        @NonNull
        private Double cantidadUnidadCompra;

        @NonNull
        private Double cantidadUnidadMedida;

        @NonNull
        @Enumerated(EnumType.STRING)
        private UnidadCompra unidadCompra;

        @NonNull
        @Enumerated(EnumType.STRING)
        private UnidadMedida unidadMedida;

        @OneToOne
        @JoinColumn(name = "id_compra", nullable = false)
        private Compra compra;

        @ManyToOne(optional = false)
        @JoinColumn(name = "id_materia_prima", nullable = false)
        private MateriaPrima materiaPrima;
    }