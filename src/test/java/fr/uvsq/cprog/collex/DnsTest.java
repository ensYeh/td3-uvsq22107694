package fr.uvsq.cprog.collex;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.*;

public class DnsTest {

    private Path dbDir;
    private Path dbFile;

    @Before
    public void setUp() throws IOException {
        dbDir = Path.of("target/test-dns");
        Files.createDirectories(dbDir);
        dbFile = dbDir.resolve("db.txt");
        // prepare initial content: one entry
        Files.writeString(dbFile, "www.uvsq.fr 193.51.31.90\n");
    }

    @After
    public void tearDown() throws IOException {
        if (Files.exists(dbFile)) Files.delete(dbFile);
        if (Files.exists(dbDir)) Files.delete(dbDir);
    }

    @Test
    public void loadAndLookup() throws IOException {
        Dns dns = new Dns(); // uses test dns.properties -> target/test-dns/db.txt
        NomMachine nm = NomMachine.parse("www.uvsq.fr");
        DnsItem item = dns.getItem(nm);
        assertNotNull(item);
        assertEquals("193.51.31.90", item.getIp().toString());
    }

    @Test
    public void addItemAndPersist() throws IOException {
        Dns dns = new Dns();
        AdresseIP newIp = AdresseIP.parse("193.51.25.24");
        NomMachine newNm = NomMachine.parse("pikachu.uvsq.fr");
        dns.addItem(newIp, newNm);
        // reload from file to ensure persistence
        Dns dns2 = new Dns();
        DnsItem found = dns2.getItem(newNm);
        assertNotNull(found);
        assertEquals(newIp, found.getIp());
        // the file should contain 2 lines
        List<String> lines = Files.readAllLines(dbFile);
        assertEquals(2, lines.size());
    }

    @Test(expected = IllegalStateException.class)
    public void addDuplicateNameFails() throws IOException {
        Dns dns = new Dns();
        // existing name from initial file
        NomMachine existing = NomMachine.parse("www.uvsq.fr");
        AdresseIP ip = AdresseIP.parse("193.51.25.100");
        dns.addItem(ip, existing);
    }
}
