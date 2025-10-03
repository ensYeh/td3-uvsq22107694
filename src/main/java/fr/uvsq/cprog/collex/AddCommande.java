package fr.uvsq.cprog.collex;

import java.io.IOException;

public class AddCommande implements Commande {
    private final AdresseIP ip;
    private final NomMachine nom;

    public AddCommande(AdresseIP ip, NomMachine nom) {
        this.ip = ip;
        this.nom = nom;
    }

    @Override
    public String execute(Dns dns) throws IOException {
        dns.addItem(ip, nom);
        return "";
    }
}
