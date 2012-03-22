package org.zezutom.spring.ldap.demo;
import org.zezutom.spring.ldap.demo.LoginHandler;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;
/**
 *
 * @author tomasz
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:app-context.xml")
public class WhenALoginAttemptIsMade {

    @Resource(name="ldapLoginHandler")
    private LoginHandler loginHandler;
    
    @Test
    public void loginShouldSucceedWithValidCredentials() {
        assertTrue(loginHandler.login("rod", "koala"));
    }

    @Test(expected=BadCredentialsException.class)
    public void invalidPasswordShouldNotBeAccepted() {
        loginHandler.login("rod", "nonsense");
        fail("An invalid password should not be accepted!");
    }
    
    @Test(expected=BadCredentialsException.class)
    public void anEmptyPasswordShouldNotBeAccepted() {
        loginHandler.login("rod", "");
        fail("An invalid password should not be accepted!");    
    }
    
    @Test    
    public void itShouldBePossibleToChangeAPassword() {
        assertTrue(loginHandler.changePassword("rod", "koala", "test"));
        assertTrue(loginHandler.login("rod", "test"));
    }    
    
}
