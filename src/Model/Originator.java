package Model;

public class Originator {
    private State state;
    
    public void setState(State state){
        this.state=state;
    }

    public State getState() {
        return state;
    }
    
    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }
}
