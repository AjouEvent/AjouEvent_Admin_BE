//package com.ajouevent.admin.auth;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class AdminAuthCheckFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        HttpSession session = request.getSession(false);
//        Long adminId = (session != null) ? (Long) session.getAttribute("adminId") : null;
//
//        // 인증되지 않은 요청이면 401 반환
//        if (adminId == null) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.setContentType("application/json; charset=UTF-8");
//            response.getWriter().write("""
//                {
//                  "code": 401001,
//                  "message": "로그인이 필요한 요청입니다."
//                }
//            """);
//            return;
//        }
//
//        // 인증 완료 → 다음 필터로 진행
//        filterChain.doFilter(request, response);
//    }
//}
