package UserModule;


/**
* UserModule/UserOperations.java .
* ��IDL-to-Java ������ (����ֲ), �汾 "3.2"����
* ��C:/Users/yjfu/Desktop/network3/user.idl
* 2017��5��19�� ������ ����10ʱ28��08�� CST
*/

public interface UserOperations 
{
  boolean add (String startTime, String endTime, String label);
  String query (String startTime, String endTime);
  boolean delete (String keyWord);
  boolean clear ();
  String show ();
} // interface UserOperations
