package ru.moore.lesson6;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Publisher {

    private static final String EXCHANGE_NAME = "CHANNEL";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            
            System.out.println("Напечатайте вашу новость.");

            Scanner in = new Scanner(System.in);
            String topic;
            do {
                String inputCommand = in.nextLine();
                String[] separateInput = inputCommand.split(" ");
                topic = separateInput[0];
                StringBuilder outMessage= new StringBuilder();
                for (int i = 2; i < separateInput.length; i++) {
                    outMessage.append(separateInput[i]).append(" ");
                }
                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
                channel.basicPublish(EXCHANGE_NAME, topic, null, outMessage.toString().getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] отправлено сообщение в канал - " + topic + " сообщение - " + outMessage);
            } while (!topic.equals("Выход"));


        }
    }

}
