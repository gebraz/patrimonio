package com.lab.patrimonio.controller;

import com.lab.patrimonio.entidade.Investimento;
import com.lab.patrimonio.entidade.Usuario;
import com.lab.patrimonio.entidade.dto.InvestimentoDTO;
import com.lab.patrimonio.service.InvestimentoService;
import com.lab.patrimonio.service.exceptions.RegraNegocioRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investimento")
public class InvestimentoController {

    @Autowired
    InvestimentoService service;

    @PostMapping
    public ResponseEntity salvar(@RequestBody InvestimentoDTO dto) {
        try {
            Investimento inv = Investimento.builder()
                    .nome(dto.getNome())
                    .usuario(Usuario.builder().id(dto.getUsuario()).build())
                    .build();

            Investimento salvo = service.salvar(inv);
            return new ResponseEntity(salvo, HttpStatus.CREATED);
        }
        catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long idInvestimento,
                                    @RequestBody InvestimentoDTO dto) {
        try {
            Investimento inv = Investimento.builder()
                    .id(idInvestimento)
                    .nome(dto.getNome())
                    .usuario(Usuario.builder().id(dto.getUsuario()).build())
                    .build();
            Investimento salvo = service.atualizar(inv);
            return ResponseEntity.ok(salvo);
        }
        catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("{id}")
    public ResponseEntity remover(@PathVariable("id") Long idInvestimento) {
        try {
            Investimento inv = Investimento.builder().id(idInvestimento).build();
            service.remover(inv);
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
        catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obter")
    public ResponseEntity buscarInvestimento(
            @RequestParam(value="usuario", required=true) Long idUsuario,
            @RequestParam(value="nome", required=false) String nome){

        Investimento filtro = Investimento.builder()
                .nome(nome)
                .usuario(Usuario.builder().id(idUsuario).build())
                .build();
        List<Investimento> investimentos = service.buscar(filtro);

        return ResponseEntity.ok(investimentos);
    }

    @GetMapping("/saldo")
    public ResponseEntity obterSaldoInvestimento(@RequestParam("id") Long idInvestimneto) {
        Investimento inv=Investimento.builder().id(idInvestimneto).build();
        try {
            Double valor = service.obterValorTotal(inv);
            return ResponseEntity.ok(valor);
        }
        catch(RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
