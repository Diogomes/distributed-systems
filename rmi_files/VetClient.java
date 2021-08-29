import java.rmi.*;
public class VetClient
{ public static void main(String[] arg)
  { 
    VetInterface v;
    try
    {
      v = (VetInterface)Naming.lookup(
                       "rmi://localhost:1099/Vet10");
      
      v.reset();
      v.setInt(6,9);
      
      for (int i=0; i<10; i++)
	System.out.println("Valor = "+v.getInt(i));
      
    }
    catch(Exception e) { /*...*/ }
  }
}

