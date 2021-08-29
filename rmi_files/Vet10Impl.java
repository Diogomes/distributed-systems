import java.rmi.*; 
import java.rmi.server.*;
public class Vet10Impl extends UnicastRemoteObject implements VetInterface
{ int[] v;

  public Vet10Impl() throws RemoteException
  { v = new int[10]; reset(); }

  public void reset() throws RemoteException
  { for(int c = 0; c < v.length; c++)
      v[c] = 0;
  }

  public void setInt(int pos, int val) throws RemoteException
  { if(pos >= 0 && pos < v.length)
      v[pos] = val;
    else
      throw new RemoteException("Indice invalido");
  }

  public int getInt(int pos) throws RemoteException
  { if(pos >= 0 && pos < v.length)
      return v[pos];
    else
     throw new RemoteException("Indice invalido");
  }
}

