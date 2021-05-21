package toy.subscribe.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import toy.subscribe.domain.logging.interceptor.LoggingInterceptor;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {
    
    private final LoggingInterceptor loggingInterceptor;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
                .setCachePeriod(86400)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(loggingInterceptor)
                .excludePathPatterns("/images/**", "/js/**", "/css/**", "/boards", "/dictionaries")
                .addPathPatterns("/**");
    }
    
}
