package fa.training.problem02.dao;

import fa.training.problem02.domains.entities.Department;
import fa.training.problem02.utils.Persistence;
import fa.training.problem02.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Edward Dao
 * @version 1.0
 */
public class DepartmentDAO {

    private static final String SELECT_ALL = "SELECT * FROM  departments;";

    private static final String INSERT_DEPARTMENT = "INSERT INTO departments (dept_name, description) VALUES (?,?);";

    public List<Department> findAll() {
        List<Department> Departments = new ArrayList<>();
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int deptNo = rs.getInt(1);
                String deptName = rs.getString(2);
                String description = rs.getString(3);

                Departments.add(new Department(deptNo, deptName, description));
            }
        } catch (SQLException e) {
            Utils.printSQLException(e);
        }
        return Departments;
    }

    public void persist(Department department) throws SQLException {
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DEPARTMENT)) {
            preparedStatement.setString(1, department.getDeptName());
            preparedStatement.setString(2, department.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("Persisted " + department.toString());
        } catch (SQLException e) {
            Utils.printSQLException(e);
        }
    }

}
