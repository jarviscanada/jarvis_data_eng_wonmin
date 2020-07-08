#!/bin/bash
set -e 

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE DATABASE jrvstrading;
    GRANT ALL PRIVILEGES ON DATABASE jrvstrading TO postgres;

    CREATE DATABASE jrvstrading_test;
    GRANT ALL PRIVILEGES ON DATABASE jrvstrading_test TO postgres;
EOSQL
