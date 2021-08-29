import java.rmi.*;
public class VetServer
{ public static void main(String[] arg)
  { try
    { Naming.rebind("Vet10", new Vet10Impl());
    }
    catch(Exception e)
    { e.printStackTrace();
    }
  }
}

