package Sem5;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Server {

    public static final int PORT = 8181;

    private static long clientIdCounter = 1L;
    private static Map<Long, User> clients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);
            while (true) {
                final Socket client = server.accept();
                final long clientId = clientIdCounter++;

                User user = new User(clientId, client);
                System.out.println("Подключился новый клиент[" + user + "]");
                clients.put(clientId, user);

                new Thread(() -> {
                    try (Scanner input = user.getInput(); PrintWriter output = user.getOutput()) {
                        output.println("Подключение успешно. Список всех клиентов: " + clients);

                        while (true) {
                            String clientInput = input.nextLine();
                            if (Objects.equals("q", clientInput)) {
                                clients.remove(clientId);
                                clients.values().forEach(it -> it.getOutput().println("Клиент[" + clientId + "] отключился"));
                                break;
                            } else if (Objects.equals("@", clientInput.substring(0, 1))) {
                                long destinationId = Long.parseLong(clientInput.substring(1, 2));
                                User destinationUser = clients.get(destinationId);
                                destinationUser.getOutput().println(clientInput);
                            } else {
                                clients.values().forEach(it -> it.getOutput().println(clientInput));
                            }
                        }
                    }
                }).start();
            }
        }
    }

}
