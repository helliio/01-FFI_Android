package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.service.HelloWorld;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * Created by chun on 2/3/15.
 */


@WebService(endpointInterface = "edu.ntnu.sair.service.HelloWorld", targetNamespace = "http://service.sair.ntnu.edu/")
@Service("helloWorld")
public class HelloWorldImpl implements HelloWorld {

    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hi " + text;
    }

    public String sayHello() {
        System.out.println("HELLOOOOOOOOOOOOOOOOOOOOOOO");
        return "Hello";
    }

}