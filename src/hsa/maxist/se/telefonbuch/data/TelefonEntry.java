package hsa.maxist.se.telefonbuch.data;

public class TelefonEntry {

    public static final String empty = "<empty>";

    private String lastName;
    private String firstName;
    private String number;

    public TelefonEntry() {
        firstName = empty;
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

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + number;
    }
}
