package desafiofinal.service.impl;

import desafiofinal.model.Contato;
import desafiofinal.model.Endereco;
import desafiofinal.repository.ContatoRepository;
import desafiofinal.repository.EnderecoRepository;
import desafiofinal.service.ContatoService;
import desafiofinal.service.ViaCepService;
import desafiofinal.service.exception.RecursoNaoEncontrado;
import desafiofinal.service.exception.RegraDeNegocioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ContatoServiceImpl implements ContatoService {

    private final ContatoRepository repository;

    private final EnderecoRepository enderecoRepository;

    private final ViaCepService viaCepService;

    @Override
    public Set<Contato> listarTodos() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public Set<Contato> buscaContatoPorNome(String nome) {
        return repository.buscaContatoIniciadosPorNome(nome).orElseThrow(RecursoNaoEncontrado::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Contato pesquisarPorId(Integer integer) {
        return repository.findById(integer).orElseThrow(RecursoNaoEncontrado::new);
    }

    @Transactional(readOnly = false)
    @Override
    public Contato salvar(Contato entity) {
        validaCampos(entity);

        entity.setEndereco(salvarEndereco(entity));

        return repository.save(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public Contato atualizar(Integer integer, Contato entity) {


        Contato contato = validaID(integer);
        contato.setCpf(entity.getCpf());
        contato.setNome(entity.getNome());
        contato.setTelefone(entity.getTelefone());
        contato.setEmail(entity.getEmail());

        Endereco endereco = new Endereco(contato.getEndereco());

        if (!entity.getEndereco().getCep().equalsIgnoreCase(contato.getEndereco().getCep())) {
            endereco = new Endereco(viaCepService.consultarCep(entity.getEndereco().getCep()));
            endereco.setId(contato.getEndereco().getId());
        }

        endereco.setNumero(entity.getEndereco().getNumero());
        endereco.setComplemento(entity.getEndereco().getComplemento());

        contato.setEndereco(endereco);

        return repository.save(contato);
    }

    @Override
    public void delete(Integer integer) {
        repository.delete(validaID(integer));
    }

    private Endereco salvarEndereco(Contato entity) {

        Endereco novoEndereco = new Endereco(viaCepService.consultarCep(entity.getEndereco().getCep()));

        novoEndereco.setNumero(entity.getEndereco().getNumero());
        novoEndereco.setComplemento(entity.getEndereco().getComplemento());

        return enderecoRepository.save(novoEndereco);
    }

    private Contato validaID(Integer id) {
        return repository.findById(id).orElseThrow(RecursoNaoEncontrado::new);
    }

    private void validaCampos(Contato entity) {
        if (entity.getEndereco().getCep() == null
                || entity.getEndereco().getCep().equalsIgnoreCase("string")
                || entity.getEndereco().getCep().equalsIgnoreCase("")
                || entity.getEndereco().getCep().trim().length() < 8)
            throw new RegraDeNegocioExcecao("O cep está inválido!");

        else if (entity.getNome() == null || entity.getNome().equalsIgnoreCase("")
                || entity.getNome().trim().length() < 2)
            throw new RegraDeNegocioExcecao("O nome está inválido!");

        else if (entity.getCpf() == null || repository.existsByCpf(entity.getCpf())
                || entity.getCpf().trim().length() < 11 || entity.getCpf().equalsIgnoreCase(""))
            throw new RegraDeNegocioExcecao("O cpf fornecido é inválido.");

        else if (entity.getTelefone() == null || entity.getTelefone().equalsIgnoreCase("")
                || repository.existsByTelefone(entity.getTelefone()))
            throw new RegraDeNegocioExcecao("O telefone fornecido é inválido.");

        else if (entity.getEmail() == null || repository.existsByEmail(entity.getEmail()))
            throw new RegraDeNegocioExcecao("O e-mail fornecido é inválido.");

        else if (entity.getEndereco().getNumero() == null
                || entity.getEndereco().getNumero().equalsIgnoreCase(""))
            throw new RegraDeNegocioExcecao("O número está inválido!");

        else if (entity.getEndereco().getComplemento() == null
                || entity.getEndereco().getComplemento().equalsIgnoreCase(""))
            throw new RegraDeNegocioExcecao("O complemento está inválido!");
    }
}
