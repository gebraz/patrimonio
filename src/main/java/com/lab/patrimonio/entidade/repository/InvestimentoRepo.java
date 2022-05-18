package com.lab.patrimonio.entidade.repository;

import com.lab.patrimonio.entidade.Investimento;
import com.lab.patrimonio.entidade.Posicao;
import com.lab.patrimonio.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InvestimentoRepo
    extends JpaRepository<Investimento, Long> {

    public List<Investimento> findByUsuario(Usuario usuario);

    @Query("select sum(p.valor) " +
            "from Posicao p join p.investimento i "+
            "where p.investimento = :investimento ")
    Double obterSaldoInvestimento(@Param("investimento") Investimento inv);

    @Query("select p " +
            "from Posicao p join p.investimento i "+
            "where p.investimento = :investimento ")
    List<Posicao> obterPosicoes(@Param("investimento") Investimento inv);
}
