//package example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.time.Instant;
import java.util.List;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApi;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxTable;
import com.influxdb.query.FluxRecord;


public class Hello {
    public static void main(final String[] args) {

        //token for system's influx
        //String token = "MglMIe5ROZDDmdzIZGoUUhPtsct8Zdf_YKep_M1GQjEzRS8Akx5HbS6B1Sp-YYsULb_GSSDpzaS6FAlsTxqtkg==";

        //token for docker's influx
        String token = "eqWXHf4eBnGcaSg6x-n_JwnPM2gkL4MyOWlwVCsZzxOOhtowigqE5L4Z51IfV77eThUKuq4YLsIluhu7270xxw==";

        String bucket = "r";
        String org = "demo";

        //client for system's influx
        //InfluxDBClient client = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray());

        //client for docker's influx
        InfluxDBClient client = InfluxDBClientFactory.create("http://172.22.0.2:8086", token.toCharArray());
        double value = 23.43234543;
        for(int i=0;i<1000000;i++){
            String data = "mem,host=host1 used_percent="+value;
            try (WriteApi writeApi = client.getWriteApi()) {
                writeApi.writeRecord(bucket, org, WritePrecision.NS, data);
            }
            value++;
        }
    }
}

//        Point point = Point
//                .measurement("mem")
//                .addTag("host", "host1")
//                .addField("used_percent", 23.45234543)
//                .time(Instant.now(), WritePrecision.NS);
//
//        try (WriteApi writeApi = client.getWriteApi()) {
//            writeApi.writePoint(bucket, org, point);
//        }
//        String query = String.format("from(bucket: \"%s\") |> range(start: %s)", bucket,"-1h");
//        List<FluxTable> tables = client.getQueryApi().query(query, org);
//        for(FluxTable fluxTable : tables){
//            //System.out.println(fluxTable.toString());
//            List<FluxRecord> records = fluxTable.getRecords();
//            for (FluxRecord fluxRecord : records) {
//                System.out.println(fluxRecord.getTime() + ": " + fluxRecord.getValueByKey("_value"));
//            }
//        }
//        //System.out.println(tables.toString());
//        try {
//            Process process = Runtime.getRuntime().exec("cat /proc/meminfo");
//            BufferedReader bufferedReader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//            String value = bufferedReader.readLine();
//            System.out.println(value);
//            process = Runtime.getRuntime().exec("cat /proc/3874/stat");
//            bufferedReader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//            value = bufferedReader.readLine();
//            System.out.println(value);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }