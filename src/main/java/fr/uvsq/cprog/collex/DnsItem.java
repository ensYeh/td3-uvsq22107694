package fr.uvsq.cprog.collex;

public final class DnsItem {
    private final NomMachine nom;
    private final AdresseIP ip;

    public DnsItem(NomMachine nom, AdresseIP ip) {
        if (nom == null || ip == null) throw new IllegalArgumentException("Nom ou ip null");
        this.nom = nom;
        this.ip = ip;
    }

    public NomMachine getNom() {
        return nom;
    }

    public AdresseIP getIp() {
        return ip;
    }

    @Override
    public String toString() {
        // display as "ip qualifiedName"
        return ip.toString() + " " + nom.qualified();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DnsItem)) return false;
        DnsItem dnsItem = (DnsItem) o;
        return nom.equals(dnsItem.nom) && ip.equals(dnsItem.ip);
    }

    @Override
    public int hashCode() {
        return nom.hashCode() * 31 + ip.hashCode();
    }

    /**
     * Parse a line from DB.
     * Accepts either:
     * - "nomMachine adresseIP"  
     * - "adresseIP nomMachine"  
     */
    public static DnsItem parseLine(String line) {
        if (line == null) throw new IllegalArgumentException("Ligne nulle");
        String[] toks = line.trim().split("\\s+");
        if (toks.length != 2) throw new IllegalArgumentException("Ligne invalide: " + line);
        String a = toks[0], b = toks[1];
        if (a.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
            AdresseIP ip = AdresseIP.parse(a);
            NomMachine nm = NomMachine.parse(b);
            return new DnsItem(nm, ip);
        } else {
            NomMachine nm = NomMachine.parse(a);
            AdresseIP ip = AdresseIP.parse(b);
            return new DnsItem(nm, ip);
        }
    }

    public String toStorageLine() {
        // store as "nomMachine adresseIP" to match README specification
        return nom.qualified() + " " + ip.toString();
    }
}
