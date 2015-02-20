package edu.ntnu.sair.aspect;

import edu.ntnu.sair.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Aspect
@Service("authenticateAspect")
public class AuthenticateAspect {
    private UserService userService;

    @Pointcut("execution(* edu.ntnu.sair.service.ReportService.*(..)) && args(..)")
    private void aspectjMethod() {
    }

    @Before(value = "aspectjMethod()")
    public void before(JoinPoint jp) {
        System.out.println("Authenticate Aspect before execution is running");
        Object[] args = jp.getArgs();
        System.out.println("Authenticate Aspect is executing the request");
        System.out.println("Authenticate Aspect is done and forwarding");
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}