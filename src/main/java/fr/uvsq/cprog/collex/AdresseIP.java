package fr.uvsq.cprog.collex;

import java.util.Arrays;

public final class AdresseIP implements Comparable<AdresseIP> {
    private final int[] octets; // length 4

    public AdresseIP(int a, int b, int c, int d) {
        this.octets = new int[]{validate(a), validate(b), validate(c), validate(d)};
    }

    public static AdresseIP parse(String s) {
        if (s == null) throw new IllegalArgumentException("Adresse IP null");
        String[] parts = s.trim().split("\\.");
        if (parts.length != 4) throw new IllegalArgumentException("Adresse IP invalide : " + s);
        int[] o = new int[4];
        try {
            for (int i = 0; i < 4; i++) {
                o[i] = validate(Integer.parseInt(parts[i]));
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Octet non numérique dans : " + s, e);
        }
        return new AdresseIP(o[0], o[1], o[2], o[3]);
    }

    private static int validate(int v) {
        if (v < 0 || v > 255) throw new IllegalArgumentException("Octet hors bornes: " + v);
        return v;
    }

    public int[] getOctets() {
        return Arrays.copyOf(octets, octets.length);
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d.%d", octets[0], octets[1], octets[2], octets[3]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdresseIP)) return false;
        AdresseIP that = (AdresseIP) o;
        return Arrays.equals(octets, that.octets);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(octets);
    }

    @Override
    public int compareTo(AdresseIP other) {
        for (int i = 0; i < 4; i++) {
            int cmp = Integer.compare(this.octets[i], other.octets[i]);
            if (cmp != 0) return cmp;
        }
        return 0;
    }

    public long toLong() {
        long v = 0;
        for (int i = 0; i < 4; i++) {
            v = (v << 8) | (octets[i] & 0xFF);
        }
        return v;
    }
}
