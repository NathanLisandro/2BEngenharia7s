package com.gerenciadordetarefas.main.service;

import com.gerenciadordetarefas.main.model.Tarefa;
import com.gerenciadordetarefas.main.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public Tarefa criarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTodasTarefas() {
        return tarefaRepository.findAll();
    }

    public Optional<Tarefa> atualizarStatusTarefa(Long id, String newStatus) {
        return tarefaRepository.findById(id).map(tarefa -> {
            tarefa.setStatus(newStatus);
            return tarefaRepository.save(tarefa);
        });
    }

    public boolean removerTarefa(Long id) {
        if (tarefaRepository.existsById(id)) {
            tarefaRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 