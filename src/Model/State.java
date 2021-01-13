package Model;

import java.util.LinkedList;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class State {
    private LinkedList<Node> list;
    private long time;

    public State(ObservableList<Node> list,long time){
        this.time=time;
        this.list=new LinkedList<>();
        for(Node node:list)
            this.list.add(node);
    }

    public long getTime() {
        return time;
    }

    public LinkedList<Node> getList() {
        return list;
    }
    
}
