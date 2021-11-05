package fa.training.problem02.dao;

import fa.training.problem02.domains.entities.WorkingHistory;
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
public class WorkingHistoryDAO {
    private static final String INSERT = "INSERT INTO working_histories (dept_no, emp_no, from_date) VALUES (?,?,?);";

    private static final String FIND_BY_EMPLOYEE_ID = "SELECT * FROM working_histories w WHERE w.emp_no =?";

    private static final String UPDATE = "UPDATE working_histories w SET w.to_date =? WHERE w.dept_no =? AND w.emp_no =?";

    private static final String FIND_ALL_WITH_FILTER = "SELECT * FROM working_histories w WHERE w.from_date >=? AND w.to_date <=?";

    public List<WorkingHistory> findByEmpID(int empID) {
        List<WorkingHistory> response = new ArrayList<>();
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMPLOYEE_ID)) {

            preparedStatement.setInt(1, empID);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                WorkingHistory result = new WorkingHistory();
                result.setDeptNo(rs.getInt(1));
                result.setEmpNo(rs.getInt(2));
                result.setFromDate(rs.getDate(3));
                result.setToDate(rs.getDate(4));
                response.add(result);
            }
        } catch (SQLException e) {
            Utils.printSQLException(e);
        }

        return response;
    }

    public void persist(WorkingHistory workingHistory) {
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {

            preparedStatement.setInt(1, workingHistory.getDeptNo());
            preparedStatement.setInt(2, workingHistory.getEmpNo());
            preparedStatement.setDate(3, workingHistory.getFromDate());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            Utils.printSQLException(e);
        }

    }

    public void update(WorkingHistory workingHistory) {
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setDate(1, workingHistory.getToDate());
            preparedStatement.setInt(2, workingHistory.getDeptNo());
            preparedStatement.setInt(3, workingHistory.getEmpNo());
            preparedStatement.executeUpdate();
            System.out.println("Update successfully!");
        } catch (SQLException e) {
            Utils.printSQLException(e);
        }

    }

    /**
     * Return all employee IDs satisfy requirement
     * 
     * @param start
     * @param end
     * @return
     */
    public List<Integer> employeeIDFilteredByTimePeriod(String start, String end) {
        List<Integer> response = new ArrayList<>();
        try (Connection connection = Persistence.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_WITH_FILTER)) {
            preparedStatement.setString(1, start);
            preparedStatement.setString(2, end);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int result = rs.getInt(2);
                response.add(result);
            }

        } catch (SQLException e) {
            Utils.printSQLException(e);
        }

        return response;
    }

}
