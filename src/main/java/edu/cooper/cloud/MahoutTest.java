//package edu.cooper.cloud;
//
//
//import java.io.*;
//import java.util.*;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.*;
//import org.apache.hadoop.io.LongWritable;
//import org.apache.hadoop.io.SequenceFile;
//import org.apache.hadoop.io.Text;
//import org.apache.mahout.cf.taste.common.TasteException;
//import org.apache.mahout.classifier.bayes.algorithm.BayesAlgorithm;
//import org.apache.mahout.classifier.bayes.common.BayesParameters;
//import org.apache.mahout.classifier.bayes.datastore.InMemoryBayesDatastore;
//import org.apache.mahout.classifier.bayes.exceptions.InvalidDatastoreException;
//import org.apache.mahout.classifier.bayes.interfaces.Algorithm;
//import org.apache.mahout.classifier.bayes.interfaces.Datastore;
//import org.apache.mahout.classifier.bayes.model.ClassifierContext;
//import org.apache.mahout.math.NamedVector;
//import org.apache.mahout.math.VectorWritable;
//import org.apache.mahout.utils.vectors.csv.CSVVectorIterator;
//import org.apache.mahout.classifier.bayes.*;
//
//
//public class MahoutTest {
//
//    public static void main(String[] args) throws IOException, TasteException, InvalidDatastoreException {
//
//        System.out.println("Hello World ");
//        System.out.println("Training");
//        for (String word: args){
//            System.out.println(word);
//        }
//
//        String input = "datasets/data.dat";
//        String output= "output/outp.out";
//
//        FileSystem fs = null;
//        SequenceFile.Writer writer;
//        Configuration conf = new Configuration();
//        BufferedReader reader = new BufferedReader(new FileReader("datasets/data.dat"));
//
//        CSVVectorIterator it = new CSVVectorIterator(reader);
//        List<NamedVector> vectors = new ArrayList<NamedVector>();
//        while(it.hasNext()){
//            vectors.add(new NamedVector(it.next(),"Test"));
//        }
//
//        fs = FileSystem.get(conf);
//        Path path = new Path("output/out.seq");
//        writer = new SequenceFile.Writer(fs, conf, path, Text.class, Text.class);
//
//        VectorWritable vec = new VectorWritable();
//        for (NamedVector vector : vectors) {
//            vec.set(vector);
//            writer.append(new Text(vector.getName()), vec);
//        }
//        writer.close();
//
//        BayesParameters bayesParameters = new BayesParameters();
//        bayesParameters.setGramSize( 1 );
//        bayesParameters.set( "verbose", "true" );
//        bayesParameters.set( "classifierType", "bayes" );
//        bayesParameters.set( "defaultCat", "OTHER" );
//        bayesParameters.set( "encoding", "UTF-8" );
//        bayesParameters.set( "alpha_i", "1.0" );
//        bayesParameters.set( "dataSource", "hdfs" );
//        bayesParameters.set("basePath", output);
//
////        Train the parameters
//        TrainClassifier.trainNaiveBayes(new Path(input), new Path(output), bayesParameters);
//
////        Make Classifier
//        Algorithm algorithm = new BayesAlgorithm();
//        Datastore datastore = new InMemoryBayesDatastore( bayesParameters );
//        ClassifierContext classifier = new ClassifierContext( algorithm, datastore );
//        classifier.initialize();
//
//
//
////
////        // Create a data source from the CSV file
////        File userPreferencesFile = new File("datasets/data.dat");
////        DataModel dataModel = new FileDataModel(userPreferencesFile);
////
////        UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
////        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
////
////        // Create a generic user based recommender with the dataModel, the userNeighborhood and the userSimilarity
////        Recommender genericRecommender =  new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);
//
////        // Recommend 5 items for each user
////        for (LongPrimitiveIterator iterator = dataModel.getUserIDs(); iterator.hasNext();)
////        {
////            long userId = iterator.nextLong();
////
////            // Generate a list of 5 recommendations for the user
////            List<RecommendedItem> itemRecommendations = genericRecommender.recommend(userId, 5);
////
////            System.out.format("User Id: %d%n", userId);
////
////            if (itemRecommendations.isEmpty())
////            {
////                System.out.println("No recommendations for this user.");
////            }
////            else
////            {
////                // Display the list of recommendations
////                for (RecommendedItem recommendedItem : itemRecommendations)
////                {
////                    System.out.format("Recommened Item Id %d. Strength of the preference: %f%n", recommendedItem.getItemID(), recommendedItem.getValue());
////                }
////            }
////        }
//
//    }
//}
