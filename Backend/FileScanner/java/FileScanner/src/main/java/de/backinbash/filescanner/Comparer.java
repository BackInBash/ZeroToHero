/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.backinbash.filescanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BackInBash
 */
public class Comparer {

    public List<JMismatch> Compare(String Path, List<JFile> imported_files) throws IOException {
        scanner scan = new scanner();
        List<JFile> scanned_files = scan.scan(Path);
        List<JMismatch> output = new ArrayList<JMismatch>();

        for (JFile imported : imported_files) {
            boolean found = false;
            for (JFile scanned : scanned_files) {
                if (imported.Path.equals(scanned.Path)) {
                    found = true;
                    if (!imported.Hash.equals(scanned.Hash)) {
                        System.out.println("File Hash Mismatch!");
                        output.add(new JMismatch(scanned.Path, scanned.Hash, "Hash Mismatch"));
                        break;
                    }
                    // All OK
                    break;
                }
            }
            if (found == false) {
                System.out.println("File not Found!");
                output.add(new JMismatch(imported.Path, imported.Hash, "File Not Found"));
            }
        }
        return output;
    }
}
