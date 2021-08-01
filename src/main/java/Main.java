import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    ClassLoader classLoader = getClass().getClassLoader();

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        try (ServerSocket serverSocket = new ServerSocket(2020);) {
            System.out.println("Server ready waiting for connection");
            while (true) {

                   Socket clientSocket = serverSocket.accept();
                   System.out.println(" Client connected");
                   Runnable runnable = new Runnable() {
                       @Override
                       public void run() {
                           try {
                               InputStreamReader reader = new InputStreamReader(clientSocket.getInputStream());
                               BufferedReader br = new BufferedReader(reader);
                               String str = "";
                               String line = br.readLine();
                               while (!line.isBlank()) {
                                   str += line + "\n";
                                   line = br.readLine();
                               }
                               System.out.println(str);
                               String strArr[] = str.split("\n");
                               System.out.println(strArr[0]);
                               String[] path = strArr[0].split(" ");
                               if (path[1].equals("/")) {
                                   for (double i = 0; i < 10000000000000000D; i++) {
                                       System.out.println(i);
                                   }
                                   File fileName = new File(main.classLoader.getResource("index.html").getFile());
                                   FileReader file = new FileReader(fileName);
                                   BufferedReader br1 = new BufferedReader(file);

                                   StringBuilder str1 = new StringBuilder();
                                   String line1;

                                   while ((line1 = br1.readLine()) != null) {
                                       str1.append(line1);
                                   }
                                   PrintWriter write = new PrintWriter(clientSocket.getOutputStream(), true);
                                   write.println("Http/1.1" + 200 + "OK");
                                   write.println("");
                                   write.println(str1);
                                   clientSocket.close();
                               } else if (path[1].equals("/data")) {
                                   File fileName1 = new File(main.classLoader.getResource("data").getFile());
                                   FileReader file1 = new FileReader(fileName1);
                                   BufferedReader br2 = new BufferedReader(file1);

                                   StringBuilder str2 = new StringBuilder();
                                   String line2;

                                   while ((line2 = br2.readLine()) != null) {
                                       str2.append(line2);
                                   }
                                   PrintWriter write = new PrintWriter(clientSocket.getOutputStream(), true);
                                   write.println("Http/1.1" + 200 + "OK");
                                   write.println("");
                                   write.println(str2);
                                   clientSocket.close();

                               } else {
                                   PrintWriter write = new PrintWriter(clientSocket.getOutputStream(), true);
                                   write.println("Http/1.1" + 404);
                                   write.println("");
                                   write.println("<h1> ERROR 404 Page not found</p>");
                                   clientSocket.close();

                               }
                           } catch (IOException e){

                           }
                       }
                   };
                   Thread thread = new Thread(runnable);
                   thread.start();



            }
        }
    }
}
