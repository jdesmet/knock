/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desmet.yo.knockknock;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

// 
// Decompiled by Procyon v0.5.29
// 

public class Main
{
    static BufferedReader stdIn;
    
    public static void main(final String[] args) {
        String read = null;
        String host = "localhost";
        int port = 0;
        try {
            while (true) {
                Main.stdIn = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Which action to take?");
                System.out.println(" [s] Start Server");
                System.out.println(" [t] Test Port");
                System.out.println(" [x] Exit");
                final String c = Main.stdIn.readLine();
                if (c == null) {
                    continue;
                }
                if (c.equalsIgnoreCase("s")) {
                    System.out.print("--> Which port to attach ? [" + port + "] ");
                    read = Main.stdIn.readLine();
                    if (!read.equals("")) {
                        port = Integer.parseInt(read);
                    }
                    KnockListener.listen(port);
                }
                else if (c.equalsIgnoreCase("t")) {
                    System.out.print("--> Which server (host) to reach ? [" + host + "]");
                    read = Main.stdIn.readLine();
                    if (!read.equals("")) {
                        host = read;
                    }
                    final KnockClient k = new KnockClient(host);
                    System.out.print("--> Which port to reach ? [" + port + "] ");
                    read = Main.stdIn.readLine();
                    if (!read.equals("")) {
                        port = Integer.parseInt(read);
                    }
                    try {
                        k.start(port);
                    }
                    catch (IOException x) {
                        x.printStackTrace();
                        System.exit(1);
                    }
                }
                else {
                    if (!c.equalsIgnoreCase("X")) {
                        continue;
                    }
                    System.exit(0);
                }
            }
        }
        catch (IOException x2) {}
    }
    
    static {
        Main.stdIn = null;
    }
}