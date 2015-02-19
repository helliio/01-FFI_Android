package edu.ntnu.sair.service;

import javax.jws.WebService;

/**
 * Created by chun on 2/16/15.
 */

@WebService
public interface HelloWorld {

    String sayHi(String text);

    String sayHello();
}