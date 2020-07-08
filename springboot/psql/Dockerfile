FROM postgres:9.4

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD password
ENV HOST localhost


EXPOSE 5432

ADD ./sql_ddl/init_script.sh /docker-entrypoint-initdb.d
ADD ./sql_ddl/init_db.sql /docker-entrypoint-initdb.d
ADD ./sql_ddl/schema.sql /docker-entrypoint-initdb.d

RUN psql -h localhost -p 5432 -U "$POSTGRES_USER" jrvstrading-test -f init_db.sql
RUN psql -h localhost -p 5432 -U "$POSTGRES_USER" jrvstrading-test -f schema.sql

