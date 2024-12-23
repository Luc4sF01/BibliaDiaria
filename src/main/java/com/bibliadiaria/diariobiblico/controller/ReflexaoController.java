package com.bibliadiaria.diariobiblico.controller;

import com.bibliadiaria.diariobiblico.model.Reflexao;
import com.bibliadiaria.diariobiblico.repository.ReflexaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/reflexoes")
public class ReflexaoController {

    @Autowired
    private ReflexaoRepository repository;

    // ✅ Listar todas as reflexões
    @GetMapping
    public String listarReflexoes(Model model) {
        List<Reflexao> reflexoes = repository.findAll();
        model.addAttribute("reflexoes", reflexoes);
        return "listar";
    }

    // ✅ Exibir formulário para nova reflexão
    @GetMapping("/nova")
    public String novaReflexao(Model model) {
        model.addAttribute("reflexao", new Reflexao());
        return "nova";
    }

    // ✅ Salvar nova reflexão
    @PostMapping
    public String salvarReflexao(@ModelAttribute Reflexao reflexao) {
        if (reflexao.getData() == null) {
            reflexao.setData(LocalDate.now());
        }
        repository.save(reflexao);
        return "redirect:/reflexoes";
    }

    // ✅ Mostrar formulário de edição
    @GetMapping("/editar/{id}")
    public String editarReflexao(@PathVariable Long id, Model model) {
        Reflexao reflexao = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reflexão inválida: " + id));
        model.addAttribute("reflexao", reflexao);
        return "editar";
    }

    // ✅ Atualizar reflexão
    @PostMapping("/atualizar/{id}")
    public String atualizarReflexao(@PathVariable Long id, @ModelAttribute Reflexao reflexaoAtualizada) {
        Reflexao reflexao = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reflexão inválida: " + id));

        reflexao.setTitulo(reflexaoAtualizada.getTitulo());
        reflexao.setTexto(reflexaoAtualizada.getTexto());
        reflexao.setAutor(reflexaoAtualizada.getAutor());
        reflexao.setData(reflexaoAtualizada.getData());

        repository.save(reflexao);
        return "redirect:/reflexoes";
    }

    // ✅ Excluir reflexão
    @PostMapping("/excluir/{id}")
    public String excluirReflexao(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/reflexoes";
    }
}
