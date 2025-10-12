package com.personal.task_manager.exceptions;

//Exceção criada para que seja lançada uma exceção personalizada ao usuário
public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
