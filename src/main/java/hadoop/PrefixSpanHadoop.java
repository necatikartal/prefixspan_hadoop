package hadoop;

/**
 * Created by WCMinor on 05/08/2014.
 */


import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;



public class PrefixSpanHadoop extends Configured implements Tool{
    public int run(String[] arg) throws IOException {


        //creating a JobConf object and assigning a Hadoop job name for identification purposes
        JobConf conf = new JobConf(getConf(), PrefixSpanHadoop.class);
        conf.setJobName("PrefixSpan");

        //Setting configuration object with the Data Type of output Key and Value
        conf.setOutputKeyClass(IntWritable.class);
        conf.setOutputValueClass(Text.class);

        //Providing the mapper and reducer class names
        conf.setMapperClass(PrefixSpanMapper.class);
        conf.setReducerClass(PrefixSpanReducer.class);

        //the hdfs input and output directory to be fetched from the command line
        FileInputFormat.addInputPath(conf, new Path(arg[0]));
        File output = new  File(arg[1]);
        if (output.exists()){
            FileUtils.deleteDirectory(output);
        }
        FileOutputFormat.setOutputPath(conf, new Path(arg[1]));

        JobClient.runJob(conf);

        return 0;

    }
    public static void main(String[] arg) throws Exception{
        //         		get the support from the second argument

        if (arg.length != 2){
            System.out.println("The number of arguments must be 2, \"input path\" and \"output\"");
            System.exit(2);
        }
        int res = ToolRunner.run(new Configuration(), new PrefixSpanHadoop(), arg);
        System.exit(res);
    }
    public static String fileToPath(String filename) throws UnsupportedEncodingException {
        return java.net.URLDecoder.decode(filename,"UTF-8");
    }
}
