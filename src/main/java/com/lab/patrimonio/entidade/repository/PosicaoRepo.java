package com.lab.patrimonio.entidade.repository;

import com.lab.patrimonio.entidade.Investimento;
import com.lab.patrimonio.entidade.Posicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosicaoRepo
    extends JpaRepository<Posicao, Long> {

    public List<Posicao> findByInvestimento(Investimento investimento);
}
