package com.example.sistema_inventario_back.entity.proveedor;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Representante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRepresentante;

    @NonNull
    private String nombre;

    @NonNull
    private String cargo;

    @NonNull
    private String cedulaIdentidad;

    @NonNull
    @Enumerated(EnumType.STRING)
    private GeneroRepresentante generoRepresentante;

    @Column(nullable = true)
    private String telefonoFijo;

    @NonNull
    private String telefonoCelular;

    @Column(nullable = true)
    private String correoElectronico;

    @Column(nullable = true)
    private String direccion;

    @NonNull
    private LocalDateTime fechaCreacion;

    @NonNull
    private LocalDateTime fechaActualizacion;

    @Column(nullable = true)
    private String observaciones;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @PrePersist
    public void prePersist(){
        fechaCreacion = fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        fechaActualizacion = LocalDateTime.now();
    }
}