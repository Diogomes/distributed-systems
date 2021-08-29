

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class ShowBindings
{  public static void main(String[] args)
   {  try
      {  Scanner sc = new Scanner (System.in);
         System.out.print("Digite o nome ou o IP da maquina que vc deseja ver os servicos rmi:  " );
         String[] bindings = Naming.list("rmi://" + sc.nextLine() + ":1099/");
         for (int i = 0; i < bindings.length; i++)
            System.out.println(bindings[i]);
      }
      catch(Exception e)
      {  System.out.println("Error: " + e);
      }
   }
}
