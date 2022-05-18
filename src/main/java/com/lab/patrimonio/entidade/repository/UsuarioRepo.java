package com.lab.patrimonio.entidade.repository;

import com.lab.patrimonio.entidade.Usuario;

import com.lab.patrimonio.entidade.dto.InvestimentoSaldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepo 
    extends JpaRepository<Usuario, Long>{

        boolean existsByEmail(String email);
        Optional<Usuario> findByEmail(String email);
        Optional<Usuario> findByNome(String nome);


        @Query("select new com.lab.patrimonio.entidade.dto.InvestimentoSaldo(i, sum(p.valor)) " +
                "from Posicao p join p.investimento i "+
                "where i.usuario = :usuario " +
                "group by i")
        List<InvestimentoSaldo> obterSaldosInvestimentos(@Param("usuario") Usuario usuario);
}
