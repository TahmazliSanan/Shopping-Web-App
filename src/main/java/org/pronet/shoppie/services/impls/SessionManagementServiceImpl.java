package org.pronet.shoppie.services.impls;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.pronet.shoppie.services.SessionManagementService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Service
public class SessionManagementServiceImpl implements SessionManagementService {
    @Override
    public void removeSessionMessage() {
        HttpServletRequest request = ((ServletRequestAttributes) (Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())))
                .getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute("successMessage");
        session.removeAttribute("errorMessage");
    }
}
