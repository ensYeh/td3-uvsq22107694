package fr.uvsq.cprog.collex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class DnsApp {

    private final DnsTUI tui = new DnsTUI();

    /**
     * Run interactive app using default Dns (reads dns.properties from classpath) and System.in/out.
     */
    public void run() {
        try {
            Dns dns = new Dns();
            run(dns, new InputStreamReader(System.in), new OutputStreamWriter(System.out));
        } catch (IOException e) {
            System.err.println("Impossible de charger la base DNS: " + e.getMessage());
        }
    }

    /**
     * Run using provided Dns and I/O streams (testable).
     */
    public void run(Dns dns, Reader in, Writer out) {
        try (BufferedReader reader = new BufferedReader(in); BufferedWriter writer = new BufferedWriter(out)) {
            String line;
            while ((line = reader.readLine()) != null) {
                Commande c = tui.nextCommande(line);
                if (c == null) {
                    writer.write("ERREUR : commande invalide" + System.lineSeparator());
                    writer.flush();
                    continue;
                }
                try {
                    String result = c.execute(dns);
                    if (result == null) {
                        // quit
                        break;
                    }
                    if (!result.isEmpty()) {
                        writer.write(result + System.lineSeparator());
                        writer.flush();
                    }
                } catch (IllegalStateException e) {
                    writer.write(e.getMessage() + System.lineSeparator());
                    writer.flush();
                } catch (IOException e) {
                    try {
                        writer.write("ERREUR I/O : " + e.getMessage() + System.lineSeparator());
                        writer.flush();
                    } catch (IOException ex) {
                        // ignore
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new DnsApp().run();
    }
}
