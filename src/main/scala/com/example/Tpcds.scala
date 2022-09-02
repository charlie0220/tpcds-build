package com.example

import com.databricks.spark.sql.perf.tpcds.TPCDSTables
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.apache.spark.{SparkContext, SparkConf}

object Tpcds {
    def main(args: Array[String]): Unit = {
        
      // Note: Declare "sqlContext" for Spark 2.x version
        //val sparkconf = new SparkConf().setMaster("k8s://https://192.168.103.45:6443").setAppName("tpcds")
        //val sc = new SparkContext(sparkconf)
        val spark = SparkSession.builder()
            .master("k8s://https://192.168.103.45:6443")
            .appName("tpcds-spark")
            .getOrCreate()
        val sqlContext = new org.apache.spark.sql.SQLContext(spark.sparkContext)
        //  Set:
        // Note: Here my env is using MapRFS, so I changed it to "hdfs:///tpcds".
        // Note: If you are using HDFS, the format should be like "hdfs://namenode:9000/tpcds"
        val rootDir = "hdfs://192.168.103.135:9000/test/spark3" // root directory of location to create data in.
        val databaseName = "tpcds-spark3" // name of database to create.
        val scaleFactor = "10" // scaleFactor defines the size of the dataset to generate (in GB).
        val format = "parquet" // valid spark format like parquet "parquet".
        // Run:
        val tables = new TPCDSTables(sqlContext,
            dsdgenDir = "/opt/spark/examples/jars/tpcds-kit/tools/", // location of dsdgen
            scaleFactor = scaleFactor,
            useDoubleForDecimal = false, // true to replace DecimalType with DoubleType
            useStringForDate = false) // true to replace DateType with StringType

        tables.genData(
            location = rootDir,
            format = format,
            overwrite = true, // overwrite the data that is already there
            partitionTables = true, // create the partitioned fact tables
            clusterByPartitionColumns = true, // shuffle to get partitions coalesced into single files.
            filterOutNullPartitionValues = false, // true to filter out the partition with NULL key value
            tableFilter = "", // "" means generate all tables
            numPartitions = 10) // how many dsdgen partitions to run - number of input tasks.
    
        
        //sql(s"create database $databaseName")
        // Create metastore tables in a specified database for your data.
        // Once tables are created, the current database will be switched to the specified database.
        //tables.createExternalTables(rootDir, "parquet", databaseName, overwrite = true, discoverPartitions = true)
        // Or, if you want to create temporary tables
        // tables.createTemporaryTables(location, format)
  
        // For CBO only, gather statistics on all columns:
        // tables.analyzeTables(databaseName, analyzeColumns = true)


    }
}
