FROM openjdk:8u151-slim
#FROM openjdk:9.0.1-slim

COPY target/jvm-cgroups-tests-1.0.0-SNAPSHOT.jar /app/jvm-cgroups-tests.jar

RUN echo "exec java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -XX:NativeMemoryTracking=summary -XshowSettings:vm -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics -jar /app/jvm-cgroups-tests.jar" > /app/cgroup.sh &&\
    echo "exec java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 -XX:+AlwaysPreTouch -XX:NativeMemoryTracking=summary -XshowSettings:vm -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics -jar /app/jvm-cgroups-tests.jar" > /app/cgroup_pre.sh &&\
    chmod 755 /app/*sh

ENTRYPOINT ["/bin/bash"]
