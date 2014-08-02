package ca.pfv.spmf.test;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import ca.pfv.spmf.algorithms.sequentialpatterns.clospan_AGP.AlgoCloSpan;
import ca.pfv.spmf.algorithms.sequentialpatterns.clospan_AGP.items.SequenceDatabase;
import ca.pfv.spmf.algorithms.sequentialpatterns.clospan_AGP.items.creators.AbstractionCreator;
import ca.pfv.spmf.algorithms.sequentialpatterns.clospan_AGP.items.creators.AbstractionCreator_Qualitative;

/**
 * Example of how to use the algorithm PrefixSpan but executing a 
 * post processing step at the end in order to find only the closed 
 * frequent patterns. The output is saved in a given file
 *
 * @author agomariz
 */
public class MainTestPrefixSpan_PostProcessingStepForClosedMining_saveToFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
    	String output = ".//output.txt";
        // Load a sequence database
        double support = (double) 180 / 360;

        boolean keepPatterns = true;
        boolean verbose = false;
        boolean findClosedPatterns = true;
        boolean executePruningMethods = false;

        AbstractionCreator abstractionCreator = AbstractionCreator_Qualitative.getInstance();

        SequenceDatabase sequenceDatabase = new SequenceDatabase();

        sequenceDatabase.loadFile(fileToPath("/Users/finito/NetBeansProjects/spmf_maven/src/ca/pfv/spmf/test/contextPrefixSpan.txt"), support);
        //sequenceDatabase.loadFile(fileToPath("contextCloSpan.txt"), support);
        //sequenceDatabase.loadFile(fileToPath("gazelle.txt"), support);

        //System.out.println(sequenceDatabase.toString());

        AlgoCloSpan algorithm = new AlgoCloSpan(support, abstractionCreator, findClosedPatterns,executePruningMethods);

        algorithm.runAlgorithm(sequenceDatabase, keepPatterns, verbose, output);
        System.out.println(algorithm.getNumberOfFrequentPatterns() + " pattern found.");

        if (keepPatterns) {
            System.out.println(algorithm.printStatistics());
        }
    }

    public static String fileToPath(String filename) throws UnsupportedEncodingException {
        URL url = MainTestCloSpan_saveToFile.class.getResource(filename);
        return java.net.URLDecoder.decode(url.getPath(), "UTF-8");
    }
}
