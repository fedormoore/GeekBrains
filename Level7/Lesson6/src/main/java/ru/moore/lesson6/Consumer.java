package ru.moore.lesson6;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Consumer {

    private static final String EXCHANGE_NAME = "CHANNEL";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String queueName = channel.queueDeclare().getQueue();

            System.out.println("Имя очереди: " + queueName);
            System.out.println("Укажите вашу подписку.");

            Scanner in = new Scanner(System.in);

            String topic;
            do {
                String inputCommand = in.nextLine();
                String[] separateInput = inputCommand.split(" ");
                topic = separateInput[1];
                channel.queueBind(queueName, EXCHANGE_NAME, topic);
                System.out.println(" [*] Ждем сообщений от канала - " + topic);
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    System.out.println(" [x] Новое сообщение '" + message + "'");
                };
                channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
                });
            } while (!topic.equals("Выход"));
        }
    }

}
