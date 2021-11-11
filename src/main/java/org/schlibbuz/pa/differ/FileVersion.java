/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.schlibbuz.pa.differ;

import java.nio.file.Path;

/**
 *
 * @author Stefan Frei <stefan.a.frei@gmail.com>
 */
public class FileVersion {

    Path mod;
    Path original;


    FileVersion(Path mod, Path original) {
        this.mod = mod;
        this.original = original;
    }
}
