package rework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

 public class InputManager extends KeyAdapter{
 	
 		public void keyPressed(KeyEvent e){
			//System.out.println(e.getKeyChar());
 		    switch(e.getKeyCode()){
				case 0x44:	// -> D
		    	case 39:	// -> Right 
		    				//if it's not the opposite direction
		    				//if(ThreadsController.directionInput!=2) 
		    					Game.inputDirection=1;
		    			  	break;

				case 0x57:	// -> W
		    	case 38:	// -> Top
							//if(ThreadsController.directionInput!=4) 
								Game.inputDirection=3;
		    				break;
		    				
				case 0x41:	// -> A
		    	case 37: 	// -> Left 
							//if(ThreadsController.directionInput!=1)
								Game.inputDirection=2;
		    				break;
		    	
				case 0x53:	// -> S
		    	case 40:	// -> Bottom
							//if(ThreadsController.directionInput!=3)
								Game.inputDirection=4;
		    				break;
		    	
		    	default: 	break;
 		    }
 		}
 	
 }
