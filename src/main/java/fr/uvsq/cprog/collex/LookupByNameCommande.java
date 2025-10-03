package fr.uvsq.cprog.collex;

public class LookupByNameCommande implements Commande {
    private final NomMachine nom;

    public LookupByNameCommande(NomMachine nom) {
        this.nom = nom;
    }

    @Override
    public String execute(Dns dns) {
        DnsItem it = dns.getItem(nom);
        return it == null ? "" : it.getIp().toString();
    }
}
