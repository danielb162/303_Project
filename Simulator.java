// Used during step():
import java.util.HashMap;
// Used in buildWorld():
import java.util.Random;
import java.lang.Math;
// Used to handle user input:
import java.util.Scanner;
// Used for Swing/display:
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Simulator {
  // Private class to follow Singleton DP
  private static class World {
    private final GridItem[][] item_arr;
    private final int max_y;
    private final int max_x;
    
    private World( int in_x_dim, int in_y_dim ) {
      item_arr = new GridItem[in_y_dim][in_x_dim];
      max_y = item_arr.length;
      max_x = item_arr[0].length;
    }
    
    // Allows Autonomous objects to move again, called after step() in Simulator's main
    public void resetMode() {
      for ( int y = 0; y < max_y; y++ ){
        for ( int x = 0; x < max_x; x++ ){
          if ( item_arr[y][x] != null && item_arr[y][x].getToken() == 'A' && item_arr[y][x].getMode() == true ) {
            item_arr[y][x].setMode(false);
          }
        }
      }
    }
    
    // Iterates through cells changing state of the world
    public void step() {      
      for ( int y = 0; y < max_y; y++ ){
        for ( int x = 0; x < max_x; x++ ){
          if ( item_arr[y][x] != null && item_arr[y][x].getToken() == 'A' && item_arr[y][x].getMode() == false ) {
            // Want to step & see if chain reaction occurs
            char dir = item_arr[y][x].step();
            if ( dir != 'E' && dir != 'e' ) { // Protect against calls on improper objects
              switch ( dir ) {
                case 'N': // Want to move 'up', y - 1
                  if ( y == 0 ) break;
                  if ( item_arr[y - 1][x] == null ) {
                    item_arr[y - 1][x] = item_arr[y][x];
                    item_arr[y - 1][x].update(x, y - 1, max_x, max_y);
                    item_arr[y - 1][x].setMode(true); // to prevent further, autonomous movement
                    item_arr[y][x] = null;
                    break;
                  }
                  else if ( item_arr[y - 1][x].getToken() == 'A' || item_arr[y - 1][x].getToken() == 'M' ) {
                    int row_count = y - 1;
                    // HM related temp variables:
                    HashMap<Integer, GridItem> bumpingHM = new HashMap<Integer, GridItem>();
                    bumpingHM.put( 0, item_arr[y][x]);
                    
                    int count = 1;
                    while ( row_count >= 0 ) {
                      if ( item_arr[row_count][x] == null ) {
                        // Collapse & broadcast
                        while ( count > 0 ) {
                          // Count is one greater than the last key in the HM, so we -1:
                          GridItem temp = bumpingHM.remove(count - 1);
                          item_arr[row_count][x] = temp;
                          item_arr[row_count][x].update(x, row_count, max_x, max_y);
                          if ( count == 1 ) item_arr[row_count][x].setMode(true); // to prevent further, autonomous movement
                          item_arr[row_count + 1][x] = null;
                          
                          row_count++;
                          count--;
                        }
                        break;
                      }
                      else if ( item_arr[row_count][x].getToken() == 'I' ) {
                        // Want to stop as we've hit an immovable object
                        break;
                      }
                      
                      // Want to put current Object onto bumpingHm and continue looping
                      bumpingHM.put(count, item_arr[row_count][x]);
                      count++;
                      row_count--;
                    }
                    
                  }
                  // Nothing happens as our object can't move up into an Immovable object
                  break;
                  
                case 'E': // Want to move 'right', x + 1
                  if ( x == max_x - 1 ) break;
                  else if ( item_arr[y][x + 1] == null ) {
                    item_arr[y][x + 1] = item_arr[y][x];
                    item_arr[y][x + 1].update(x + 1, y, max_x, max_y);
                    item_arr[y][x + 1].setMode(true);
                    item_arr[y][x] = null;
                    break;
                  }
                  else if ( item_arr[y][x + 1].getToken() == 'A' || item_arr[y][x + 1].getToken() == 'M' ) {
                    int col_count = x + 1;
                    HashMap<Integer, GridItem> bumpingHM = new HashMap<Integer, GridItem>();
                    bumpingHM.put( 0, item_arr[y][x]);
                    
                    int count = 1;
                    while ( col_count < max_x ) {
                      if ( item_arr[y][col_count] == null ) {
                        while ( count > 0 ) {
                          GridItem temp = bumpingHM.remove(count - 1);
                          item_arr[y][col_count] = temp;
                          item_arr[y][col_count].update(col_count, y, max_x, max_y);
                          if ( count == 1 ) item_arr[y][col_count].setMode(true);
                          item_arr[y][col_count - 1] = null;
                          
                          col_count--;
                          count--;
                        }
                        break;
                      }
                      else if ( item_arr[y][col_count].getToken() == 'I' ) {
                        break;
                      }
                      
                      bumpingHM.put(count, item_arr[y][col_count]);
                      count++;
                      col_count++;
                    }
                    
                  }
                  break;
                  
                case 'S': // Want to move 'down', y + 1
                  if ( y == max_y - 1) break;
                  else if ( item_arr[y + 1][x] == null ) {
                    item_arr[y + 1][x] = item_arr[y][x];
                    item_arr[y + 1][x].update(x, y + 1, max_x, max_y);
                    item_arr[y + 1][x].setMode(true);
                    item_arr[y][x] = null;
                    break;
                  }
                  else if ( item_arr[y + 1][x].getToken() == 'A' || item_arr[y + 1][x].getToken() == 'M' ) {
                    int row_count = y + 1;
                    HashMap<Integer, GridItem> bumpingHM = new HashMap<Integer, GridItem>();
                    bumpingHM.put( 0, item_arr[y][x]);
                    
                    int count = 1;
                    while ( row_count < max_y ) {
                      if ( item_arr[row_count][x] == null ) {
                        while ( count > 0 ) {
                          GridItem temp = bumpingHM.remove(count - 1);
                          item_arr[row_count][x] = temp;
                          item_arr[row_count][x].update(x, row_count, max_x, max_y);
                          if ( count == 1 ) item_arr[row_count][x].setMode(true);
                          item_arr[row_count - 1][x] = null;
                          
                          row_count--;
                          count--;
                        }
                        break;
                      }
                      else if ( item_arr[row_count][x].getToken() == 'I' ) {
                        break;
                      }
                      
                      bumpingHM.put(count, item_arr[row_count][x]);
                      count++;
                      row_count++;
                    }
                    
                  }
                  break;
                  
                case 'W': // Want to move 'left', x - 1
                  if ( x == 0 ) break;
                  else if ( item_arr[y][x - 1] == null ) {
                    item_arr[y][x - 1] = item_arr[y][x];
                    item_arr[y][x - 1].update(x - 1, y, max_x, max_y);
                    item_arr[y][x - 1].setMode(true); // to prevent further, autonomous movement
                    item_arr[y][x] = null;
                    break;
                  }
                  else if ( item_arr[y][x - 1].getToken() == 'A' || item_arr[y][x - 1].getToken() == 'M' ) {
                    int col_count = x - 1;
                    HashMap<Integer, GridItem> bumpingHM = new HashMap<Integer, GridItem>();
                    bumpingHM.put( 0, item_arr[y][x]);
                    
                    int count = 1;
                    while ( col_count >= 0 ) {
                      if ( item_arr[y][col_count] == null ) {
                        while ( count > 0 ) {
                          GridItem temp = bumpingHM.remove(count - 1);
                          item_arr[y][col_count] = temp;
                          item_arr[y][col_count].update(col_count, y, max_x, max_y);
                          if ( count == 1 ) item_arr[y][col_count].setMode(true);
                          item_arr[y][col_count + 1] = null;
                          
                          col_count++;
                          count--;
                        }
                        break;
                      }
                      else if ( item_arr[y][col_count].getToken() == 'I' ) {
                        break;
                      }
                      
                      bumpingHM.put(count, item_arr[y][col_count]);
                      count++;
                      col_count--;
                    }
                  }
                  break;
              }
            }
          }
        } // End of inner loop
      } // End of outer loop
    }
    
    
    // Uses Swing to display
    public void display() {
      // TO DO
      
      
      
    }
    
    // ASCII/text-only display
    public void displayASCII() {
      System.out.println("          0   1   2   3   4");
      for ( int y = 0; y < max_y; y++ ){
        System.out.print("Row #" + y + ": ");
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
    
    // Method to create and add a GridItem, returns false if -for any reason- the GridItem can't be added
    public boolean add( String in_name, char in_symbol, int in_x, int in_y ) {
      if ( item_arr[in_y][in_x] != null ) return false;
      switch ( in_symbol ) {
        case 'I':
          try {
          item_arr[in_y][in_x] = new Immovable(in_name, 'I', in_x, in_y, item_arr[in_y].length, item_arr.length);
          return true;
        }
          catch ( WorldItemException e ) {
            return false;
          }
        case 'A':
          try {
          item_arr[in_y][in_x] = new Autonomous(in_name, 'A', in_x, in_y, item_arr[in_y].length, item_arr.length);
          return true;
        }
          catch ( WorldItemException e ) {
            return false;
          }
        case 'M':
          try {
          item_arr[in_y][in_x] = new Moveable(in_name, 'M', in_x, in_y, item_arr[in_y].length, item_arr.length);
          return true;
        }
          catch ( WorldItemException e ) {
            return false;
          }
      }
      return true;
    }
  }
  
  // Specifications used to populate item_arr, can be changed by TA's to make testing easier
  private final static int imove_num = 5;
  private final static int move_num = 3;
  private final static int auto_num = 2;
  private final static int x_size = 5;
  private final static int y_size = 5;
  private static World gameWorld;
  private static final int loop_num = 100;
  
  // Creating Simulator using Singleton DP
  private static Simulator simInstance = new Simulator();
  private Simulator() {}
  private static Simulator getInstance() {
    return simInstance;
  }
  
  // Builds a 'World' of a particular size and then populates with the provided specs
  private static void buildWorld() throws WorldItemException {
    // To make sure we don't get stuck in an infinite loop
    if ( imove_num + move_num + auto_num <= x_size * y_size ) {
      gameWorld = new World(x_size, y_size);
      Random rand_gen = new Random();
      
      for ( int i = 1; i <= imove_num; ) {
        if ( gameWorld.add("I" + i, 'I', ( Math.abs(rand_gen.nextInt()) % 5 ), ( Math.abs(rand_gen.nextInt()) % 5 )) ) i++;
      }
      for ( int i = 1; i <= move_num; ) {
        if ( gameWorld.add("M" + i, 'M', ( Math.abs(rand_gen.nextInt()) % 5 ), ( Math.abs(rand_gen.nextInt()) % 5 )) ) i++;
      }
      for ( int i = 1; i <= auto_num; ) {
        if ( gameWorld.add("A" + i, 'A', ( Math.abs(rand_gen.nextInt()) % 5 ), ( Math.abs(rand_gen.nextInt()) % 5 )) ) i++;
      }
    }
    else System.err.println("The world sizes you provided was too small to accomodate the number of items you requested");
    
    /* Hand-coded for debugging/testing:
       gameWorld.add("I1", 'I', 1, 0);
       gameWorld.add("I2", 'I', 3, 1);
       gameWorld.add("I3", 'I', 2, 3);
       gameWorld.add("I4", 'I', 1, 3);
       gameWorld.add("I5", 'I', 4, 4);
       
       gameWorld.add("M1", 'M', 2, 1);
       gameWorld.add("M2", 'M', 3, 2);
       gameWorld.add("M3", 'M', 2, 4);
       
       gameWorld.add("A1", 'A', 2, 0);
       gameWorld.add("A2", 'A', 3, 4);
     //*/
    
  }
  
  public static void main(String[] args) throws WorldItemException {
    // Some variables we'll during our loops
    Scanner kb_scanner = new Scanner(System.in);
    String resp;
    int cur_iterate = 0;
    
    // 0th loop:
    buildWorld();
    System.out.println("Opening:");
    gameWorld.displayASCII();
    System.out.println();
    
    // Nth loop:
    do {
      int temp = cur_iterate;
      int lim = temp + loop_num;
      for (; temp < lim; temp++, cur_iterate++ ){
        gameWorld.step();
        gameWorld.resetMode();
        System.out.println("Post-step " + (cur_iterate + 1) + ":");
        gameWorld.displayASCII();
        System.out.println();
      }
      System.out.println("Would you like to run the simulation again?");
      resp = kb_scanner.nextLine();
    } while ( !resp.equals("N") );
    
    
  }
  
}