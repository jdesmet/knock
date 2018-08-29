package desmet.yo.knockknock;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class KnockListener extends Thread
{
    private final int port;
    private static final Logger LOGGER = Logger.getLogger(KnockListener.class.getName());
    static { LOGGER.setLevel(Level.OFF); }
    //ServerSocket serverSocket;
    static private final Set<Integer> PORTSET = Collections.synchronizedSet(new HashSet<>());
    
    private KnockListener(final int port) {
      this.port = port;
    }
    
    static void listen(final int port) {
      final KnockListener listener = new KnockListener(port);
      listener.start();
    }
    
    @Override
    public void run() {
      try (ServerSocket serverSocket = new ServerSocket(port)) {
        PORTSET.add(port);
        while (true) {
          KnockHandler.handle(serverSocket);
        }
      } catch (IOException x) {
          LOGGER.log(Level.SEVERE, null, x);
      }
    }
}