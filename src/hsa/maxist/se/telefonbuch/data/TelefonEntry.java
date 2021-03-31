package hsa.maxist.se.telefonbuch.data;

public class TelefonEntry {

    public static int numOfEntries;
    private String lastName;
    private String firstName;
    private String number;

    public TelefonEntry() { numOfEntries++;}

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
}
