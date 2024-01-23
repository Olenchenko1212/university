package ua.foxminded.universitycms.util;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String redirectUrl = determineRedirectUrl(authentication);
        redirectToUrl(request, response, redirectUrl);
    }

    private String determineRedirectUrl(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            String authority = grantedAuthority.getAuthority();

            if (RoleEnum.ROLE_USER.toString().equals(authority)) {
                return "/menu";
            } else if (RoleEnum.ROLE_ADMIN.toString().equals(authority)) {
                return "/admin-panel";
            }
        }
        return "/error";
    }

    private void redirectToUrl(HttpServletRequest request, HttpServletResponse response, String redirectUrl)
            throws IOException {
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}