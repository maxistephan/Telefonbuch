package hsa.maxist.se.telefonbuch.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;

public class TelefonBook implements Iterable<TelefonEntry>{

    ObservableList<TelefonEntry> telefonNumbers;

    public TelefonBook() {
        telefonNumbers = FXCollections.observableArrayList();
    }

    public ObservableList<TelefonEntry> getTelefonNumbers(){
        return telefonNumbers;
    }

    @Override
    public Iterator<TelefonEntry> iterator() {
        return telefonNumbers.iterator();
    }
}
