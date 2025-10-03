package fr.uvsq.cprog.collex;

public class LookupByIpCommande implements Commande {
    private final AdresseIP ip;

    public LookupByIpCommande(AdresseIP ip) {
        this.ip = ip;
    }

    @Override
    public String execute(Dns dns) {
        DnsItem it = dns.getItem(ip);
        return it == null ? "" : it.getNom().qualified();
    }
}
