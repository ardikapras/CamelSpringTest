package me.ardikapras.camelspringtest.rest;

import me.ardikapras.camelspringtest.model.AppModel;
import me.ardikapras.camelspringtest.model.Response;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
public class AppController extends RouteBuilder {
    Logger logger = LoggerFactory.getLogger(AppController.class);

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .enableCORS(true)
                .bindingMode(RestBindingMode.auto)
                .dataFormatProperty("prettyPrint", "true")
                .dataFormatProperty("mustBeJAXBElement", "false");

        rest("/customers/")
                .produces(APPLICATION_JSON_VALUE)
                .consumes(APPLICATION_JSON_VALUE)

                .get("/{id}?sortBy={sort}&filterBy={filter}").type(AppModel.class).routeId("get-data").to("direct:getData")
                .patch("?sortBy={sort}&filterBy={filter}").type(AppModel.class).routeId("patch-data").to("direct:patchData")

                .outType(Response.class);

        from("direct:getData")
                .process(
                        (Exchange exchange) -> {
                            String id = exchange.getIn().getHeader("id", String.class);
                            String sort = exchange.getIn().getHeader("sort", String.class);
                            String filter = exchange.getIn().getHeader("filter", String.class);
                            logger.info("GET method");
                            logger.info("Customer ID: {}", id);
                            logger.info("Sort: {}", sort);
                            logger.info("Filter: {}", filter);
                            Response response = new Response();
                            response.setStatus("success");
                            response.setMessage(id + "," + sort + "," + filter);
                            exchange.getIn().setBody(response);
                        })
                .removeHeaders("*")
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

        from("direct:patchData")
                .process(
                        (Exchange exchange) -> {
                            AppModel appModel = (AppModel) exchange.getIn().getBody();
                            String sort = exchange.getIn().getHeader("sort", String.class);
                            String filter = exchange.getIn().getHeader("filter", String.class);
                            logger.info("PATCH method");
                            logger.info("Sort: {}", sort);
                            logger.info("Filter: {}", filter);
                            logger.info("First Name: {}", appModel.getFirstName());
                            logger.info("Last Name: {}", appModel.getLastName());
                            logger.info("Email: {}", appModel.getEmail());
                            Response response = new Response();
                            response.setStatus("success");
                            response.setMessage(String.valueOf(appModel));
                            exchange.getIn().setBody(response);
                        })
                .removeHeaders("*")
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}
