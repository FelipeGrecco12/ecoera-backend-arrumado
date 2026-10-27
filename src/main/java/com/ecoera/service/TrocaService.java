package com.ecoera.service;

import com.ecoera.model.Troca;
import com.ecoera.repository.TrocaRepository;
import com.ecoera.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TrocaService {

    private final TrocaRepository repository;

    public TrocaService(TrocaRepository repository) {
        this.repository = repository;
    }

    public List<Troca> listarTodas() {
        return repository.findAll();
    }

    public List<Troca> listarPorStatus(String status) {
        return repository.findByStatus(status);
    }

    public Troca buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Troca não encontrada: " + id));
    }

    public Troca salvar(Troca troca) {
        if (troca.getStatus() == null) {
            troca.setStatus("PENDENTE");
        }
        if (troca.getDataTroca() == null) {
            troca.setDataTroca(LocalDate.now());
        }
        return repository.save(troca);
    }

    public Troca concluir(Long id) {
        Troca troca = buscarPorId(id);
        troca.setStatus("CONCLUIDA");
        return repository.save(troca);
    }

    public void deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
    }
}