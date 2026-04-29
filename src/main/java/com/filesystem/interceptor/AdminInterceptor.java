package com.filesystem.interceptor;

import com.filesystem.entity.User;
import com.filesystem.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理员权限拦截器
 *
 */
@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !UserRole.ADMIN.getCode().equals(user.getRole())) {
            log.warn("非管理员访问: {}, 用户: {}", request.getRequestURI(), user != null ? user.getUsername() : "null");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(objectMapper.writeValueAsString(Result.forbidden()));
            return false;
        }

        return true;
    }
}
