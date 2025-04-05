package util;

import java.util.*;
 
public class Roles {
	
    static class Permission {
        String name;
        
        public Permission(String name) {
            this.name = name;
        }
    }

    static class Role {
        String name;
        Set<Permission> permissions = new HashSet<>();

        public Role(String name) {
            this.name = name;
        }
        
        // type of any permission to be added 
        public void addPermission(Permission permission) {
            permissions.add(permission);
        }
    }

    static class User {
        String name;
        Role role;
        
        
        public User(String name, Role role) {
            this.name = name;
            this.role = role;
        }
        
        //permission given to a class(role) of users
        public void showPermissions() {
            System.out.println("Permissions granted to " + name + " (" + role.name + "):");
            for (Permission p : role.permissions) {
                System.out.println("- " + p.name);
            }
        }
    }

    public static void main(String[] args) {
        // type of rules and permissions
        Permission viewData = new Permission("View Data");
        Permission editData = new Permission("Edit Data");
        Permission deleteData = new Permission("Delete Data");
        Permission manageUsers = new Permission("Manage Users");

        // Defining roles
        Role seniorSoftwareEngineer = new Role("Senior Software Engineer");
        seniorSoftwareEngineer.addPermission(viewData);
        seniorSoftwareEngineer.addPermission(editData);
        
        Role employee = new Role("Employee");
        employee.addPermission(viewData);

        Role supervisor = new Role("Supervisor");
        supervisor.addPermission(viewData);
        supervisor.addPermission(manageUsers);

        // Define roles map
        Map<String, Role> rolesMap = new HashMap<>();
        rolesMap.put("Senior Software Engineer", seniorSoftwareEngineer);
        rolesMap.put("Employee", employee);
        rolesMap.put("Supervisor", supervisor);

        // User input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your role (Senior Software Engineer, Employee, Supervisor): ");
            String roleInput = scanner.nextLine();
            Role userRole = rolesMap.get(roleInput);

            if (userRole != null) {
                User user = new User(name, userRole);
                user.showPermissions();
            } else {
                System.out.println("Invalid role selected. Please enter a valid role.");
            }

            System.out.print("\nDo you want to add another user? (yes/no): ");
            String answer = scanner.nextLine();
            if (!answer.equalsIgnoreCase("yes")) {
                break;
            }
        }

        scanner.close();
    }
}
