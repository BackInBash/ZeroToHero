package de.backinbash.filescanner;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class scanner {
    public String calculateMD5(String path) throws IOException {
        try (InputStream is = Files.newInputStream(Paths.get(path))) {
            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(is);
            return md5;
        }
    }

    public List<JFile> scan(String path) throws IOException {
        System.out.println("Start Scanner...");
        List<JFile> files = new ArrayList<JFile>();
        Files.walk(Paths.get(path)).filter(Files::isRegularFile).forEach(file -> {
            try {
                files.add(new JFile(file.toAbsolutePath().toString(), calculateMD5(file.toAbsolutePath().toString())));
            } catch (IOException ex) {
                Logger.getLogger(scanner.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return files;
    }
}