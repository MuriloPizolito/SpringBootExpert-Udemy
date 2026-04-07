package br.com.murilo.arquiteturaspring;

import br.com.murilo.arquiteturaspring.todos.TodoEntity;
import br.com.murilo.arquiteturaspring.todos.TodoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Lazy // o padrão da annotacao é true. Diz que esse bean só sera instanciado quando for acionado (código).
@Component
// escopo - pode colocar nos beans ou em components.
// @Scope("singleton") // padrão - singleton (onde vc tem uma instancia unica de um objeto que vai atender toda a aplicaçãp)
@Scope(BeanDefinition.SCOPE_SINGLETON) // outra forma de definir
// @Scope("prototype") contrário do singleton, para cada usuário/requisição ele cria uma instância
// @Scope("request") nao guarda estado, sobrevive enquanto durar a requisiçãp (aplicações web), quando finalza a requisição ele nao existe mais.
// @Scope("session") guarda/mantem apenas durante a sessão do usuário, um único usuário
// @Scope("application") serve apenas para aplicações web, parecido com o session mas serve para todos os usuários
// @Scope(WebApplicationContext.SCOPE_APPLICATION) outra forma de definir, esses ficam no pacote WebApplication
public class BeanGerenciado {

    // Primeira forma - mais facil
    @Autowired
    private TodoValidator validator;

    // Segunda forma - mais recomendada
    @Autowired
    public BeanGerenciado(TodoValidator validator) {
        this.validator = validator;
    }

    public void utilizar() {
        var todo = new TodoEntity();
        validator.validar(todo);
    }

    // Terceira forma de injetar - menos usada
    @Autowired
    public void setValidator(TodoValidator validator) {
        this.validator = validator;
    }

}
