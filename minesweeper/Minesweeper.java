
package minesweeper;


public class Minesweeper implements Runnable {
    
    Screen screen = new Screen();
    
    public static void main(String[] args) {
        new Thread(new Minesweeper()).start();
    }   
        
    @Override
    public void run() {
        while(true){
            screen.repaint();
        }
    }
    
}
