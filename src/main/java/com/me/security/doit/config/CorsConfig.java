package com.me.security.doit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for Cross-Origin Resource Sharing (CORS).
 * Defines allowed origins, methods, and headers for API requests.
 * 
 * @author KAYUMBA J88
 * @version 1.0
 * @see Configuration
 * @see EnableWebMvc
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    
    /**
     * Configures CORS mappings for the application.
     * 
     * @param registry the CorsRegistry to configure
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("https://apis-react-spring-client-kayumba.vercel.app", "http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Access-Control-Allow-Origin");
    }
}