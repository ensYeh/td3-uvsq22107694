package fr.uvsq.cprog.collex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DnsItemTest {

    @Test
    public void parseBothOrders() {
        DnsItem d1 = DnsItem.parseLine("www.uvsq.fr 193.51.31.90");
        DnsItem d2 = DnsItem.parseLine("193.51.31.90 www.uvsq.fr");
        assertEquals(d1, d2);
    }
}
