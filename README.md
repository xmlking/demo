# Demo


### Start
```bash
docker-compose up mysql
docker-compose up kafka
docker-compose up schema-registry
```


### Setup 
> Setup IntelliJ

    1. Install *Lombok Plugin* for IntelliJ
    2. Settings > Build, Execution, Deployment > Compiler > Annotation Processors. Tick 'Enable annotation processing'.

### Build
```bash
gradle build
```
> generate build/libs/demo-0.0.1-SNAPSHOT.jar

### Run
```bash
gradle bootRun
```


### Test

`cd /Developer/Applications/confluent-5.2.1`

> producer 
```bash
./bin/kafka-avro-console-producer \
--broker-list localhost:9092 \
--topic customer-change \
--property value.schema='{"type":"record","name":"CustomerDto", "namespace": "com.example.demo.domain", "fields":[{"name":"id","type":"long"}, {"name":"city","type":"string"}]}'


{"id": 9223372036854775805, "city": "river"}
{"id": 2, "city": "irvine"}
{"id": 3, "city": "costa"}
{"id": 4, "city": "costa"}
```

> consumer
```bash
./bin/kafka-avro-console-consumer \
--bootstrap-server localhost:9092 \
--topic customer-change
```

> drain
Temporarily update the retention time on the topic to one second:

./bin/kafka-configs --zookeeper localhost:2181 --entity-type topics --alter --entity-name customer-change --add-config retention.ms=1000
./bin/kafka-configs --zookeeper localhost:2181 --entity-type topics --alter --entity-name customer-change --delete-config retention.ms
./bin/kafka-configs --zookeeper localhost:2181 --describe --entity-type topics --entity-name customer-change


./bin/kafka-topics --describe --zookeeper localhost:2181 --topic customer-change
./bin/kafka-topics --list --zookeeper localhost:2181
./bin/kafka-topics --zookeeper localhost:2181 --delete --topic output3

https://ronnieroller.com/kafka/cheat-sheet
https://docs.confluent.io/current/streams/developer-guide/datatypes.html

https://github.com/bobypt/kafka-streams-demo/blob/master/stream-processor/src/main/java/org/bpt/demo/processors/AvroToTextProcessor.java


Delegate Build And Run Actions To Gradle In IntelliJ IDEA
https://mrhaki.blogspot.com/2016/11/gradle-goodness-delegate-build-and-run.html