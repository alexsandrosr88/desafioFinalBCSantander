package desafiofinal.service.impl;

import desafiofinal.model.Contato;
import desafiofinal.model.Endereco;
import desafiofinal.repository.ContatoRepository;
import desafiofinal.repository.EnderecoRepository;
import desafiofinal.service.ContatoService;
import desafiofinal.service.ViaCepService;
import desafiofinal.service.exception.RecursoNaoEncontrado;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ContatoServiceImpl implements ContatoService {

    private ContatoRepository repository;
    private ViaCepService viaCepService;

    private EnderecoRepository enderecoRepository;

    public ContatoServiceImpl(ContatoRepository repository){
        this.repository = repository;
    }

    @Override
    public Set<Contato> listarTodos() {
        return new HashSet<>(repository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public Contato pesquisarPorId(Integer integer) {
        return repository.findById(integer).orElseThrow(RecursoNaoEncontrado::new);
    }

    @Override
    public Contato salvar(Contato entity) {

        entity.setEndereco(salvarEndereco(entity));

        return repository.save(entity);
    }

    @Override
    public Contato atualizar(Integer integer, Contato entity) {
        return null;
    }

    @Override
    public void delete(Integer integer) {
    }

    private Endereco salvarEndereco(Contato entity){
        Endereco endEntity = new Endereco(viaCepService.consultarCep(entity.getCpf()));
        endEntity.setNumero(endEntity.getNumero());
        endEntity.setComplemento(endEntity.getComplemento());
        return enderecoRepository.save(endEntity);
    }
}
