package ffiandroid.situationawareness.service;

/**
 * Created by chun on 3/4/15.
 */
public interface UserService {

    public String register(String username, String password, String name, String teamId);

    public String login(String username, String deviceId, String password);
}
