package UserModule;


/**
* UserModule/UserOperations.java .
* 由IDL-to-Java 编译器 (可移植), 版本 "3.2"生成
* 从C:/Users/yjfu/Desktop/network3/user.idl
* 2017年5月19日 星期五 上午10时28分08秒 CST
*/

public interface UserOperations 
{
  boolean add (String startTime, String endTime, String label);
  String query (String startTime, String endTime);
  boolean delete (String keyWord);
  boolean clear ();
  String show ();
} // interface UserOperations
