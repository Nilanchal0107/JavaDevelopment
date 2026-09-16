import java.sql.DriverManager;
import java.sql.SQLException;

public class LaunchClassForNameEx {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
	{
		Class.forName("Demo");

		DriverManager.registerDriver(new com.mysql.jdbc.Driver());

	}

}

class Demo
{
	static
	{
		System.out.println("Static Block");
	}

	{
		System.out.println("Instance block ==> Non static");
	}
}
