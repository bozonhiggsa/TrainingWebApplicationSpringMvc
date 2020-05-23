package training.web.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.VersionResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import training.web.application.interceptor.MatchUserInterceptor;
import training.web.application.interceptor.ValidateDataInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * Java Config - instead of a xml configuration
 * @author Ihor Savchenko
 * @version 1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan("training.web.application.controller")
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");

        return resolver;
    }

    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new ValidateDataInterceptor()).addPathPatterns("/createUser").order(1);
        registry.addInterceptor(new MatchUserInterceptor()).addPathPatterns("/createUser").order(2);
    }

    // позволяет получить ресурсы, задав к ним путь в url
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations("/js/", "/css/", "/img/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(new VersionResourceResolver()
                        .addContentVersionStrategy("/**"));

    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/loginPage").setStatusCode(HttpStatus.OK).setViewName("login");
        registry.addViewController("/registrationPage").setStatusCode(HttpStatus.OK).setViewName("registration");
    }
}
