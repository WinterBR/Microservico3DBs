package br.com.winter.validation;

import br.com.winter.entity.Cliente;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

//Valida se o Cliente é maior de idade
@Component
public class ClienteValidation {

    public Boolean maiorDeIdade(Cliente cliente) {
        LocalDate aniversario = cliente.getData();
        LocalDate dataAtual = LocalDate.now();
        int idade = Period.between(aniversario, dataAtual).getYears();
        return idade >= 18;
    }
}
