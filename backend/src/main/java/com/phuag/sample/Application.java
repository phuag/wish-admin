package com.phuag.sample;

import com.phuag.sample.modules.sys.service.SysUserService;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author vvvvvv
 * @date 2017/3/1
 */
@SpringBootApplication
@Configuration
@EnableSwagger2
@MapperScan(basePackages = "com.phuag.sample.modules")
public class Application {

    private static final Logger log = LoggerFactory
            .getLogger(Application.class);

    @Autowired
    private SysUserService sysUserService;

    @Bean
    public Docket config() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.phuag.sample"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统API文档")
                .contact(new Contact("作者", "访问地址", "联系方式"))
                .build();
    }

//    @RequestMapping("/")
    public String sayHello(){
        log.debug("in sayHello");
        String passwordWithSalt = sysUserService.encryptPassword("asfas");
        return "Hello world!\n"+passwordWithSalt;
    }

    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class,args);
    }
}
