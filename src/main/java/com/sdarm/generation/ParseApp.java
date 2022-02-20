package com.sdarm.generation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParseApp {

    public static Connection getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "vxlafteubyfdnr");
        connectionProps.put("password", "27af9e2765eb53a294c394b35aa0bb047954bff131b44df72f0236bef1c8f7ea");

        return DriverManager.getConnection(
                "jdbc:postgresql://ec2-44-193-188-118.compute-1.amazonaws.com:5432/deq9fv4gdppsot",
                connectionProps);
    }


    public static void generate(String[] args) throws IOException, SQLException {

        try (PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO participant(name, gender, mobile) VALUES (?, ?, ?)")) {
            try (Stream<String> stream = Files.lines(Paths.get("build/resources/main/names.txt"))) {
                for (String line : stream.collect(Collectors.toList())) {
                    List<String> parts = Arrays.stream(line.split(" ")).filter(part -> !part.isEmpty()).collect(Collectors.toList());
                    if (!parts.isEmpty()) {

                        String name = parts.get(1);
                        String surname = parts.get(2);
                        String mobile = parts.get(3);
                        String gender = parts.get(4);

                        preparedStatement.setString(1, name + " " + surname);
                        preparedStatement.setString(2, gender.equalsIgnoreCase("м") ? "MALE" : "FEMALE");
                        preparedStatement.setString(3, mobile);
                        preparedStatement.executeUpdate();
                    }
                }
            }
        }

    }
}
