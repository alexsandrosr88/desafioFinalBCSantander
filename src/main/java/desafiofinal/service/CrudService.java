package desafiofinal.service;

import java.util.Set;

public interface CrudService<ID, T> {
    Set<T> listarTodos();
    T pesquisarPorId(ID id);
    T salvar(T entity);
    T atualizar(ID id, T entity);
    void delete(ID id);

}
