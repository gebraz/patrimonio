package com.lab.patrimonio.controller;

import com.lab.patrimonio.entidade.Usuario;
import com.lab.patrimonio.entidade.dto.InvestimentoSaldo;
import com.lab.patrimonio.entidade.dto.UsuarioDTO;
import com.lab.patrimonio.service.UsuarioService;
import com.lab.patrimonio.service.exceptions.RegraNegocioRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping
    public ResponseEntity salvar (@RequestBody UsuarioDTO request) {
        Usuario usuario = Usuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .build();
        try {
            Usuario salvo = service.salvar(usuario);
            return new ResponseEntity(salvo, HttpStatus.CREATED);
        }catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody UsuarioDTO request) {

        try {
            Boolean valor = service.efetuarLogin(request.getEmail(), request.getSenha());
            return new ResponseEntity(valor, HttpStatus.OK);
        } catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/saldos")
    public ResponseEntity obterSaldos(
            @RequestParam(value = "usuario", required = true) Long idUsuario)
    {
        try {
            List<InvestimentoSaldo> invs =
                    service.obterSaldos(Usuario.builder().id(idUsuario).build());
            return new ResponseEntity(invs, HttpStatus.OK);
        }catch (RegraNegocioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }



}
