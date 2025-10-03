package fr.uvsq.cprog.collex;

import java.util.regex.Pattern;

public class DnsTUI {
    private static final Pattern IP_PATTERN = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");

    /**
     * Parse a user input line and return a Commande.
     */
    public Commande nextCommande(String line) {
        if (line == null) return new QuitCommande();
        String t = line.trim();
        if (t.isEmpty()) return new QuitCommande();
        if (t.equalsIgnoreCase("quit") || t.equalsIgnoreCase("exit")) return new QuitCommande();

        // add command
        if (t.startsWith("add ")) {
            String[] toks = t.split("\\s+", 3);
            if (toks.length != 3) return null;
            String a = toks[1];
            String b = toks[2];
            AdresseIP ip = AdresseIP.parse(a);
            NomMachine nm = NomMachine.parse(b);
            return new AddCommande(ip, nm);
        }

        // ls command: ls [-a] domain
        if (t.startsWith("ls")) {
            String[] toks = t.split("\\s+");
            boolean all = false;
            String domain = null;
            if (toks.length == 2) {
                domain = toks[1];
            } else if (toks.length == 3 && toks[1].equals("-a")) {
                all = true;
                domain = toks[2];
            } else {
                return null;
            }
            return new LsCommande(domain, all);
        }

        // if single token and matches IP pattern -> lookup by IP
        if (IP_PATTERN.matcher(t).matches()) {
            AdresseIP ip = AdresseIP.parse(t);
            return new LookupByIpCommande(ip);
        }

        // otherwise assume it's a qualified name
        try {
            NomMachine nm = NomMachine.parse(t);
            return new LookupByNameCommande(nm);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public String affiche(String result) {
        if (result == null) return "";
        return result;
    }
}
