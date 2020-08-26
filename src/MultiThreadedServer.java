import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MultiThreadedServer {
    private Socket socket;
    private int sessionNo = 1;
    public static void main(String[] args) {
        System.out.println("SERVER STARTET");
        new MultiThreadedServer();
    }

    public MultiThreadedServer() {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(10000);
                while (true) {
                    System.out.println(new Date() + ": Wait for players to join session " + sessionNo + '\n');

                    Socket player1 = serverSocket.accept();
                    System.out.println(new Date() + ": Player 1 joined session " + sessionNo + '\n');
                    System.out.println("Player 1's IP address" + player1.getInetAddress().getHostAddress() + '\n');

                    /*DataOutputStream dataOutputStream = new DataOutputStream(player1.getOutputStream());
                    dataOutputStream.writeInt(1);*/

                    Socket player2 = serverSocket.accept();
                    System.out.println(new Date() + ": Player 2 joined session " + sessionNo + '\n');
                    System.out.println("Player 2's IP address" + player2.getInetAddress().getHostAddress() + '\n');

                    /*DataOutputStream dataOutputStream2 = new DataOutputStream(player1.getOutputStream());
                    dataOutputStream2.writeInt(1);*/
                    new Thread(new HandleAClient(player1, player2)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    class HandleAClient implements Runnable, Constants {
        Socket player1;
        Socket player2;

        public HandleAClient(Socket player1, Socket player2) {
            this.player1 = player1;
            this.player2 = player2;
        }

        @Override
        public void run() {
            try {
                DataInputStream fromPlayer1 = new DataInputStream(player1.getInputStream());
                DataOutputStream toPlayer1 = new DataOutputStream(player1.getOutputStream());
                DataInputStream fromPlayer2 = new DataInputStream(player2.getInputStream());
                DataOutputStream toPlayer2 = new DataOutputStream(player2.getOutputStream());


                while (true) {
                    //Recieve move from player 1.
                    int player1Hand = fromPlayer1.readInt();
                    System.out.println(player1Hand);

                    //Recieve move from player 2.
                    int player2Hand = fromPlayer2.readInt();
                    System.out.println(player2Hand);


                    if (player2Hand > 21 && player1Hand > 21) {
                        toPlayer1.writeUTF("player 1 got: " + player1Hand + " and player 2 got: " + player2Hand + ". ITS A DRAW!!");
                        toPlayer2.writeUTF("player 1 got: " + player1Hand + " and player 2 got: " + player2Hand + ". ITS A DRAW!!");
                        break;
                    } else if (player1Hand > 21 && player2Hand < 21) {
                        toPlayer1.writeUTF("player 1 got: " + player1Hand + " and player 2 got: " + player2Hand + ". Player 2 won the game");
                        toPlayer2.writeUTF("player 1 got: " + player1Hand + " and player 2 got : " + player2Hand + ". Player 2 won the game");
                        break;
                    } else if (player2Hand > 21 && player1Hand < 21) {
                        toPlayer1.writeUTF("player 1 got: " + player1Hand + " and player 2 got : " + player2Hand + ". Player 1 won the game");
                        toPlayer2.writeUTF("player 1 got: " + player1Hand + " and player 2 got : " + player2Hand + ". Player 1 won the game");
                        break;
                    } else if (player1Hand == player2Hand) {
                        toPlayer1.writeUTF("player 1 got: " + player1Hand + " and player 2 got : " + player2Hand + ". ITS A DRAW!!!");
                        toPlayer2.writeUTF("player 1 got: " + player1Hand + " and player 2 got : " + player2Hand + ". ITS A DRAW!!!");
                        break;
                    } else if (player1Hand > player2Hand) {
                        toPlayer1.writeUTF("player 1 got: " + player1Hand + " and player 2 got : " + player2Hand + ". Player 1 won the game");
                        toPlayer2.writeUTF("player 1 got: " + player1Hand + " and player 2 got : " + player2Hand + ". Player 1 won the game");
                        break;
                    } else {
                        toPlayer1.writeUTF("player 1 got: " + player1Hand + " and player 2 got : " + player2Hand + ". Player 2 won the game");
                        toPlayer2.writeUTF("player 1 got: " + player1Hand + " and player 2 got : " + player2Hand + ". Player 2 won the game");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
