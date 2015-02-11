package edu.ntnu.sair.service.impl;

/**
 * Created by chun on 1/28/15.
 */

import edu.ntnu.sair.service.HelloWorld;
import javax.jws.WebService;

@WebService(endpointInterface = "edu.ntnu.sair.service.HelloWorld", targetNamespace = "http://service.sair.ntnu.edu/")
public class HelloWorldImpl implements HelloWorld {


    public String sayHi(String text) {
        System.out.println("sayHi called");
        return "Hi " + text;
    }

    public String sayHello(){
        System.out.println("HELLOOOOOOOOOOOOOOOOOOOOOOO");
        return  "Hello";
    }

}