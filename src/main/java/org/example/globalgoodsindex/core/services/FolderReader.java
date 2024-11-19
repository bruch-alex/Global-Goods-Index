package org.example.globalgoodsindex.core.services;

import java.io.File;

public class FolderReader {

    public static void readFileContent(){
        File folder = new File("../../../resources/data/products");
        File[] listOfFiles = folder.listFiles();

        for (var file : listOfFiles){
            // do something
        }
    }
}

