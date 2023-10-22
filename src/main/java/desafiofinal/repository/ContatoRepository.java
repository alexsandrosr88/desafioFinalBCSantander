package desafiofinal.repository;

import desafiofinal.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {
    boolean existsByCpf(String cpf);
    boolean existsByTelefone(String telefone);
    boolean existsByEmail(String email);
    @Query("SELECT c FROM Contato c WHERE LOWER(c.nome) LIKE lower(:nome || '%')")
    Optional<Set<Contato>> buscaContatoIniciadosPorNome(String nome);
}
