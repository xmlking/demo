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