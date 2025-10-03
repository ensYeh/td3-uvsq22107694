package fr.uvsq.cprog.collex;

import java.util.Objects;

public final class NomMachine implements Comparable<NomMachine> {
    private final String host;   // before first dot
    private final String domain; // after first dot (can contain dots)

    public NomMachine(String host, String domain) {
        if (host == null || host.isBlank()) throw new IllegalArgumentException("Host vide");
        if (domain == null || domain.isBlank()) throw new IllegalArgumentException("Domain vide");
        this.host = host;
        this.domain = domain;
    }

    public static NomMachine parse(String qualified) {
        if (qualified == null) throw new IllegalArgumentException("Nom qualifié null");
        String q = qualified.trim();
        int i = q.indexOf('.');
        if (i <= 0 || i == q.length() - 1) {
            throw new IllegalArgumentException("Nom qualifié invalide : " + qualified);
        }
        String host = q.substring(0, i);
        String domain = q.substring(i + 1);
        return new NomMachine(host, domain);
    }

    public String getHost() {
        return host;
    }

    public String getDomain() {
        return domain;
    }

    public String qualified() {
        return host + "." + domain;
    }

    @Override
    public String toString() {
        return qualified();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NomMachine)) return false;
        NomMachine that = (NomMachine) o;
        return host.equalsIgnoreCase(that.host) && domain.equalsIgnoreCase(that.domain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host.toLowerCase(), domain.toLowerCase());
    }

    @Override
    public int compareTo(NomMachine other) {
        int cmp = this.host.compareToIgnoreCase(other.host);
        if (cmp != 0) return cmp;
        return this.domain.compareToIgnoreCase(other.domain);
    }
}
