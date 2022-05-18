package com.lab.patrimonio.entidade.dto;

import com.lab.patrimonio.entidade.Investimento;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvestimentoSaldo {
    Investimento inv;
    Double valor;
}
