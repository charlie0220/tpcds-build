scalaVersion := "2.12.15"
organization := "com.example"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.2.1",
  "org.apache.spark" %% "spark-sql" % "3.2.1",
  "com.databricks" % "spark-sql-perf_2.10" % "0.5.1" from "file:///home/charles/spark-sql-perf/target/scala-2.12/spark-sql-perf_2.12-0.5.1-SNAPSHOT.jar"
)

lazy val tpcds = (project in file("."))
  .settings(
    name := "Tpcds"
  )

assemblyMergeStrategy := { 
  
    case PathList("javax", "servlet", xs @ _*)              => MergeStrategy.discard
    case PathList("javax", "activation", xs @ _*)           => MergeStrategy.discard
    case PathList("javax", "annotation", xs @ _*)           => MergeStrategy.discard
    case PathList("org", "apache", xs @ _*)                 => MergeStrategy.discard
    case PathList("com", "google", xs @ _*)                 => MergeStrategy.discard
    case PathList("com", "esotericsoftware", xs @ _*)       => MergeStrategy.discard
    case PathList("com", "codahale", xs @ _*)               => MergeStrategy.discard
    case PathList("com", "yammer", xs @ _*)                 => MergeStrategy.discard
    case PathList("io", "netty", xs@ _*)                    => MergeStrategy.discard
    case PathList("META-INF", "native-image", "io.netty", xs@ _*)                    => MergeStrategy.discard

    case "about.html"                                       => MergeStrategy.discard
    case "META-INF/ECLIPSEF.RSA"                            => MergeStrategy.discard
    case "META-INF/mailcap"                                 => MergeStrategy.discard
    case "META-INF/mimetypes.default"                       => MergeStrategy.discard
    case "plugin.properties"                                => MergeStrategy.discard
    case "log4j.properties"                                 => MergeStrategy.discard
    case "git.properties"                                   => MergeStrategy.discard
    case PathList("META-INF", "io.netty.versions.properties", xs @ _*) => MergeStrategy.discard
    case "module-info.class"                       => MergeStrategy.discard
    case x => 
      val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
      oldStrategy(x)

}
