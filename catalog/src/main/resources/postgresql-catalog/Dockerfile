FROM postgres:9.6
MAINTAINER florian.lehmann@etu.unice.fr

ENV POSTGRES_USER root
ENV POSTGRES_PASSWORD root
ENV POSTGRES_DB catalog

ADD ./init.sql /docker-entrypoint-initdb.d/

