package com.example.sistema_inventario_back.service.config_service;

import com.example.sistema_inventario_back.dto.usuario.UsuarioUpdateDTO;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import com.example.sistema_inventario_back.repository.usuario.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Servicio para listar a todos los usuarios del sistema.
    public Iterable<Usuario> listarUsuarios(){
        return userRepository.findAll();
    }

    // Servicio para actualizar campos del usuario
    public Usuario updateUsuario(Integer id, UsuarioUpdateDTO dto){
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombres(dto.getNombres());
        usuario.setApellidoPaterno(dto.getApellidoPaterno());
        usuario.setApellidoMaterno(dto.getApellidoMaterno());
        usuario.setCarnetIdentidad(dto.getCarnetIdentidad());

        return userRepository.save(usuario);
    }

    // Servicio para cambiar el nombre de usuario
    public Usuario actualizarNombreUsuario(Integer id, String nuevoNombre){
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreUsuario(nuevoNombre);
        return userRepository.save(usuario);
    }

    // Servicio para cambiar la contraseña
    public Usuario actualizarPassword(Integer id, String actualPassword, String nuevoPassword){
        Usuario usuario = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(actualPassword, usuario.getPassword())){
            throw new RuntimeException("Contraseña actual incorrecta");
        }

        usuario.setPassword(passwordEncoder.encode(nuevoPassword));
        return userRepository.save(usuario);
    }
}
