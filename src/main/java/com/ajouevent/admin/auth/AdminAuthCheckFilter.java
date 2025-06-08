package com.ajouevent.admin.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminAuthCheckFilter extends OncePerRequestFilter {

    private final SessionRepository<? extends Session> sessionRepository;

    public AdminAuthCheckFilter(SessionRepository<? extends Session> sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String sessionId = request.getHeader("X-Session-Id");

        if (sessionId == null || sessionId.isBlank()) {
            respondUnauthorized(response);
            return;
        }

        Session session = sessionRepository.findById(sessionId);
        if (session == null) {
            respondUnauthorized(response);
            return;
        }

        Long adminId = session.getAttribute("adminId");
        if (adminId == null) {
            respondUnauthorized(response);
            return;
        }

        request.setAttribute("adminId", adminId);
        filterChain.doFilter(request, response);

    }

    private void respondUnauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("""
        {
          "code": 401001,
          "message": "로그인이 필요한 요청입니다."
        }
    """);
    }

}
