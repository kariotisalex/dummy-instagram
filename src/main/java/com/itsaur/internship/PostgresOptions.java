package com.itsaur.internship;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import io.vertx.pgclient.PgConnectOptions;
@Parameters(separators = "=")
public class PostgresOptions {
    @Parameter(names = {"--port", "-p"}, description = "Specify port for PostgreSQL ")
    private int port = 5432;

    @Parameter(names = {"--host", "-h"}, description = "Specify the host url for PostgreSQL")
    private String host = "localhost";

    @Parameter(names = {"--database", "-d"}, description = "Specify the database name for PostgreSQL")
    private String database = "postgres";

    @Parameter(names = {"--user", "-u"}, description = "Specify username for PostgreSQL")
    private String user = "postgres";

    @Parameter(names = {"--password", "-P"}, description = "Specify password for PostgreSQL", password = true)
    private String password = "password";

    @Parameter(names = {"--service", "-s"}, description = "Specify \"console\" or \"server\"")
    private String service = "serverdb";

    public PostgresOptions() {
    }

    public PgConnectOptions getPgConnectOptions(){
        return new PgConnectOptions()
                .setPort(port)
                .setHost(host)
                .setDatabase(database)
                .setUser(user)
                .setPassword(password);
    }


    public String getService() {
        return service;
    }
}
