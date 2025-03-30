FROM ubuntu:24.04

RUN apt update && apt install libvips-dev -y
RUN mkdir /jdk
RUN curl --output jdk23.tar.gz https://download.java.net/java/GA/jdk23.0.2/6da2a6609d6e406f85c491fcb119101b/7/GPL/openjdk-23.0.2_linux-aarch64_bin.tar.gz \
    && tar -xf jdk23.tar.gz -C /jdk

RUN ls -al /jdk
ENV JAVA_HOME=/jdk/jdk-23.0.2
ENV PATH=$PATH:$JAVA_HOME/bin
RUN java -version

COPY . /

CMD ./mvnw -X spring-boot:run