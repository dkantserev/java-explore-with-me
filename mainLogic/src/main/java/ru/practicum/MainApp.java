package ru.practicum;


import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@SpringBootApplication

public class MainApp {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, FileNotFoundException {
        SpringApplication.run(MainApp.class, args);

        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/explore";
        Connection connection = DriverManager.getConnection(url, "test", "test");
        ScriptRunner r = new ScriptRunner(connection);
        File t = new File("mainLogic/src/main/resources/test_data_for_feature.sql");
        String q = t.getAbsolutePath();
        Reader reader = new BufferedReader(new FileReader(q));
        r.runScript(reader);
    }

}
