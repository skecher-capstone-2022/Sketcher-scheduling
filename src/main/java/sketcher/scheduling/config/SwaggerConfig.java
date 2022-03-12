package sketcher.scheduling.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import sketcher.scheduling.domain.Schedule;
import sketcher.scheduling.dto.ScheduleDto;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api(TypeResolver typeResolver){

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
//                .apiInfo(apiInfo())
    }

    private ApiInfo apiInfo() {
        String description = "Sketcher API Documentation";
        return new ApiInfoBuilder()
                .title("Sketcher Swagger")
                .description(description)
                .version("1.0")
                .contact(new Contact("Sketcher" , "https://github.com/dokongMin/Sketcher-scheduling","aaa@tukorea.ac.kr"))
                .build();
    }
}
