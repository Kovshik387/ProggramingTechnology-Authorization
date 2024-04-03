package com.auth.ProgrammingTechnology.Authorization.dal;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class Context {
    public static DataSource createDataSource(){
        final String url =
                "jdbc:postgresql://localhost:5432/authorization_db?user=postgres&password=123";
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }
}
