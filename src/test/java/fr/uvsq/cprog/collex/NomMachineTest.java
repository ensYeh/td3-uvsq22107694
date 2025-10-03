package fr.uvsq.cprog.collex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NomMachineTest {

    @Test
    public void parseQualified() {
        NomMachine nm = NomMachine.parse("www.uvsq.fr");
        assertEquals("www", nm.getHost());
        assertEquals("uvsq.fr", nm.getDomain());
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingDot() {
        NomMachine.parse("local");
    }
}
