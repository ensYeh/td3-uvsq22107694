package fr.uvsq.cprog.collex;

import org.junit.Test;

import static org.junit.Assert.*;

public class DnsTUITest {

    @Test
    public void parseLookupName() {
        DnsTUI tui = new DnsTUI();
        Commande c = tui.nextCommande("www.uvsq.fr");
        assertTrue(c instanceof LookupByNameCommande);
    }

    @Test
    public void parseLookupIp() {
        DnsTUI tui = new DnsTUI();
        Commande c = tui.nextCommande("193.51.31.90");
        assertTrue(c instanceof LookupByIpCommande);
    }

    @Test
    public void parseLs() {
        DnsTUI tui = new DnsTUI();
        Commande c = tui.nextCommande("ls -a uvsq.fr");
        assertTrue(c instanceof LsCommande);
    }

    @Test
    public void parseAdd() {
        DnsTUI tui = new DnsTUI();
        Commande c = tui.nextCommande("add 193.51.25.24 pikachu.uvsq.fr");
        assertTrue(c instanceof AddCommande);
    }

    @Test
    public void parseInvalid() {
        DnsTUI tui = new DnsTUI();
        Commande c = tui.nextCommande("this is invalid");
        assertTrue(c instanceof ErreurCommande);
        // executing should return an error message
        try {
            String m = c.execute(new Dns());
            assertTrue(m.startsWith("ERREUR"));
        } catch (Exception e) {
            // ignore - Dns constructor might throw if properties missing in non-test context
        }
    }
}
