package fa.training.problem02.controllers;

import fa.training.problem02.dao.EmployeeDAO;
import fa.training.problem02.dao.WorkingHistoryDAO;
import fa.training.problem02.domains.bo.Gender;
import fa.training.problem02.domains.entities.Department;
import fa.training.problem02.domains.entities.Employee;
import fa.training.problem02.domains.entities.WorkingHistory;
import fa.training.problem02.exceptions.ManagementException;
import fa.training.problem02.utils.Utils;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Edward Dao
 * @version 1.0
 */
public class EmployeeController {
    private final EmployeeDAO repository = new EmployeeDAO();
    private final WorkingHistoryDAO workingHistoryDAO = new WorkingHistoryDAO();
    private final DepartmentController department = new DepartmentController();

    public void findById(int employeeID) {
        Employee e = repository.findById(employeeID);
        if (Utils.isNull(e)) {
            throw new ManagementException(String.format("Cannot find employee with ID = %d", employeeID));
        }
        System.out.println(e);
    }

    /**
     * 
     * @throws SQLException
     */
    public void create() throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose department ID to add employee to:");
        for (Department d : department.getAll()) {
            System.out.println(d);
        }

        int deptId = Integer.parseInt(sc.nextLine().trim());

        Employee employee = new Employee();

        // create first name
        System.out.print("$ Employee's first name: \t");
        employee.setFirstName(sc.nextLine().trim());

        // create last name
        System.out.print("$ Employee's last name: \t");
        employee.setLastName(sc.nextLine().trim());

        // create sex
        System.out.println("$ Employee's gender: ");
        for (int i = 0; i < Gender.values().length; i++) {
            System.out.println("\t" + (i + 1) + ". " + Gender.values()[i]);
        }
        String ans = sc.nextLine().trim();
        switch (ans) {
        case "1":
            employee.setGender(Gender.values()[0]);
            break;
        case "2":
            employee.setGender(Gender.values()[1]);
            break;
        case "3":
            employee.setGender(Gender.values()[2]);
            break;
        // Add more case if it has more type of Gender
        }

        // create date of birth
        System.out.print("$ Employee's birthday (yyyy-mm-dd): \t");
        String birthDate = sc.nextLine().trim();
        employee.setBirthDate(Date.valueOf(birthDate));

        // create hired date
        System.out.print("$ Employee's hired date (yyyy-mm-dd): \t");
        String hiredDate = sc.nextLine().trim();
        employee.setHiredDate(Date.valueOf(hiredDate));
        sc.close();

        employee = repository.persist(employee);
        WorkingHistory workingHistory = new WorkingHistory(deptId, employee.getEmpNo(), employee.getHiredDate());
        workingHistoryDAO.persist(workingHistory);
        System.out.println("Persisted " + employee.toString());

    }

    /**
     *
     * @throws SQLException
     */
    public void update() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nChoose employee ID to update:");
        for (Employee e : repository.findAll()) {
            System.out.println(e);
        }

        int empID = Integer.parseInt(sc.nextLine().trim());
        Employee employee = repository.findById(empID);

        // update first name
        System.out.println("Update first name (press Enter to keep it unchanged)");
        System.out.println("Old first name: " + employee.getFirstName());
        String newFirstName = sc.nextLine().trim();
        if (!newFirstName.isEmpty()) {
            employee.setFirstName(newFirstName);
        }

        // update last name
        System.out.println("Update last name (press Enter to keep it unchanged)");
        System.out.println("Old last name: " + employee.getLastName());
        String newLastName = sc.nextLine().trim();
        if (!newLastName.isEmpty()) {
            employee.setLastName(newLastName);
        }

        // update gender
        System.out.println("Update gender (press Enter to keep it unchanged)");
        System.out.println("Old gender: " + employee.getFirstName());
        for (int i = 0; i < Gender.values().length; i++) {
            System.out.println("\t" + (i + 1) + ". " + Gender.values()[i]);
        }
        String _num = sc.nextLine().trim();
        if (!_num.isEmpty()) {
            employee.setGender(Gender.values()[Integer.parseInt(_num) - 1]);
        }

        // update dob
        System.out.println("Update birth date with format YYYY-MM-DD (press Enter to keep it unchanged)");
        System.out.println("Old birth date: " + employee.getBirthDate());
        String newDOB = sc.nextLine().trim();
        if (!newDOB.isEmpty()) {
            employee.setBirthDate(Date.valueOf(newDOB));
        }

        System.out.println("Update start-working date with format YYYY-MM-DD (press Enter to keep it unchanged)");
        System.out.println("Old one: " + employee.getHiredDate());
        String hiredDate = sc.nextLine().trim();
        if (!hiredDate.isEmpty()) {
            employee.setHiredDate(Date.valueOf(hiredDate));
        }

        sc.close();

        boolean result = repository.update(employee);

        if (!result) {
            System.err.println("Update failed!");
        }
        System.out.println("Update successfully!");
    }

    public void updateWorkingHistory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nChoose employee ID to update:");
        for (Employee e : repository.findAll()) {
            System.out.println(e);
        }
        int empID = Integer.parseInt(sc.nextLine().trim());
        List<WorkingHistory> workingHistories = workingHistoryDAO.findByEmpID(empID);

        System.out.println("Choose history to update: ");
        int count = 1;
        for (WorkingHistory w : workingHistories) {
            System.out.println(count + ". " + w);
            count++;
        }
        int ans1 = Integer.parseInt(sc.nextLine().trim());
        WorkingHistory target = workingHistories.get(ans1 - 1);

        String toDate = "";
        while (true) {
            System.out.print("Set to_date attribute (YYYY-MM-DD): ");
            toDate = sc.nextLine().trim();
            java.util.Date fromDate = new java.util.Date(target.getFromDate().getTime());
            java.util.Date _toDate = new java.util.Date(Date.valueOf(toDate).getTime());
            if (_toDate.compareTo(fromDate) > 0) {
                break;
            }
        }

        target.setToDate(Date.valueOf(toDate));
        workingHistoryDAO.update(target);
        sc.close();
    }

    public void filterByTimePeriod() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Start from (YYYY-MM-DD):");
        String start = sc.nextLine();
        System.out.print("End on (YYYY-MM-DD): ");
        String end = sc.nextLine();
        sc.close();

        List<Integer> employeeIDs = workingHistoryDAO.employeeIDFilteredByTimePeriod(start, end);
        List<Employee> response = repository.findByIdIn(employeeIDs);
        // List<Employee> response = repository.findByIdIn(Arrays.asList(new Integer[] {
        // 1, 4, 5, 6 }));
        for (Employee e : response) {
            System.out.println(e);
        }

    }

}
