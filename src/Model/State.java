package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class State {
    private ObservableList<Node> list;
    private long time;

    public State(ObservableList<Node> list,long time){
        this.time=time;
        this.list = FXCollections.observableArrayList(list);
    }

    public long getTime() {
        return time;
    }

    public ObservableList<Node> getList() {
        return list;
    }
    
}
