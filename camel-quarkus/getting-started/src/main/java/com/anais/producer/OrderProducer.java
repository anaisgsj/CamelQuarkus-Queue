package com.anais.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.camel.ProducerTemplate;


public class OrderProducer {

    private final ProducerTemplate producerTemplate;

    public OrderProducer(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

    public void init() {
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            System.out.println(">>> Método enviarOrden ejecutado");
            enviarOrden();
        }, 1, TimeUnit.SECONDS); 
    }
    public void enviarOrden() {

        Map<String, Object> headers = new HashMap<>();
        headers.put("tipoOrden", "alimentos");
        producerTemplate.sendBodyAndHeaders("jms:queue:ordenes", "Orden de Alimentos", headers);

        headers.put("tipoOrden", "ropa");
        producerTemplate.sendBodyAndHeaders("jms:queue:ordenes", "Orden de Ropa", headers);

        headers.put("tipoOrden", "general");
        producerTemplate.sendBodyAndHeaders("jms:queue:ordenes", "Orden General", headers);
    }

    
}
