package desmet.yo.knockknock;

import java.io.IOException;
import java.net.SocketException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class KnockClient
{
    String host;
    
    public KnockClient(final String host) {
        this.host = host;
    }
    
    public void start(final int port) throws IOException {
        try (
            Socket socket = new Socket(this.host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ){
            out.println("knock-knock");
            String fromServer = in.readLine();
            if (fromServer.startsWith("Living at port ")) {
                System.out.println("  Server successfully responded: " + fromServer);
                System.out.println("  Telling to stop conversation (Bye) ...");
                out.println("bye");
                fromServer = in.readLine();
                System.out.println("  Server responded: " + fromServer);
            }
            else {
                System.out.println("Garbled response. Something else listening?");
            }
        }
        catch (SocketException x) {
            System.out.println(x.getMessage());
        }
    }
}