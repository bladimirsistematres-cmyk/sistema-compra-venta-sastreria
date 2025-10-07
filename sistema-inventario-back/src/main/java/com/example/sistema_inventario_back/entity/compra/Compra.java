package com.example.sistema_inventario_back.entity.compra;

import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCompra;

    @NonNull
    private Double totalCompra;

    private String notaEdicion;

    @NonNull
    private LocalDateTime fechaCompra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @OneToOne(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompraDetalle compraDetalle;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @PrePersist
    public void prePersist(){
        fechaCompra = LocalDateTime.now();
    }
}