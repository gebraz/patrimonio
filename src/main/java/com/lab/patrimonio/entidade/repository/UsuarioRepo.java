package com.lab.patrimonio.entidade.repository;

import com.lab.patrimonio.entidade.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepo 
    extends JpaRepository<Usuario, Long>{
    
        Usuario findByEmail(String email);
        boolean existsByEmail(String email);

        @Query("select u from Usuario u where u.nome=:nomeUsuario")
        Usuario obterUsuarioPorNome(
            @Param("nomeUsuario") String nomeUsuario);
}
