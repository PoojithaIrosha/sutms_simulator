package com.poojithairosha.sutmssimulator.service;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;

public class DataTransmissionService {

    private InitialContext context;
    private QueueSender sender;
    private QueueSession session;
    private static DataTransmissionService instance;

    private DataTransmissionService(String factoryJNDI, String queueJNDI) {
        try {
            context = new InitialContext();
            QueueConnectionFactory factory = (QueueConnectionFactory) context.lookup(factoryJNDI);
            session = factory.createQueueConnection().createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            sender = session.createSender((Queue) context.lookup(queueJNDI));
        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataTransmissionService getInstance(String factoryJNDI, String queueJNDI) {
        if(instance == null) {
            instance = new DataTransmissionService(factoryJNDI, queueJNDI);
        }
        return instance;
    }

    public void transmit(Serializable data) throws JMSException {
        sender.send(session.createObjectMessage(data));
    }
}
