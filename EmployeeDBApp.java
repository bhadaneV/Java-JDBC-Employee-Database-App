import java.sql.*;
import java.util.Scanner;

public class EmployeeDBApp {
    static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
    static final String USER = "root";
    static final String PASS = "your_password_here"; // Replace with your MySQL password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n1. Add Employee\n2. View All Employees\n3. Update Employee\n4. Delete Employee\n5. Exit");
                System.out.print("Choose option: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        addEmployee(conn, sc);
                        break;
                    case 2:
                        viewEmployees(conn);
                        break;
                    case 3:
                        updateEmployee(conn, sc);
                        break;
                    case 4:
                        deleteEmployee(conn, sc);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addEmployee(Connection conn, Scanner sc) throws SQLException {
        String query = "INSERT INTO employees (name, position) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query);
        System.out.print("Enter name: ");
        sc.nextLine();
        pstmt.setString(1, sc.nextLine());
        System.out.print("Enter position: ");
        pstmt.setString(2, sc.nextLine());
        pstmt.executeUpdate();
        System.out.println("Employee added.");
    }

    static void viewEmployees(Connection conn) throws SQLException {
        String query = "SELECT * FROM employees";
        ResultSet rs = conn.createStatement().executeQuery(query);
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getString("position"));
        }
    }

    static void updateEmployee(Connection conn, Scanner sc) throws SQLException {
        String query = "UPDATE employees SET name=?, position=? WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        System.out.print("Enter employee ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter new name: ");
        pstmt.setString(1, sc.nextLine());
        System.out.print("Enter new position: ");
        pstmt.setString(2, sc.nextLine());
        pstmt.setInt(3, id);
        pstmt.executeUpdate();
        System.out.println("Employee updated.");
    }

    static void deleteEmployee(Connection conn, Scanner sc) throws SQLException {
        String query = "DELETE FROM employees WHERE id=?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        System.out.print("Enter employee ID to delete: ");
        pstmt.setInt(1, sc.nextInt());
        pstmt.executeUpdate();
        System.out.println("Employee deleted.");
    }
}
