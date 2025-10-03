package fr.uvsq.cprog.collex;

import java.io.IOException;

/**
 * Represents an invalid or error command that returns a user-friendly message when executed.
 */
public class ErreurCommande implements Commande {
    private final String message;

    public ErreurCommande(String message) {
        this.message = message;
    }

    @Override
    public String execute(Dns dns) throws IOException {
        // simply return the error message to be displayed by the TUI
        return message;
    }
}
