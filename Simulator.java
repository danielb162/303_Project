// Necessary libraries:
import java.util.ArrayList;
import java.util.Arrays;

public class Simulator {
  // Private class to follow Singleton DP
  private static class World {
    private GridItem[][] item_arr;
    //private ArrayList<Autonomous> auto_list;
    
    private World( int in_x_dim, int in_y_dim ) {
      item_arr = new GridItem[in_y_dim][in_x_dim];
    }
    
    
    
    // Iterates through cells changing state of the world
    public void step() {
      
      
      
      
      
      
      
      
      
      
    }
    
    
    
    
    // Uses Swing to display
    public void display() {
    }
    
    
    
    
    
    // ASCII/text-only display
    public void displayASCII() {
      int max_y = item_arr.length;
      int max_x = item_arr[0].length;
      
      for ( int y = 0; y < max_y; y++ ){
        for ( int x = 0; x < max_x; x++ ){
          if ( x == max_x - 1 ) {
            if ( item_arr[y][x] == null ) System.out.println("|   |");
            else System.out.println("| " + item_arr[y][x].getToken() + " |");
          }
          else {
            if ( item_arr[y][x] == null ) System.out.print("|   ");
            else System.out.print("| " + item_arr[y][x].getToken() + " ");
          }
          
        }
      }
    }
    
    public boolean add( String in_name, char in_symbol, int in_x, int in_y ) throws WorldItemException {
      if ( item_arr[in_y][in_x] != null ) return false;
      switch ( in_symbol ) {
        case 'I':
          try {
          item_arr[in_y][in_x] = new Immovable(/*in_world,*/ in_name, 'I', in_x, in_y, item_arr[in_y].length, item_arr.length);
          return true;
        }
          catch ( WorldItemException e ) {
            return false;
          }
        case 'A':
          try {
          item_arr[in_y][in_x] = new Autonomous(/*in_world,*/ in_name, 'A', in_x, in_y, item_arr[in_y].length, item_arr.length);
          //auto_list.add(item_arr[in_y][in_x]);
          return true;
        }
          catch ( WorldItemException e ) {
            return false;
          }
        case 'M':
          try {
          item_arr[in_y][in_x] = new Moveable(/*in_world,*/ in_name, 'M', in_x, in_y, item_arr[in_y].length, item_arr.length);
          return true;
        }
          catch ( WorldItemException e ) {
            return false;
          }
      }
      return true;
    }
  }
  
  
  
  
  
  private static World gameWorld; //new World(5, 5);
  // Used to populate item_arr, can be changed by TA's
  private final int imove_num = 5;
  private final int move_num = 3;
  private final int auto_num = 2;
  //private /*final*/ ArrayList<Autonomous> auto_list = null;
  
  // Creating Simulator using Singleton DP
  private static Simulator simInstance = new Simulator();
  private Simulator() {
    // ????
  }
  private static Simulator getInstance() {
    return simInstance;
  }
  
  // TO DO: Randomize object placement?
  // Builds a world of a particular size and then populates with the above number of objects
  private static void buildWorld() throws WorldItemException {
    gameWorld = new World(5,5);
    
    // Hand-coded coordinates for my objects:
    gameWorld.add("I1", 'I', 1, 0);
    gameWorld.add("I2", 'I', 3, 1);
    gameWorld.add("I3", 'I', 2, 3);
    gameWorld.add("I4", 'I', 1, 3);
    gameWorld.add("I5", 'I', 4, 4);
    
    gameWorld.add("M1", 'M', 2, 1);
    gameWorld.add("M2", 'M', 1, 2);
    gameWorld.add("M3", 'M', 2, 4);
    
    gameWorld.add("A1", 'A', 2, 2);
    gameWorld.add("A2", 'A', 3, 4);
    
  }
  
  public static void main(String[] args) throws WorldItemException {
    buildWorld();
    gameWorld.displayASCII();
  }
  
}