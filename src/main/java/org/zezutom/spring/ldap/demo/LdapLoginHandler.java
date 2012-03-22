package org.zezutom.spring.ldap.demo;

import javax.annotation.Resource;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author tomasz
 */
@Service("ldapLoginHandler")
public class LdapLoginHandler implements LoginHandler {

    public static final String UID_FORMAT = "uid=%s";
    @Resource
    private AuthenticationManager authManager;
    
    @Resource
    private LdapTemplate ldapTemplate;

    private String searchBase;

    public void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }
    
    @Override
    public boolean login(String username, String password) {

        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        final boolean authenticated = isAuthenticated(auth);

        if (authenticated) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        return authenticated;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        if (!login(username, oldPassword)) {
            return false;
        }

        DirContextOperations ctx = ldapTemplate.lookupContext(getUid(username));
        ctx.setAttributeValue("userPassword", newPassword);
        ldapTemplate.modifyAttributes(ctx);
        return true;
    }
    
    private boolean isAuthenticated(Authentication auth) {
        return auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated();
    }

    private String getUid(String username) {
        if (searchBase == null || searchBase.isEmpty()) {
            return String.format(UID_FORMAT, username);
        } else {
            return String.format(UID_FORMAT, username) + "," + searchBase;
        }
    }
}
