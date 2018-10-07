FROM postgres:9.6
MAINTAINER haroun3amri@ieee.org

ENV POSTGRES_USER root
ENV POSTGRES_PASSWORD root
ENV POSTGRES_DB catalogue

ADD ./init.sql /docker-entrypoint-initdb.d/

