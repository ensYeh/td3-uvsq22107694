package fr.uvsq.cprog.collex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * DNS database: loads entries from a text file and allows lookup/add operations.
 */
public class Dns {
    private final Map<NomMachine, DnsItem> byName = new HashMap<>();
    private final Map<AdresseIP, DnsItem> byIp = new HashMap<>();
    private final Path dbPath;

    /**
     * Load DNS using a properties file from the classpath named 'dns.properties' with key 'db.file'.
     * If the properties file or key is missing, an IOException is thrown.
     */
    public Dns() throws IOException {
        Properties p = new Properties();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dns.properties")) {
            if (is == null) {
                throw new IOException("dns.properties not found on classpath");
            }
            p.load(is);
        }
        String db = p.getProperty("db.file");
        if (db == null || db.isBlank()) {
            throw new IOException("db.file property not set in dns.properties");
        }
        this.dbPath = Path.of(db);
        loadFromFile();
    }

    /**
     * Construct a Dns backed by the given file path (for tests or explicit control).
     */
    public Dns(Path dbPath) throws IOException {
        this.dbPath = Objects.requireNonNull(dbPath);
        loadFromFile();
    }

    private void loadFromFile() throws IOException {
        byName.clear();
        byIp.clear();
        if (!Files.exists(dbPath)) {
            // create empty file to allow later writes
            Files.createDirectories(dbPath.getParent() == null ? Path.of(".") : dbPath.getParent());
            Files.write(dbPath, Collections.emptyList(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return;
        }
        List<String> lines = Files.readAllLines(dbPath);
        for (String line : lines) {
            String t = line.trim();
            if (t.isEmpty()) continue;
            // ignore comments starting with #
            if (t.startsWith("#")) continue;
            DnsItem item = DnsItem.parseLine(t);
            byName.put(item.getNom(), item);
            byIp.put(item.getIp(), item);
        }
    }

    public DnsItem getItem(NomMachine nom) {
        return byName.get(nom);
    }

    public DnsItem getItem(AdresseIP ip) {
        return byIp.get(ip);
    }

    /**
     * Return items for a given domain (case-insensitive). The returned list is sorted by host name.
     */
    public List<DnsItem> getItems(String domain) {
        if (domain == null) return List.of();
        String d = domain.trim();
        List<DnsItem> result = byName.values().stream()
                .filter(i -> i.getNom().getDomain().equalsIgnoreCase(d))
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.sort(result, (a, b) -> a.getNom().compareTo(b.getNom()));
        return result;
    }

    /**
     * Add an item and persist the database. Throws IllegalStateException if name or ip already exists.
     */
    public void addItem(AdresseIP ip, NomMachine nom) throws IOException {
        if (byIp.containsKey(ip)) {
            throw new IllegalStateException("ERREUR : L'adresse IP existe déjà !");
        }
        if (byName.containsKey(nom)) {
            throw new IllegalStateException("ERREUR : Le nom de machine existe déjà !");
        }
        DnsItem item = new DnsItem(nom, ip);
        byName.put(nom, item);
        byIp.put(ip, item);
        persist();
    }

    private void persist() throws IOException {
        List<String> lines = byName.values().stream()
                .map(DnsItem::toStorageLine)
                .sorted()
                .collect(Collectors.toList());
        Files.write(dbPath, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
