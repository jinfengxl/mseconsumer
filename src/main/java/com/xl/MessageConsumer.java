package com.xl;

import com.aliyun.openservices.ons.api.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.nio.charset.Charset;
import java.util.Properties;


@Component
public class MessageConsumer implements ApplicationRunner {

    @Value(("${grayEnv:base}"))
    private String gray;


    @Value(("${mqAddress:rmq-cn-x0r3bu0yp03.cn-hangzhou.rmq.aliyuncs.com:8080}"))
    private String mqAddress;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /**
        //String endpoints = "rmq-cn-x0r3bu0yp03.cn-hangzhou.rmq.aliyuncs.com:8080";
        String endpoints = mqAddress;
        //指定需要订阅哪个目标Topic，Topic需要提前在控制台创建，如果不创建直接使用会返回报错。
        String topic = "xl-test";
        //为消费者指定所属的消费者分组，Group需要提前在控制台创建，如果不创建直接使用会返回报错。
        String consumerGroup = "xl-test-group";
        final ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfigurationBuilder builder = ClientConfiguration.newBuilder().setEndpoints(endpoints);

        builder.setCredentialProvider(new StaticSessionCredentialsProvider("kuUR1WQF4JkFT53A", "I5yi1WuGxe4Lzhtl"));
        ClientConfiguration clientConfiguration = builder.build();
        //订阅消息的过滤规则，表示订阅所有Tag的消息。
        String tag = "*";
        FilterExpression filterExpression = new FilterExpression(tag, FilterExpressionType.TAG);
        //初始化PushConsumer，需要绑定消费者分组ConsumerGroup、通信参数以及订阅关系。
        try {

            Charset charset = Charset.forName("UTF-8");

            PushConsumer pushConsumer = provider.newPushConsumerBuilder()
                    .setClientConfiguration(clientConfiguration)
                    //设置消费者分组。
                    .setConsumerGroup(consumerGroup)
                    //设置预绑定的订阅关系。
                    .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                    //设置消费监听器。
                    .setMessageListener(messageView -> {
                        //处理消息并返回消费结果。
                        // LOGGER.info("Consume message={}", messageView);
                        System.out.println("current consumer is " + gray + ", Consume Message content: " + charset.decode(messageView.getBody()).toString());
                        return ConsumeResult.SUCCESS;
                    })
                    .build();
            Thread.sleep(Long.MAX_VALUE);
        }catch(Exception e) {
            e.printStackTrace();
        }
         */

        subMsg();
    }


    private void subMsg() {

        Charset charset = Charset.forName("UTF-8");

        Properties properties = new Properties();
        // 您在控制台创建的Group ID。
        properties.put(PropertyKeyConst.GROUP_ID, "xl-test-group");
        // AccessKey ID，阿里云身份验证标识。获取方式，请参见本文前提条件中的获取AccessKey。
        properties.put(PropertyKeyConst.AccessKey, "kuUR1WQF4JkFT53A");
        // AccessKey Secret，阿里云身份验证密钥。获取方式，请参见本文前提条件中的获取AccessKey。
        properties.put(PropertyKeyConst.SecretKey, "I5yi1WuGxe4Lzhtl");
        // 设置TCP协议接入点，进入控制台的实例详情页面的TCP协议客户端接入点区域查看。
        properties.put(PropertyKeyConst.NAMESRV_ADDR,
                mqAddress);
        // 集群订阅方式（默认）。
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        // 广播订阅方式。
        // properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.BROADCASTING);

        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("xl-test", "", new MessageListener() { //订阅多个Tag
            public Action consume(Message message, ConsumeContext context) {

                System.out.println("current consumer is " + gray + ", Consume Message content: " + new String(message.getBody()));
                return Action.CommitMessage;
            }
        });


        consumer.start();

    }
}
