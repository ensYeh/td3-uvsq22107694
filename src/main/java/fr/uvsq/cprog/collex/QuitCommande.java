package fr.uvsq.cprog.collex;

public class QuitCommande implements Commande {
    @Override
    public String execute(Dns dns) {
        return null; // null indicates quit in our TUI
    }
}
