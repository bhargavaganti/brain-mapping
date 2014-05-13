package edu.cooper.cloud;


import org.apache.hadoop.fs.Path;
import org.apache.mahout.*;
import org.apache.mahout.classifier.*;
import org.apache.mahout.classifier.bayes.algorithm.BayesAlgorithm;
//import org.apache.mahout.classifier.BayesFileFormatter;
//import org.apache.mahout.classifier.ClassifierResult;
//import org.apache.mahout.classifier.bayes.TrainClassifier;
//import org.apache.mahout.classifier.bayes.algorithm.BayesAlgorithm;
//import org.apache.mahout.classifier.bayes.common.BayesParameters;
//import org.apache.mahout.classifier.bayes.datastore.InMemoryBayesDatastore;
//import org.apache.mahout.classifier.bayes.exceptions.InvalidDatastoreException;
//import org.apache.mahout.classifier.bayes.interfaces.Algorithm;
//import org.apache.mahout.classifier.bayes.interfaces.Datastore;
//import org.apache.mahout.classifier.bayes.model.ClassifierContext;
//import org.apache.mahout.vectorizer.DefaultAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

public class MahoutTest {

//    /**
//     * This method permits to make smarter apache mahout.
//     * @param label the label associated to the file.
//     * @param fileToClassify the file associated to the specified label.
//     * @param charset the charset of content of the file.
//     * @param outputDir the directory where the transformed file will be stocked.
//     * @param databaseOutputDir the directory of the apache hadoops database, which contains reference data for future classification.
//     * @throws IOException
//     */
//
//
//    public void training(String label, String fileToClassify, String charset, String outputDir, String databaseOutputDir) throws IOException {
//
//  /*
//   * Take the document and associate to a label inside a file that
//   * respects the apache mahout input format:
//   * [LABEL] _TAB_ [TEXT]
//   * example:
//   * english mahout is a good product.
//   * french mahout est un bon produit.
//   * Note the analyzer =&gt; This is a lucene analyzer, by default apache mahout provide one. I used this one.
//   * In few words the analyzor permits to define how the words will be extracted from your file...
//   */
//        BayesFileFormatter.format(label, new DefaultAnalyzer(), new File(fileToClassify), Charset.forName(charset), new File(outputDir));
//
//
//
//  /*
//   * Here we build the bayes parameters object that permits to define some
//   * information about the way to stock the training data. Mahout use
//   * apache hadoops in background for save the classification data.
//   * See the hadoops documentation to know more about this object.
//   * Just take care to specify the classifierType and the basePath.
//   */
//        BayesParameters bayesParameters = buildBayesParam(charset, databaseOutputDir);
//
//  /*
//  * Start the training !
//  */
//        TrainClassifier.trainNaiveBayes(new Path(outputDir), new Path(databaseOutputDir), bayesParameters);
//
//    }
//
//    private BayesParameters buildBayesParam(String charset, String databaseOutputDir) {
//        BayesParameters bayesParameters = new BayesParameters();
//        bayesParameters.setGramSize(1);
//        bayesParameters.set("verbose", "true"); //If you want to see what happen.
//        bayesParameters.set("classifierType", "bayes");
//        bayesParameters.set("defaultCat", "other"); //The default category to return if a label is not found for a specified text.
//        bayesParameters.set("encoding", charset);
//        bayesParameters.set("alpha_i", "1.0");
//        bayesParameters.set("dataSource", "hdfs");
//        bayesParameters.set("basePath", databaseOutputDir);
//        return bayesParameters;
//    }
//
//
//
//    /**
//     * Ask to mahout to find the good label for the specified content.
//     *
//     * @param contentToClassify
//     *            the content to classify.
//     * @param charset
//     *            the charset of the content.
//     * @param databaseOutputDir
//     *            mahout database directory.
//     * @return label the label retrieved by mahout.
//     * @throws InvalidDatastoreException
//     * @throws IOException
//     */
//    public String searchLabel(String contentToClassify, String charset, String databaseOutputDir) throws InvalidDatastoreException, IOException {
//        //define the algorithm to use
//        Algorithm algorithm = new BayesAlgorithm();
//        //specify the mahout datastore to use. (the path of hadoops database).
//        Datastore datastore = new InMemoryBayesDatastore(buildBayesParam(charset, databaseOutputDir));
//        //initialize the mahout context.
//        ClassifierContext context = new ClassifierContext(algorithm, datastore);
//        context.initialize();
//
//        //Make the search
//        ClassifierResult classifyResult = context.classifyDocument(new String[] { contentToClassify }, "other");
//
//        //Result
//        return classifyResult.getLabel();
//    }


    public static void main(String[] args){
        System.out.println("Hello World ");
        System.out.println("Training");
        for (String word: args){
            System.out.println(word);
        }
        org.apache.mahout.classifier.bayes.interfaces.Algorithm algorithm = new BayesAlgorithm();

    }
}
