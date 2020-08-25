package com.javatar.router;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * @author Esmaeil Sadeghi, 8/25/2020 2:33 PM
 */
@Component
public class AppRouter extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        from("rest:get:hello:/{me}")
                .transform().simple("Hi ${header.me}");

        from("rest:get:hello:/french/{me}")
                .transform().simple("Bonjour ${header.me}");

        rest("/api/people")
                .get()
                .route()
                .setBody(constant("ali, hasan, hosain")); //http://localhost:8080/api/people
    }
}
