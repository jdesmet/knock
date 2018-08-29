/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desmet.yo.knockknock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jd3714
 */
public class KnockHandler extends Thread {
  private static final Logger LOGGER = Logger.getLogger(KnockHandler.class.getName());
  private final Socket clientSocket;
  static { LOGGER.setLevel(Level.OFF); }

  public static void handle(ServerSocket serverSocket) throws IOException {
    KnockHandler handler = new KnockHandler(serverSocket.accept());
    handler.start();
  }

  private KnockHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }
  
  @Override
  public void run() {
    LOGGER.log(Level.INFO, "Waiting for incomming connect");
    try (
      Socket socket = clientSocket; // Autoclose Socket!!
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    ) {
        LOGGER.log(Level.INFO, "KnockClient connection accepted at port {0}", socket.getLocalPort());
        while (true) {
          // Conversation Loop
          final String inputLine = in.readLine();
          if (inputLine == null) {
            LOGGER.log(Level.WARNING, "  Unexpected end on the conversation! ...");
            return;
          }
          switch (inputLine) {
            case "knock-knock":
              LOGGER.log(Level.INFO, "  Got the Knock request.");
              LOGGER.log(Level.INFO, "  Responding with: Living at port {0}", socket.getLocalPort());
              out.println("Living at port " + socket.getLocalPort());
              break;
            case "bye":
              out.println("bye");
              LOGGER.log(Level.INFO, "  Client asked to stop conversation ...");
              return;
            default:
              LOGGER.log(Level.WARNING, "  Unknown command received, not part of Knock protocol.");
              break;
          }
        }
    } catch (IOException ex) {
      Logger.getLogger(KnockHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
