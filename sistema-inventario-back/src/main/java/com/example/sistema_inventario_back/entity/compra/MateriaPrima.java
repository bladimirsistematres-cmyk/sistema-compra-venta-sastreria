package com.example.sistema_inventario_back.entity.compra;

import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMateriaPrima;

    @NonNull
    private String nombreMateriaPrima;

    private String marca;
    private String modelo;

    @NonNull
    private Double stockActual;

    @NonNull
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;

    @NonNull
    private String descripcion;

    @NonNull
    private LocalDateTime fechaCreacion;

    @NonNull
    private LocalDateTime fechaActualizacion;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @OneToMany(mappedBy = "materiaPrima", cascade = CascadeType.ALL)
    private List<CompraDetalle> compraDetalles;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @PrePersist
    public void prePersist(){
        fechaCreacion = fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        fechaActualizacion = LocalDateTime.now();
    }
}