import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagementSystem {

    // ArrayList used to store all Patron objects
    private static ArrayList<Patron> patrons = new ArrayList<>();

    // Scanner used to collect information entered by the user
    private static Scanner input = new Scanner(System.in);

    // The start of the Library Management System
    public static void main(String[] args) {

        // Store the user's menu selection
        int selection = 0;

        // Continues to display the menu until the user selects Exit
        while (selection != 5) {

            // Displays the Library Management System on-screen menu
            System.out.println("\nLIBRARY MANAGEMENT SYSTEM");
            System.out.println("1. Add Patrons from Text File");
            System.out.println("2. Add Patron Manually");
            System.out.println("3. Remove Patron");
            System.out.println("4. Display All Patrons");
            System.out.println("5. Exit the Library Management System");
            System.out.print("Please enter your selection: ");

            // Checks to make sure the user entered a number
            if (input.hasNextInt()) {

                // Stores the user's menu selection
                selection = input.nextInt();
                input.nextLine();

                // Performs a task based on the user's menu selection
                switch (selection) {

                    // Imports patrons from a text file
                    case 1:
                        importPatrons();
                        break;

                    // Allows the user to manually add a patron
                    case 2:
                        addPatron();
                        break;

                    // Allows the user to remove a patron
                    case 3:
                        removePatron();
                        break;

                    // Displays all patrons currently stored in the system
                    case 4:
                        displayPatrons();
                        break;

                    // Exits the Library Management System
                    case 5:
                        System.out.println("You have exited the Library Management System.");
                        break;

                    // Displays a message if the user enters an invalid selection
                    default:
                        System.out.println(
                                "Invalid selection. Please select a number from 1 through 5.");
                }

            } else {

                // Displays a message if the user does not enter a number
                System.out.println("Invalid selection. Please enter a number.");
                input.nextLine();
            }
        }
    }

    // Allows the user to manually add a patron to the system
    public static void addPatron() {

        // Asks the user to enter the patron's ID number
        System.out.print("Please enter the patron's 7 digit ID number: ");
        String id = input.nextLine();

        // Validates that the ID contains exactly 7 numeric digits
        if (!id.matches("\\d{7}")) {
            System.out.println(
                    "Invalid ID. The ID must contain exactly 7 digits.");
            return;
        }

        // Checks to make sure the ID is not already assigned to another patron
        if (isDuplicateId(id)) {
            System.out.println(
                    "Invalid ID. This ID is already assigned to another patron.");
            return;
        }

        // Asks the user to enter the patron's name
        System.out.print("Please enter the patron's name: ");
        String name = input.nextLine();

        // Asks the user to enter the patron's address
        System.out.print("Please enter the patron's address: ");
        String address = input.nextLine();

        // Asks the user to enter the patron's overdue fine
        System.out.print("Please enter the patron's overdue fine amount (0 to 250): ");

        // Checks to make sure the overdue fine amount is entered as a number
        if (!input.hasNextDouble()) {
            System.out.println(
                    "Invalid overdue fine amount. Please enter a number between 0 and 250.");
            input.nextLine();
            return;
        }

        // Stores the overdue fine amount entered by the user
        double overdueFine = input.nextDouble();
        input.nextLine();

        // Validates that the overdue fine is between $0 and $250
        if (overdueFine < 0 || overdueFine > 250) {
            System.out.println(
                    "Invalid overdue fine amount. The amount must be between $0 and $250.");
            return;
        }

        // Creates a new Patron object using the information entered
        Patron patron = new Patron(id, name, address, overdueFine);

        // Adds the new Patron object to the ArrayList
        patrons.add(patron);

        // Confirms that the patron was successfully added
        System.out.println("Patron added successfully.");
    }

    // Allows patrons to be added to the system from a text file
    public static void importPatrons() {

        // Asks the user to enter the location of the text file
        System.out.print("Please enter the location of the text file: ");
        String fileName = input.nextLine();

        try {

            // Creates a File object using the file location entered
            File file = new File(fileName);

            // Scanner used to read the information contained in the text file
            Scanner fileScanner = new Scanner(file);

            // Reads each line of the text file until there are no lines remaining
            while (fileScanner.hasNextLine()) {

                // Stores one line of patron information
                String line = fileScanner.nextLine();

                // Separates the patron information using the dash
                String[] data = line.split("-");

                // Makes sure that all four pieces of patron information are included
                if (data.length != 4) {
                    System.out.println(
                            "Invalid patron information: " + line);
                    continue;
                }

                // Stores the ID, name, and address from the text file
                String id = data[0];
                String name = data[1];
                String address = data[2];

                try {

                    // Converts the overdue fine amount from text into a double
                    double overdueFine = Double.parseDouble(data[3]);

                    // Validates that the ID contains exactly 7 numeric digits
                    if (!id.matches("\\d{7}")) {
                        System.out.println(
                                "Invalid patron ID: " + id);
                        continue;
                    }

                    // Checks to make sure the ID is not already being used
                    if (isDuplicateId(id)) {
                        System.out.println(
                                "Duplicate patron ID: " + id);
                        continue;
                    }

                    // Validates that the overdue fine is between $0 and $250
                    if (overdueFine < 0 || overdueFine > 250) {
                        System.out.println(
                                "Invalid overdue fine amount for patron: " + id);
                        continue;
                    }

                    // Creates a new Patron object using the imported information
                    Patron patron =
                            new Patron(id, name, address, overdueFine);

                    // Adds the new Patron object to the ArrayList
                    patrons.add(patron);

                    // Confirms that the patron was successfully added
                    System.out.println(
                            "Patron " + id + " has been added successfully.");

                } catch (NumberFormatException e) {

                    // Displays a message if the overdue fine is not a valid number
                    System.out.println(
                            "Invalid overdue fine amount: " + line);
                }
            }

            // Closes the Scanner after the entire text file has been read
            fileScanner.close();

            // Confirms that the text file has been read
            System.out.println("\nText file has been successfully loaded.");

            // Displays all patrons currently stored after the file is loaded
            displayPatrons();

        } catch (FileNotFoundException e) {

            // Displays a message if the text file cannot be found
            System.out.println(
                    "File not found. Please check the file location.");
        }
    }

    // Allows the user to remove a patron using the patron's ID number
    public static void removePatron() {

        // Asks the user to enter the ID number of the patron to remove
        System.out.print(
                "Please enter the ID number of the patron to be removed: ");
        String id = input.nextLine();

        // Searches through the ArrayList for a patron with the matching ID
        for (int i = 0; i < patrons.size(); i++) {

            // Compares the entered ID to the ID of each stored patron
            if (patrons.get(i).getId().equals(id)) {

                // Removes the Patron object from the ArrayList
                patrons.remove(i);

                // Confirms that the patron was successfully removed
                System.out.println("Patron has been removed successfully.");
                return;
            }
        }

        // Displays a message if the patron ID was not found
        System.out.println("Patron not found.");
    }

    // Displays all patrons currently stored in the system
    public static void displayPatrons() {

        // Checks to see if there are any patrons stored in the ArrayList
        if (patrons.isEmpty()) {

            // Displays a message if there are no patrons currently stored
            System.out.println(
                    "There are currently no patrons stored in the system.");
            return;
        }

        // Displays a heading before displaying the patron information
        System.out.println("\nPatrons:");

        // Goes through each Patron object stored in the ArrayList
        for (Patron patron : patrons) {

            // Displays all information for each patron
            System.out.println(patron);
        }
    }

    // Checks to determine if a patron ID is already being used
    public static boolean isDuplicateId(String id) {

        // Goes through each Patron object stored in the ArrayList
        for (Patron patron : patrons) {

            // Compares the entered ID to each existing patron ID
            if (patron.getId().equals(id)) {

                // Returns true if a matching ID is found
                return true;
            }
        }

        // Returns false if the ID is not already being used
        return false;
    }
}