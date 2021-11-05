package fa.training.problem02.controllers;

import fa.training.problem02.dao.DepartmentDAO;
import fa.training.problem02.domains.entities.Department;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Edward Dao
 * @version 1.0
 */
public class DepartmentController {
    private final DepartmentDAO repo = new DepartmentDAO();

    public List<Department> getAll() {
        return repo.findAll();
    }

    public void create() throws SQLException {
        Department d = new Department();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new department name: ");
        d.setDeptName(sc.nextLine().trim());
        System.out.print("Enter description (optional): ");
        d.setDescription(sc.nextLine().trim());
        sc.close();
        repo.persist(d);
    }
}
