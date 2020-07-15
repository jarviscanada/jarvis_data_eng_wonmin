FROM postgres:9.4

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD password
ENV HOST localhost

ADD ./sql_ddl/init_db.sql /docker-entrypoint-initdb.d
ADD ./sql_ddl/schema.sql /docker-entrypoint-initdb.d

