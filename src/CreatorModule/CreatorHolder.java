package CreatorModule;

/**
* CreatorModule/CreatorHolder.java .
* ��IDL-to-Java ������ (����ֲ), �汾 "3.2"����
* ��C:/Users/yjfu/Desktop/network3/creator.idl
* 2017��5��19�� ������ ����10ʱ27��54�� CST
*/

public final class CreatorHolder implements org.omg.CORBA.portable.Streamable
{
  public CreatorModule.Creator value = null;

  public CreatorHolder ()
  {
  }

  public CreatorHolder (CreatorModule.Creator initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = CreatorModule.CreatorHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    CreatorModule.CreatorHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return CreatorModule.CreatorHelper.type ();
  }

}
