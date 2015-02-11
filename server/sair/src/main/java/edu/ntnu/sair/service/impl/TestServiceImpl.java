package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.service.TestService;
import org.springframework.stereotype.Service;


@Service("testService")
public class TestServiceImpl implements TestService{
    @Override
    public String testSOAP(String name) {
        return null;
    }
}
