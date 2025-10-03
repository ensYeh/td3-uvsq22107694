package fr.uvsq.cprog.collex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AdresseIPTest {

    @Test
    public void parseAndToString() {
        AdresseIP ip = AdresseIP.parse("192.168.0.1");
        assertEquals("192.168.0.1", ip.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOctet() {
        AdresseIP.parse("256.0.0.1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidFormat() {
        AdresseIP.parse("1.2.3");
    }
}
