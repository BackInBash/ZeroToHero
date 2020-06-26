package de.backinbash.filescanner;

public class JFile {
    public JFile(String Path, String Hash) {
        this.Path = Path;
        this.Hash = Hash;
    }

    public JFile() {
    }

    public String Path;
    public String Hash;
}