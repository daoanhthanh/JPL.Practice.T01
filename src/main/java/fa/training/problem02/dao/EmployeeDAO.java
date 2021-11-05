package fa.training.problem02.dao;

import fa.training.problem02.domains.bo.Gender;
import fa.training.problem02.domains.entities.Employee;
import fa.training.problem02.utils.Persistence;
import fa.training.problem02.utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Edward Dao
 * @version 1.0
 */
public class EmployeeDAO {

    private static final String FIND_BY_ID = "SELECT * FROM employees e WHERE e.emp_no =?";

    private static final String SELECT_ALL = "SELECT * FROM  employees";

    private static final String INSERT_EMPLOYEE = "INSERT INTO employees (birth_date, first_name, last_name,"
            + "gender, hired_Date) VALUES (?,?,?,?,?);";

    private static final String UPDATE_EMPLOYEE = "UPDATE employees e SET e.birth_date =?,"
            + " e.first_name =?, e.last_name =?, e.gender =?, e.hired_date =? WHERE e.emp_no =?";

    // private static final String FIND_BY_ID_IN = "SELECT * FROM employees e WHERE
    // e.emp_no IN ?";

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int empNo = rs.getInt(1);
                Date birthDate = rs.getDate(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                Gender gender = Gender.valueOf(rs.getString(5));
                Date hiredDate = rs.getDate(6);
                employees.add(new Employee(empNo, birthDate, firstName, lastName, gender, hiredDate));
            }
        } catch (SQLException e) {
            Utils.printSQLException(e);
        }
        return employees;
    }

    /**
     * 
     * @param ids
     * @return
     */
    public List<Employee> findByIdIn(Collection<Integer> ids) {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int empNo = rs.getInt(1);
                Date birthDate = rs.getDate(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                Gender gender = Gender.valueOf(rs.getString(5));
                Date hiredDate = rs.getDate(6);
                employees.add(new Employee(empNo, birthDate, firstName, lastName, gender, hiredDate));
            }
        } catch (SQLException e) {
            Utils.printSQLException(e);
        }
        return employees.parallelStream().distinct().filter(e -> ids.contains(e.getEmpNo()))
                .collect(Collectors.toList());
    }

    public Employee findById(int id) {
        Employee employee = null;
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int empNo = rs.getInt(1);
                Date birthDate = rs.getDate(2);
                String firstName = rs.getString(3);
                String lastName = rs.getString(4);
                Gender gender = Gender.valueOf(rs.getString(5));
                Date hiredDate = rs.getDate(6);
                employee = new Employee(empNo, birthDate, firstName, lastName, gender, hiredDate);
            }
        } catch (SQLException e) {
            Utils.printSQLException(e);
        }
        return employee;

    }

    /**
     * NOTE + TODO: there are some exceptions that are not captured e.g creation
     * fail for other 2 reasons
     * 
     * @param employee
     * @return
     * @throws SQLException
     */
    public Employee persist(Employee employee) throws SQLException {
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYEE,
                        Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, employee.getBirthDate());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getGender().name());
            preparedStatement.setDate(5, employee.getHiredDate());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    employee.setEmpNo(generatedKeys.getInt(1));

            } catch (SQLException e) {
                System.err.println("Creating user failed, no ID obtained.");
            }
        } catch (SQLException e) {
            Utils.printSQLException(e);
        }
        return employee;
    }

    public boolean update(Employee employee) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_EMPLOYEE);) {
            preparedStatement.setDate(1, employee.getBirthDate());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setString(4, employee.getGender().name());
            preparedStatement.setDate(5, employee.getHiredDate());
            preparedStatement.setInt(6, employee.getEmpNo());

            rowUpdated = preparedStatement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

}
