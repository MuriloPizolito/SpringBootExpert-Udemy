package br.com.murilo.arquiteturaspring.todos;

import org.springframework.stereotype.Component;

@Component
// todos são components, pai de todas as annotations. quando quiser criar um que não é nenhum dos outros (controller, service, repository) é utilizado o component
public class TodoValidator {

    private TodoRepository todoRepository;

    public TodoValidator(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // metodo que vai receber um todo e validar
    public void validar(TodoEntity todo) {
        if (existeTodoComDescricao(todo.getDescricao())) {
            throw new IllegalArgumentException("Já existe um ToDo com essa descrição");
        }
    }

    private boolean existeTodoComDescricao(String descricao) {
        return todoRepository.existsByDescricao(descricao);
    }
}
