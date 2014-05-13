/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.cooper.cloud;

import java.io.IOException;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Normalize {



    public static class NormalizeMapper extends Mapper <LongWritable, DoubleArrayWritable, IntWritable,DoubleArrayWritable> {

        private int dataDimFrom;
        private int dataDimTo;
        private long samplesCount;
        private int universeSize;

        @Override
        protected void setup(Context context) throws IOException {
            Configuration conf = context.getConfiguration();
            dataDimFrom = conf.getInt("dataDimFrom", 0);
            dataDimTo = conf.getInt("dataDimTo", 0);
            samplesCount = conf.getLong("samplesCount", 0);
            universeSize = dataDimTo - dataDimFrom + 1;
        }

        @Override
        public void map(
                LongWritable key,
                DoubleArrayWritable array,
                Context context) throws IOException, InterruptedException {
            DoubleWritable[] outArray = new DoubleWritable[universeSize*2];
            for (int c = 0; c < universeSize; c++) {
                outArray[c] = new DoubleWritable(
                        array.get(c+dataDimFrom).get() / samplesCount);
            }
            for (int c = universeSize; c < universeSize*2; c++) {
                double val = array.get(c-universeSize+dataDimFrom).get();
                outArray[c] = new DoubleWritable((val*val) / samplesCount);
            }
            context.write(new IntWritable(1), new DoubleArrayWritable(outArray));
        }

    }

    public static class NormalizeReducer extends Reducer <IntWritable, DoubleArrayWritable, IntWritable, DoubleArrayWritable> {

        private int dataDimFrom;
        private int dataDimTo;
        private int universeSize;

        @Override
        protected void setup(Context context) throws IOException {
            Configuration conf = context.getConfiguration();
            dataDimFrom = conf.getInt("dataDimFrom", 0);
            dataDimTo = conf.getInt("dataDimTo", 0);
            universeSize = dataDimTo - dataDimFrom + 1;
        }

        @Override
        public void reduce(
                IntWritable column,
                Iterable<DoubleArrayWritable> partialSums,
                Context context) throws IOException, InterruptedException {
            DoubleWritable[] outArray = new DoubleWritable[universeSize*2];
            boolean isFirst = true;
            for (DoubleArrayWritable partialSum : partialSums) {
                for (int i = 0; i < universeSize; i++) {
                    if (!isFirst) {
                        outArray[i].set(outArray[i].get() + partialSum.get(i).get());
                    } else {
                        outArray[i] = new DoubleWritable(partialSum.get(i).get());
                    }
                }
                isFirst = false;
            }
            for (int i = universeSize; i < universeSize * 2; i++) {
                double mean = outArray[i-universeSize].get();
                outArray[i].set(Math.sqrt(outArray[i].get() - mean*mean));
            }
            context.write(column, new DoubleArrayWritable(outArray));
        }

    }




    public static class NormalizeCombiner extends Reducer <IntWritable, DoubleArrayWritable, IntWritable, DoubleArrayWritable> {

        private int dataDimFrom;
        private int dataDimTo;
        private int universeSize;

        @Override
        protected void setup(Context context) throws IOException {
            Configuration conf = context.getConfiguration();
            dataDimFrom = conf.getInt("dataDimFrom", 0);
            dataDimTo = conf.getInt("dataDimTo", 0);
            universeSize = dataDimTo - dataDimFrom + 1;
        }

        @Override
        public void reduce(
                IntWritable column,
                Iterable<DoubleArrayWritable> partialSums,
                Context context) throws IOException, InterruptedException {
            DoubleWritable[] outArray = new DoubleWritable[universeSize*2];
            boolean isFirst = true;
            for (DoubleArrayWritable partialSum : partialSums) {
                for (int i = 0; i < universeSize*2; i++) {
                    if (!isFirst) {
                        outArray[i].set(outArray[i].get()
                                + partialSum.get(i).get());
                    } else {
                        outArray[i]
                                = new DoubleWritable(partialSum.get(i).get());
                    }
                }
                isFirst = false;
            }
            context.write(column, new DoubleArrayWritable(outArray));
        }

    }

    public static void main(String[] args) throws Exception {

        String input = "datasets/train_subject01.csv";
        String output= "output/trainX2.csv";

        Configuration conf = new Configuration();
        Map<String, String> env = System.getenv();
        Path coreSiteXml = new Path(env.get("HADOOP_CONF_DIR")+"/core-site.xml");
        Path hdfsSiteXml = new Path(env.get("HADOOP_CONF_DIR")+"/hdfs-site.xml");
        Path yarnSiteXml = new Path(env.get("HADOOP_CONF_DIR")+"/yarn-site.xml");
        Path mapredSiteXml = new Path(env.get("HADOOP_CONF_DIR")+"/mapred-site.xml");
        conf.addResource(coreSiteXml);
        conf.addResource(hdfsSiteXml);
        conf.addResource(yarnSiteXml);
        conf.addResource(mapredSiteXml);

//        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
//        if (otherArgs.length != 2) {
//            System.err.println("Usage: wordcount <in> <out>");
//            System.exit(2);
//        }

        Job job = new Job(conf, "normalize");
        job.setJarByClass(Normalize.class);
        job.setMapperClass(NormalizeMapper.class);
        job.setCombinerClass(NormalizeCombiner.class);
        job.setReducerClass(NormalizeReducer.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(DoubleArrayWritable.class);

        Path inputPath = new Path(input);
        System.out.println(inputPath);
        Path outputPath = new Path(output);
        System.out.println(outputPath);

        FileInputFormat.addInputPath(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
