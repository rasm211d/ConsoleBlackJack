import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 implements Constants{
    private static DataInputStream dataInputStream;
    private static DataOutputStream dataOutputStream;
    private static String host = "localhost";
    private static Scanner scanner;
    private static int clientNo = 2;

    public static void main(String[] args) throws IOException {
        //String input;
        //int number;
        try {
            Socket socket = new Socket(host, 10000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            /*try {
                int player = dataInputStream.readInt();
                if (player == PLAYER1) {
                    System.out.println("I ARE PLAYER WON");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            while (true) {
                scanner = new Scanner(System.in);
                int number = (int) (Math.random() * (11 - 1)) + 1;
                System.out.println("Your first number: " + number);

                while (true) {
                    System.out.println("Write hit for another number, write stay to stop");
                    String input = scanner.nextLine();
                    if (input.equalsIgnoreCase("hit")) {
                        int number1 = (int) (Math.random() * (11 - 1)) + 1;
                        System.out.println("You got number: " + number1);
                        number += number1;
                        System.out.println("Your number is: " + number);
                        if (number > 21) {
                            System.out.println("You hit too hard and your number is now: " + number);
                            try {
                                dataOutputStream.writeInt(number);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } try {
                                String status = dataInputStream.readUTF();
                                System.out.println(status);
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (input.equalsIgnoreCase("stay")) {
                        System.out.println("You decided to stay, your number is: " + number);
                        try {
                            dataOutputStream.writeInt(number);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            String status = dataInputStream.readUTF();
                            System.out.println(status);
                            break;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;

            }
            System.exit(0);

        }).start();
    }
}
