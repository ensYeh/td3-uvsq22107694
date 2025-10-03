package fr.uvsq.cprog.collex;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

public class DnsAppTest {

    private Path dbDir;
    private Path dbFile;

    @Before
    public void setUp() throws IOException {
        dbDir = Path.of("target/test-dns");
        Files.createDirectories(dbDir);
        dbFile = dbDir.resolve("db.txt");
        Files.writeString(dbFile, "www.uvsq.fr 193.51.31.90\n");
    }

    @After
    public void tearDown() throws IOException {
        if (Files.exists(dbFile)) Files.delete(dbFile);
        if (Files.exists(dbDir)) Files.delete(dbDir);
    }

    @Test
    public void runSession() throws IOException {
        Dns dns = new Dns();
        String input = "www.uvsq.fr\nadd 193.51.25.24 pikachu.uvsq.fr\nls -a uvsq.fr\nquit\n";
        StringReader in = new StringReader(input);
        StringWriter out = new StringWriter();
        new DnsApp().run(dns, in, out);
        String outStr = out.toString();
        // after lookup and add and ls, output should contain the added entry
        assertTrue(outStr.contains("pikachu.uvsq.fr") || outStr.contains("193.51.25.24"));
    }
}
