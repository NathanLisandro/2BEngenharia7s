package com.gerenciadordetarefas.main.repository;

import com.gerenciadordetarefas.main.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
} 