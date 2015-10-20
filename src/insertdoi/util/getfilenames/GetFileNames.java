package insertdoi.util.getfilenames;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetFileNames {
    public static List<String> run(String folderName, String suffix){
        List<String> fileNames = new ArrayList<String>();
        
        File folder = new File(folderName);
        File[] allFiles = folder.listFiles();
        
        for (File file : allFiles) {
            if (file.isFile() && file.getName().endsWith(suffix)) {
                fileNames.add(file.getName());
            }
        }
        
        Collections.sort(fileNames);
        
        return fileNames;
    }
}
