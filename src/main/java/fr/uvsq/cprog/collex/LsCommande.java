package fr.uvsq.cprog.collex;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LsCommande implements Commande {
    private final String domain;
    private final boolean all;

    public LsCommande(String domain, boolean all) {
        this.domain = domain;
        this.all = all;
    }

    @Override
    public String execute(Dns dns) {
        List<DnsItem> items = dns.getItems(domain);
        if (all) {
            // sort by IP
            items = items.stream()
                    .sorted(Comparator.comparing(DnsItem::getIp))
                    .collect(Collectors.toList());
        }
        StringBuilder sb = new StringBuilder();
        for (DnsItem it : items) {
            sb.append(it.getIp().toString()).append(" ").append(it.getNom().qualified()).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
