package application;

import Model.QueueObserver;
import javafx.scene.text.Text;

public class QueueText extends Text implements QueueObserver {

    public QueueText(double x,double y,String text){
        super(x, y, text);
    }
    @Override
    public void update(String text) {
        setText(text);
    }
    
}
