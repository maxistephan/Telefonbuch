package hsa.maxist.se.telefonbuch.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;

import java.util.Iterator;

public class TelefonBook implements Iterable<TelefonEntry>{

    ObservableList<TelefonEntry> telefonNumbers;

    public TelefonBook() {
        telefonNumbers = FXCollections.observableArrayList();
    }

    public void delete(ObservableList<TelefonEntry> selected){
        for (TelefonEntry item : selected) {
            telefonNumbers.remove(item);
        }
    }

    public void add(Event event) {
        telefonNumbers.add(new TelefonEntry());
    }

    public void search(Event event) {

    }

    public ObservableList<TelefonEntry> getTelefonNumbers(){
        return telefonNumbers;
    }

    @Override
    public Iterator<TelefonEntry> iterator() {
        return telefonNumbers.iterator();
    }
}
