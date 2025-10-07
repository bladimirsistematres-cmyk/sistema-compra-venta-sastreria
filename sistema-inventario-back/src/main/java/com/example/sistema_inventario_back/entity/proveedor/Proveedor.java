package com.example.sistema_inventario_back.entity.proveedor;

import com.example.sistema_inventario_back.entity.compra.MateriaPrima;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProveedor;

    @NonNull
    private String nombreComercial;

    @NonNull
    private String identificacionFiscal;

    @Column(nullable = true)
    private String correoElectronico;

    @NonNull
    private String direccion;

    @Column(nullable = true)
    private String enlacePagina;

    @NonNull
    private LocalDateTime fechaCreacion;

    @NonNull
    private LocalDateTime fechaActualizacion;

    @NonNull
    private TipoProveedor tipoProveedor;

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Representante> representantes = new ArrayList<>();

    @OneToMany(mappedBy = "proveedor")
    private List<MateriaPrima> materiaPrimas;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_ciudad", nullable = false)
    private Ciudad ciudad;

    @PrePersist
    public void prePersist(){
        fechaCreacion = fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        fechaActualizacion = LocalDateTime.now();
    }
}
