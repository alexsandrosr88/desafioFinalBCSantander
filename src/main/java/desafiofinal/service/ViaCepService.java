package desafiofinal.service;

import desafiofinal.dto.EnderecoViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "http://viacep.com.br/ws")
public interface ViaCepService {

    @GetMapping("/{cep}/json/")
    EnderecoViaCepDTO consultarCep(@PathVariable("cep") String cep);
}
