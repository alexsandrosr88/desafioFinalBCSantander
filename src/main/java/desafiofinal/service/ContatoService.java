package desafiofinal.service;

import desafiofinal.dto.ContatoResponseDTO;
import desafiofinal.model.Contato;
import desafiofinal.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

public interface ContatoService extends CrudService<Integer, Contato> {
}
