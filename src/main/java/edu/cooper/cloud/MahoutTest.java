package edu.cooper.cloud;


import java.io.*;
import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.classifier.AbstractVectorClassifier;
import org.apache.mahout.classifier.ClassifierResult;
import org.apache.mahout.classifier.naivebayes.*;
import org.apache.mahout.classifier.naivebayes.training.TrainNaiveBayesJob;


public class MahoutTest {

    public static void main(String[] args) throws IOException, TasteException {

        System.out.println("Hello World ");
        System.out.println("Training");
        for (String word: args){
            System.out.println(word);
        }

        String input = "datasets/iris.data";
        String output= "output/out.out";

        FileSystem fs = null;
        SequenceFile.Writer writer;
        Configuration conf = new Configuration();
        TrainNaiveBayesJob trainNaiveBayes = new TrainNaiveBayesJob();
        trainNaiveBayes.setConf(conf);
//        trainNaiveBayes.run()l
//        https://svn.apache.org/repos/asf/mahout/tags/mahout-0.7/core/src/test/java/org/apache/mahout/classifier/naivebayes/NaiveBayesTest.java
        NaiveBayesModel nbMobel = NaiveBayesModel.fromMRTrainerOutput(new Path(output), conf);
        AbstractVectorClassifier classifier = new StandardNaiveBayesClassifier(nbMobel);



//        BufferedReader reader = new BufferedReader(new FileReader(output));

//        CSVVectorIterator it = new CSVVectorIterator(reader);
//        List<NamedVector> vectors = new ArrayList<NamedVector>();
//        while(it.hasNext()){
//            vectors.add(new NamedVector(it.next(),"Test"));
//        }
//
//        fs = FileSystem.get(conf);
//        Path path = new Path("output/out.seq");
//        writer = new SequenceFile.Writer(fs, conf, path, Text.class, VectorWritable.class);
//
//        VectorWritable vec = new VectorWritable();
//        for (NamedVector vector : vectors) {
//            vec.set(vector);
//            writer.append(new Text(vector.getName()), vec);
//        }
//        writer.close();

//        BayesParameters bayesParameters = new BayesParameters();
//        bayesParameters.setGramSize( 1 );
//        bayesParameters.set("verbose", "true");
//        bayesParameters.set( "classifierType", "bayes" );
//        bayesParameters.set( "defaultCat", "OTHER" );
//        bayesParameters.set( "encoding", "UTF-8" );
//        bayesParameters.set( "alpha_i", "1.0" );
//        bayesParameters.set( "dataSource", "hdfs" );
//        bayesParameters.set("basePath", output);
//
////        Train the parameters
//        TrainClassifier.trainNaiveBayes(new Path(input), new Path(output), bayesParameters);

//        Make Classifier
//        Algorithm algorithm = new BayesAlgorithm();
//        Datastore datastore = new InMemoryBayesDatastore( bayesParameters );
//        ClassifierContext classifier = new ClassifierContext( algorithm, datastore );
//        classifier.initialize();
//
//        BufferedReader reader = new BufferedReader( new FileReader( input ) );
//        String entry = reader.readLine();
//         while( entry != null ) {
//            List<String> document = Arrays.asList(entry.split("\\s*,\\s*"));
////            List< String > document = new NGrams( entry,
////                    Integer.parseInt( bayesParameters.get( "gramSize" ) ) )
////                    .generateNGramsWithoutLabel();
//
//            ClassifierResult result =
//                    classifier.classifyDocument( document.toArray( new String[document.size()]),
//                            bayesParameters.get( "defaultCat" ) );
//            entry = reader.readLine();
//            System.out.println(result.getLabel()+"  " +result.getScore());
//        }



//
//        // Create a data source from the CSV file
//        File userPreferencesFile = new File("datasets/data.dat");
//        DataModel dataModel = new FileDataModel(userPreferencesFile);
//
//        UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
//        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(2, userSimilarity, dataModel);
//
//        // Create a generic user based recommender with the dataModel, the userNeighborhood and the userSimilarity
//        Recommender genericRecommender =  new GenericUserBasedRecommender(dataModel, userNeighborhood, userSimilarity);

//        // Recommend 5 items for each user
//        for (LongPrimitiveIterator iterator = dataModel.getUserIDs(); iterator.hasNext();)
//        {
//            long userId = iterator.nextLong();
//
//            // Generate a list of 5 recommendations for the user
//            List<RecommendedItem> itemRecommendations = genericRecommender.recommend(userId, 5);
//
//            System.out.format("User Id: %d%n", userId);
//
//            if (itemRecommendations.isEmpty())
//            {
//                System.out.println("No recommendations for this user.");
//            }
//            else
//            {
//                // Display the list of recommendations
//                for (RecommendedItem recommendedItem : itemRecommendations)
//                {
//                    System.out.format("Recommened Item Id %d. Strength of the preference: %f%n", recommendedItem.getItemID(), recommendedItem.getValue());
//                }
//            }
//        }

    }
}
