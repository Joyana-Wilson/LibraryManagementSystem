public class Patron {

    // Variables used to store the information for each patron
    private String id;
    private String name;
    private String address;
    private double overdueFine;

    // Constructor used to create a Patron object and assign the patron's information
    public Patron(String id, String name, String address, double overdueFine) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.overdueFine = overdueFine;
    }

    // Returns the patron's unique 7-digit ID number
    public String getId() {
        return id;
    }

    // Returns the patron's name
    public String getName() {
        return name;
    }

    // Returns the patron's address
    public String getAddress() {
        return address;
    }

    // Returns the patron's overdue fine amount
    public double getOverdueFine() {
        return overdueFine;
    }

    // The toString returns all of the patron's information so that it can be displayed
    @Override
    public String toString() {
        return id + " - " + name + " - " + address + " - $" + overdueFine;
    }
}
