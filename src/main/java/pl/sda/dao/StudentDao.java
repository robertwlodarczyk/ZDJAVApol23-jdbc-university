package pl.sda.dao;

import pl.sda.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {

    private final Connection connection;

    public StudentDao(Connection connection) {
        this.connection = connection;
    }

    public void add(String firstName, String lastName) {
        String sql = "INSERT INTO UNIVERSITY.STUDENT (FIRST_NAME, LAST_NAME) VALUES (?,?)";
        try (PreparedStatement statement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    System.out.println("Dodano studenta z id" + generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Nie można dodać studenta");
        }
    }

    public void printAllStudents() {
        String sql = "SELECT * FROM UNIVERSITY.STUDENT";
        try (Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            List<Student> studentList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("FIRST_NAME");
                String last_name = resultSet.getString("LAST_NAME");
                Student student = new Student(id, first_name, last_name);
                studentList.add(student);
            }

            resultSet.close();

            studentList.forEach(student -> System.out.println(student.toString()));


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

