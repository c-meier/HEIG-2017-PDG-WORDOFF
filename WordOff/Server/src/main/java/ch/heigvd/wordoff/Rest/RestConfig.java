package ch.heigvd.wordoff.Rest;

import ch.heigvd.wordoff.Repository.UserRepository;
import ch.heigvd.wordoff.Rest.Inteceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class RestConfig extends WebMvcConfigurerAdapter {
    private UserRepository repository;

    @Bean
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/user/test")
                .addPathPatterns("/game/**/*");
        super.addInterceptors(registry);
    }
}
