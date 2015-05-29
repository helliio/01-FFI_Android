package edu.ntnu.sair.aspect;

import edu.ntnu.sair.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Chun on 2/16/15.
 * <br>
 * AuthenticateAspect: Authenticate the users.
 */
@Aspect
@Service("authenticateAspect")
public class AuthenticateAspect {
    private UserService userService;

    /**
     * Set the pointcut.
     */
    @Pointcut("execution(* edu.ntnu.sair.service.ReportService.*(..)) && args(..)")
    private void aspectjMethod() {
    }

    /**
     * Execute before the point cut
     *
     * @param jp the pointcut
     */
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