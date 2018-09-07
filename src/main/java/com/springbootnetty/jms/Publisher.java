package com.springbootnetty.jms;//package com.springnetty.jms;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.stereotype.Service;
//
//import javax.print.attribute.standard.Destination;
//
//@Service
//public class Publisher {
//
//    @Autowired
//    private JmsTemplate jmsTemplate;
//
//    public void sendMessage(Destination destination, Object message){
//        try {
//            jmsTemplate.convertAndSend(destination,message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
