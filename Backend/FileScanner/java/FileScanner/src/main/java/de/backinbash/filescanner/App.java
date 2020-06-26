package de.backinbash.filescanner;

import de.backinbash.filescanner.scanner;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting FileScanner...");
        if (args.length == 0) {
            System.out.print("No CLI Args given...\n --path Path to Files to Scann\n --check |-c Enable Check");
            System.exit(1);
        }

        Config config = new Config(false);
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--path":
                    config.Path = args[i + 1];
                    break;
                case "-c":
                case "--check":
                    config.Check = true;
                    break;
            }
        }

        if (config.Path == null) {
            System.out.println("Error no Path given");
            System.exit(1);
        }

        if(!config.Check){
            scanner scan = new scanner();
            ArrayList<JFile> files = scan.scan(config.Path);
            System.out.println("w");
        }

    }
}
