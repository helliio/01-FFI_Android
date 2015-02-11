package edu.ntnu.sair.service;

import javax.jws.WebService;

@WebService
public interface HelloWorld {
    String sayHi(String text);

    String sayHello();
}