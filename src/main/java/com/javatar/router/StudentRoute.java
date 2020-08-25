package com.javatar.router;

import com.javatar.data.Student;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Esmaeil Sadeghi, 8/25/2020 12:25 PM
 */
@Component
public class StudentRoute extends RouteBuilder {

    @Override
    public void configure() {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.json);

//        rest("/api/people")
//                .get()
//                .route()
//                .setBody(constant("ali, hasan, hosain")); //http://localhost:8080/api/people

        rest("/student").produces("application/json")
                .get("/hello/{name}") // http://localhost:8080/student/hello/ali
                .route().transform().simple("Hello ${header.name}, Welcome to JavaOutOfBounds.com")
                .endRest()
                .get("/records/{name}").to("direct:records")

                .get("/customers").to("direct:costomerlist");

        from("direct:records")
                .process(new Processor() {

                    final AtomicLong counter = new AtomicLong();

                    @Override
                    public void process(Exchange exchange) throws Exception {

                        final String name = exchange.getIn().getHeader("name", String.class);
                        exchange.getIn().setBody(new Student(counter.incrementAndGet(), name, "Camel + SpringBoot"));
                    }
                });

        from("direct:costomerlist")
                .process(new Processor() {

                    List<String> products = new ArrayList<>();

                    @Override
                    public void process(Exchange exchange) throws Exception {
                        products.add("ali");
                        products.add("hasan");

                        exchange.getOut().setBody(products);
                    }
                });
    }
}