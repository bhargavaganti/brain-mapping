Distributed Neurophysiological Data Analysis
=====

This project attempts to solve the [DecMeg2014 - Decoding the Human Brain](http://www.kaggle.com/c/decoding-the-human-brain) Machine Learning competition. The goal of this competition is to predict the category of a visual stimulus presented to a subject from the concurrent brain activity. Specifically, the data provided to us is a large corpus of Magnetoencephalography (MEG) data taken from 23 patients. Each patient was shown images of a face as well as images of a scrambled face. The 306 MEG time series segments were recorded at a 1kHz sampling rate. A visualization of the type of data we take as input can be seen here:

![Distributed Neurophysiological Data Analysis](https://raw.githubusercontent.com/schauhan19/brain-mapping/master/MEG_data.png)

Because the corpus of data is many gigabytes large, it is suitable to apply a distributed approach to solving this problem. We chose to use hadoop and mahout for our solution, which has several steps that take use the input data to produce an output file (in csv format) of classified test cases:
  
1. Convert .mat input files to csv (matlab)
2. Preprocess data with map-reduce, normalizing each feature and reducing the dimensionality (hadoop)
3. Convert data to mahout format (csv -> seq -> mahout vectors)
4. Train and Classify using built-in mahout classifiers (mahout)
5. Output the data to a csv

We had several challenges implementing this framework which prevented us from producing a successful output. Our first challenge was hadoop/mahout incompatibility. We were unable to use mahout on the Cooper EE cluster, and eventually reverted to local installations of mahout. Then, we were unable to implement mahout code as outlined in the documentation because of a mahout versioning issue. However, even when we resolved the versioning issue, we were not able to successfully execute mahout examples, and constantly got compilation and runtime errors.

Requirements
=============
1. Java 1.7
2. Hadoop 
3. Maven 3.x

Running the Code
==========
Clean the project. This will download all the required libraries for the project. 
```
mvn clean dependency:resolve
```
Compile
```
mvn compile
```
Running the Normalization HADOOP Job
```
mvn exec:java -Dexec.mainClass="edu.cooper.cloud.Normalize"
```
Running the classifcation MAHOUT Job
```
mvn exec:java -Dexec.mainClass="edu.cooper.cloud.MahoutTest"
```
