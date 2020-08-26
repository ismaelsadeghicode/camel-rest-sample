package com.javatar.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xmljson.XmlJsonDataFormat;
import org.springframework.stereotype.Component;

/**
 * @author Esmaeil Sadeghi, 8/25/2020 2:55 PM
 */
@Component
public class XML2JSONRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:marshalEmployeexml2json")
                .to("log:?level=INFO&showBody=true")
                .marshal().xmljson()
                .to("log:?level=INFO&showBody=true");

        final XmlJsonDataFormat xmlJsonFormat = new XmlJsonDataFormat();

        xmlJsonFormat.setRootName("Employee");
        from("direct:unMarshalEmployeejson2xml")
                //.unmarshal().xmljson()
                .unmarshal(xmlJsonFormat)
                .to("log:?level=INFO&showBody=true");

    }
}
