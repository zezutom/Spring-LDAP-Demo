package org.zezutom.spring.ldap.demo;

/**
 * Provides basic authentication services.
 * 
 * @author tomasz
 */
public interface LoginHandler {
   
    /**
     * Logs the user in.
     * 
     * @param username
     * @param password
     * 
     * @return True if the user has been successfully logged in, false otherwise.
     */
    boolean login(String username, String password);
    
    /**
     * Enables to change a password without logging in.
     * 
     * @param username
     * @param oldPassword
     * @param newPassword
     * 
     * @return True if the password has been successfully changed, false otherwise.
     */
    boolean changePassword(String username, String oldPassword, String newPassword);

}
