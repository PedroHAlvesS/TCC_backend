package br.com.rapidoja.tcc.security.aspect;

import br.com.rapidoja.tcc.security.annotation.AdminOnly;
import br.com.rapidoja.tcc.security.annotation.AdminOrCustomer;
import br.com.rapidoja.tcc.security.annotation.AdminOrDeliveryMan;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;

@Aspect
@Component
public class SecurityAspect {

    @Around("@annotation(adminOnly)")
    public Object checkAdminOnly(ProceedingJoinPoint joinPoint, AdminOnly adminOnly) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            throw new SecurityException("Access denied: Admin role required");
        }
        
        return joinPoint.proceed();
    }

    @Around("@annotation(adminOrCustomer)")
    public Object checkAdminOrCustomer(ProceedingJoinPoint joinPoint, AdminOrCustomer adminOrCustomer) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null) {
            throw new SecurityException("Access denied: Authentication required");
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return joinPoint.proceed();
        }

        boolean isCustomer = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_CUSTOMER"));

        if (!isCustomer) {
            throw new SecurityException("Access denied: Customer or Admin role required");
        }

        Long tokenUserId = (Long) authentication.getCredentials();
        String customerIdParam = adminOrCustomer.customerIdParam();
        Long customerId = getPathVariableValue(joinPoint, customerIdParam);

        if (customerId == null) {
            throw new SecurityException("Access denied: Customer ID parameter not found");
        }

        if (!tokenUserId.equals(customerId)) {
            throw new SecurityException("Access denied: Token does not belong to the specified customer");
        }

        return joinPoint.proceed();
    }

    @Around("@annotation(adminOrDeliveryMan)")
    public Object checkAdminOrDeliveryMan(ProceedingJoinPoint joinPoint, AdminOrDeliveryMan adminOrDeliveryMan) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null) {
            throw new SecurityException("Access denied: Authentication required");
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return joinPoint.proceed();
        }

        boolean isDeliveryMan = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_DELIVERY_MAN"));

        if (!isDeliveryMan) {
            throw new SecurityException("Access denied: Delivery Man or Admin role required");
        }

        Long tokenUserId = (Long) authentication.getCredentials();
        String deliveryManIdParam = adminOrDeliveryMan.deliveryManIdParam();
        Long deliveryManId = getPathVariableValue(joinPoint, deliveryManIdParam);

        if (deliveryManId == null) {
            throw new SecurityException("Access denied: Delivery Man ID parameter not found");
        }

        if (!tokenUserId.equals(deliveryManId)) {
            throw new SecurityException("Access denied: Token does not belong to the specified delivery man");
        }

        return joinPoint.proceed();
    }

    private Long getPathVariableValue(ProceedingJoinPoint joinPoint, String paramName) {
        Object[] args = joinPoint.getArgs();
        java.lang.reflect.Method method = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod();
        java.lang.reflect.Parameter[] parameters = method.getParameters();

        for (int i = 0; i < parameters.length; i++) {
            PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
            if (pathVariable != null && (pathVariable.value().equals(paramName) || parameters[i].getName().equals(paramName))) {
                if (args[i] instanceof Long) {
                    return (Long) args[i];
                }
            }
        }
        return null;
    }
}
