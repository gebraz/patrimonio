package com.lab.patrimonio.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posicao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Posicao {
    @Id
    @Column(name="id_posicao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="valor")
    private Double valor;
    @Temporal(TemporalType.DATE)    
    @Column(name = "data_cadastro")       
    private Date data; 
    @ManyToOne
    @JoinColumn(name="investimento_id")
    private Investimento investimento;
}