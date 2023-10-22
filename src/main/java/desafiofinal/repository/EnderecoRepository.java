package desafiofinal.repository;

import desafiofinal.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Query("select e from Endereco e where LOWER(e.cep) = LOWER(:cep) and e.numero = :numero and LOWER(e.complemento) = LOWER(:complemento)")
    Endereco existsEndereco(@Param("cep") String cep, @Param("numero") String numero,
                            @Param("complemento") String complemento);
}