package de.backinbash.filescanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class App {
  
    public static void main(String[] args) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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

        if (!config.Check) {
            scanner scan = new scanner();
            List<JFile> files = scan.scan(config.Path);
            FileWriter output = new FileWriter("output.json");
            gson.toJson(files, output);
            output.close();
        }
        if(config.Check) {
            JsonReader input = new JsonReader(new FileReader("output.json"));
            Comparer compare = new Comparer();
            JFile[] JArray = new Gson().fromJson(input, JFile[].class);
            List<JMismatch> output = compare.Compare(config.Path, Arrays.asList(JArray));
            FileWriter mismatch = new FileWriter("mismatch.json");
            gson.toJson(output, mismatch);
            mismatch.close();
        }
    }
}
