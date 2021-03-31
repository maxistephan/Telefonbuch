package hsa.maxist.se.telefonbuch.data;

public class TelefonEntry {

    public static final String empty = "<empty>";

    private int id;
    private String lastName;
    private String firstName;
    private String number;
    public static int numOfInstances;

    public TelefonEntry() {
        firstName = empty;
        id = numOfInstances++;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getNumber() {
        return number;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + number;
    }
}
