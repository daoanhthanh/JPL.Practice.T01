package fa.training.problem02;

import fa.training.problem02.controllers.DepartmentController;
import fa.training.problem02.controllers.EmployeeController;

import java.util.Scanner;

/**
 * @author Edward Dao
 * @version 1.0
 */
public class App {
    public static void main(String[] args) throws Exception {
        EmployeeController employeeController = new EmployeeController();
        DepartmentController departmentController = new DepartmentController();
        Scanner sc = new Scanner(System.in);
        System.out.println("******** WELCOME TO CONSOLE EMPLOYEE MANAGEMENT APPLICATION ********\n");
        System.out.println("Please choose your option:");
        System.out.println("\t1. Employee management.");
        System.out.println("\t2. Department management.");
        System.out.println("\t3. Close the program.");
        String ans = sc.nextLine();
        switch (ans) {
        case "1":
            System.out.println("Employee management selected!");
            System.out.println("Choose function:");
            System.out.println("\t1. Add a new Employee");
            System.out.println("\t2. Update a specific Employee");
            System.out.println("\t3. Find an employee by emp_no");
            System.out.println("\t4. Add the working history");
            System.out.println("\t5. Find all the employees by working period of time");
            String ans1 = sc.nextLine();
            switch (ans1) {
            case "1":
                int departmentNumber = departmentController.getAll().size();
                if (departmentNumber == 0) {
                    System.err.println("There aren't any department. Please create one prior to continue!");
                    break;
                }
                System.out.println("\n ***** Add a new Employee *****");
                employeeController.create();
                break;

            case "2":
                employeeController.update();
                break;

            case "3":
                System.out.println("Enter employee's ID: ");
                String empID = sc.nextLine();
                employeeController.findById(Integer.parseInt(empID));

                break;

            case "4":
                System.out.println("Add the working history");
                employeeController.updateWorkingHistory();
                break;

            case "5":
                System.out.println("Find all the employees by working period of time");
                employeeController.filterByTimePeriod();
                break;
            }
            break;

        case "2":
            System.out.println("Department management selected!");
            System.out.println("Choose function:");
            System.out.println("\t1. Add a new department");
            String ans2 = sc.nextLine();
            if (ans2.equals("1")) {
                departmentController.create();
            }
            break;

        case "3":
            System.out.println("Bye!");
            break;
        }

        sc.close();
    }
}
