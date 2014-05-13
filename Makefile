backup:	
	cp mahout/core/target/mahout-core-0.9-job.jar  mahout/core/target/mahout-core-0.9-SNAPSHOT-job.jar_backup
	mv mahout/core/target/mahout-core-0.9-job.jar  mahout/core/target/mahout-core-0.9-SNAPSHOT-job.jar
copy: 
	jar uf mahout/core/target/mahout-core-0.9-SNAPSHOT-job.jar target/classes/edu/cooper/cloud/MahoutTest.class
	cp mahout/core/target/mahout-core-0.9-SNAPSHOT-job.jar ./CloudFinal.jar

all: backup, copy
