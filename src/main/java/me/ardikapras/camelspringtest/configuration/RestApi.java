package me.ardikapras.camelspringtest.configuration;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RestApi extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .contextPath("/api")
                .port(8088);
    }

    @Bean
    ServletRegistrationBean apiServletRegistrationBean() {
        ServletRegistrationBean servlet =
                new ServletRegistrationBean(new CamelHttpTransportServlet(), "/api/*");
        servlet.setName("CamelServlet");
        return servlet;
    }
}
