/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.backinbash.filescanner;

/**
 *
 * @author BackInBash
 */
public class JMismatch extends JFile {
    public JMismatch (String Path, String Hash, String Type){
        this.Path = Path;
        this.Hash = Hash;
        this.Type = Type;
    }
    public String Type;    
}
