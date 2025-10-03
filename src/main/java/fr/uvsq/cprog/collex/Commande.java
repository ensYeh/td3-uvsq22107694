package fr.uvsq.cprog.collex;

import java.io.IOException;

/**
 * Command interface for DNS actions. Single method that executes the command against a Dns instance
 * and returns the textual result to display (or null/empty for no output).
 */
public interface Commande {
    String execute(Dns dns) throws IOException;
}
