package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputManager extends KeyAdapter{

  public void keyPressed(KeyEvent e){
    //System.out.println(e.getKeyChar());
    switch(e.getKeyCode()){
      case 0x44:	// -> D
      case 39:	// -> Right 
        Game.inputDirection=1;
        break;

      case 0x57:	// -> W
      case 38:	// -> Top
        Game.inputDirection=3;
        break;

      case 0x41:	// -> A
      case 37: 	// -> Left 
        Game.inputDirection=2;
        break;

      case 0x53:	// -> S
      case 40:	// -> Bottom
        Game.inputDirection=4;
        break;

      case 0x1B:	// -> Esc
        Game.pausePressed = true;

      default: 	break;
    }
  }

}
