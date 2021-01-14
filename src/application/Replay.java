package application;

import Model.*;

public class Replay implements Runnable {
    SimulatorController controller;

    public Replay(SimulatorController controller) {
        this.controller=controller;
    }

    @Override
    public void run() {
        for (int i = 0; i < controller.careTaker.getSize() - 1; i++) {
            State firstState = controller.careTaker.get(i).getState();
            State secondState = controller.careTaker.get(i + 1).getState();
            controller.updateCanvas(firstState.getList());
            try {
                Thread.sleep(secondState.getTime() - firstState.getTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        State lastState=controller.careTaker.get(controller.careTaker.getSize()-1).getState();
        controller.updateCanvas(lastState.getList());
    }
    
}
