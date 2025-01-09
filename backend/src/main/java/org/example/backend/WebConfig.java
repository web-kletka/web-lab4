package org.example.backend;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Подключить ко всем маршрутам
                .allowedOrigins("http://localhost:5173") // Разрешить ваш фронтенд
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Методы
                .allowedHeaders("*") // Любые заголовки
                .allowCredentials(true); // Если используете куки или авторизацию
    }
}

