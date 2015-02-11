package edu.ntnu.sair.service;

import javax.jws.WebService;


/**
 * Created by chun on 1/28/15.
 */

@WebService
public interface HelloWorld {
    String sayHi(String text);

    String sayHello();
}