FROM tomee:8-jre-7.0.2-plus

RUN apt-get update \
    && apt-get install -y sudo netcat

ADD target/orders.war /usr/local/tomee/webapps
ADD src/main/resources /usr/local/tomee/lib
ADD src/main/resources ./
COPY src/main/resources /usr/local/tomee/conf

RUN chmod +x wait_dependencies.sh

EXPOSE 8080

CMD ["./wait_dependencies.sh"]
