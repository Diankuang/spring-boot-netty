package com.springbootnetty.config;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;

public class JMSConfig {

    @Bean
    public BrokerService brokerService() {
        BrokerService brokerService = new BrokerService();
        ActiveMQTopic topic = new ActiveMQTopic("jms.topic.HttpToTcp");
        brokerService.setDestinations(new ActiveMQDestination[]{topic});
        ManagementContext managementContext = new ManagementContext();
        managementContext.setCreateConnector(true);
        brokerService.setManagementContext(managementContext);
        return brokerService;
    }




}
