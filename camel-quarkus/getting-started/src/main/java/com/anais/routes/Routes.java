package com.anais.routes;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;

import com.anais.producer.OrderProducer;

public class Routes extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Configurar las rutas de Camel
        from("jms:queue:ordenes")
            .log("Recibiendo mensaje: ${body} con tipoOrden: ${header.tipoOrden}")
            .choice()
                .when(header("tipoOrden").isEqualTo("alimentos"))
                    .log("Enrutando a la cola: ordenes-alimentos")
                    .to("jms:queue:ordenes-alimentos")
                .when(header("tipoOrden").isEqualTo("ropa"))
                    .log("Enrutando a la cola: ordenes-ropa")
                    .to("jms:queue:ordenes-ropa")
                .otherwise()
                    .log("Enrutando a la cola: ordenes-generales")
                    .to("jms:queue:ordenes-generales")
            .end();

        ProducerTemplate producerTemplate = getContext().createProducerTemplate();

        OrderProducer orderProducer = new OrderProducer(producerTemplate);

        orderProducer.init();
    }
}