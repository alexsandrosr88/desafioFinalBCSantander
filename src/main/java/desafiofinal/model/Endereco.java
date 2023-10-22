package desafiofinal.model;

import desafiofinal.dto.EnderecoRequestDTO;
import desafiofinal.dto.EnderecoViaCepDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String cep;
    @Column(length = 50)
    private String logradouro;
    @Column(length = 10)
    private String numero;
    @Column(length = 15)
    private String complemento;
    @Column(length = 50)
    private String bairro;
    @Column(length = 50)
    private String cidade;
    @Column(length = 2)
    private String estado;

    public Endereco(EnderecoRequestDTO enderecoRequestDTO){
        cep = enderecoRequestDTO.getCep();
        numero = enderecoRequestDTO.getNumero();
        complemento = enderecoRequestDTO.getComplemento();
    }

    public Endereco(EnderecoViaCepDTO enderecoViaCepDTO){
        cep = enderecoViaCepDTO.getCep();
        logradouro = enderecoViaCepDTO.getLogradouro();
        bairro = enderecoViaCepDTO.getBairro();
        cidade = enderecoViaCepDTO.getLocalidade();
        estado = enderecoViaCepDTO.getUf();
    }
    public Endereco(Endereco endereco){
        id = endereco.getId();
        cep = endereco.getCep();
        logradouro = endereco.getLogradouro();
        numero = endereco.getNumero();
        complemento = endereco.getComplemento();
        bairro = endereco.getBairro();
        cidade = endereco.getCidade();
        estado = endereco.getEstado();
    }
}
