/**
 * <h1>Interface to enforce validity-checking during WorldItem construction</h1>
 * This interface is used so that, at construction, only a char which correctly represents the WorldItem Object
 * it is trying to construct will be accepted. Otherwise a WorldItemException is thrown
 * 
 * @see WorldItem
 * @see WorldItemException
 * @see Autonomous
 * @see Moveable
 * @see Immovable
 * 
 * @author Daniel Busuttil, 260608427
 * @version 1.0
 * @since 2018-04-08
 */ 

public interface Bumpable {
  public boolean bump( char in_symbol );
  public boolean symbolValidCheck( char in_symbol );
  public int step();
  
  
}