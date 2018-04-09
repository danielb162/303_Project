/**
 * <h1>Sub-class to populate World</h1>
 * Child of WorldItem
 * 
 * @see WorldItem
 * 
 * @author Daniel Busuttil, 260608427
 * @version 1.0
 * @since 2018-04-08
 */ 

import java.util.Random;

public class Autonomous extends WorldItem implements Bumpable {
  
  private final Random ran_dir_gen; 
  
  public Autonomous(String in_name, char symbol, int in_x, int in_y, int in_grid_x, int in_grid_y) throws WorldItemException {
    super(in_name, symbol, in_x, in_y, in_grid_x, in_grid_y);
    this.ran_dir_gen = new Random();
  }
  
  public boolean symbolValidCheck( char in_symbol ) {
    if ( in_symbol == 'A') return true;
    return false;
  }
  
  // Placeholder until complete-recursive bump is complete
  public boolean bump( char in_char ) {
    return true;
  }
  
  public boolean step() {
    int temp = ran_dir_gen.nextInt() % 4;
    switch ( temp ) {
      case 0:
        return bump('N');
        
      case 1:
        return bump('E');
        
      case 2:
        return bump('S');
        
      case 3:
        return bump('W');
        
      // Should never be executed  
      default:
        return false; 
    }
  }

  
  
  
  
}




