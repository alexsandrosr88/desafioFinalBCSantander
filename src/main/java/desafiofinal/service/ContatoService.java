package desafiofinal.service;

import desafiofinal.model.Contato;

import java.util.Set;

public interface ContatoService extends CrudService<Integer, Contato> {
    Set<Contato> buscaContatoPorNome(String nome);
}