package edu.ntnu.sair.service.impl;

import edu.ntnu.sair.service.TestService;
import org.springframework.stereotype.Service;

/**
 * Created by chun on 1/28/15.
 */

@Service("testService")
public class TestServiceImpl implements TestService{
    @Override
    public String testSOAP(String name) {
        return null;
    }
}
