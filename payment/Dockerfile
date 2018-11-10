FROM tomee:8-jre-7.0.2-plus

RUN apt-get update \
    && apt-get install -y sudo netcat

ADD target/payment.war /usr/local/tomee/webapps
ADD src/main/resources/postgresql-42.2.5.jar /usr/local/tomee/lib
ADD src/main/resources/wait_dependencies.sh ./
COPY src/main/resources/tomee.xml /usr/local/tomee/conf

RUN chmod +x wait_dependencies.sh

EXPOSE 8080

CMD ["./wait_dependencies.sh"]
