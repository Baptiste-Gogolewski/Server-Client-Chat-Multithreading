package source;

import java.sql.*;

public class SQL
{
    // Login et Password voir dans "Users And Priviles" dans MySQL Workbench
    // Si vous voulez vous connectez à la base de données en distanciel laissez comme tel
    // si vous etes en localhost alors modifier en conséquence le Login et Password
    private String Login = "root";
    private String Password = "123456";

    // Adresse de la machine ou est lancé le serveur MySQL si pour une connexion à distance sinon mettre "localhost"
    private String IpAddress = "localhost";

    // L'adresse IP est celle de l'ordi ou est lancé le serveur MySQL sinon mettre localhost si le serveur tourne sur la même machine
    private String Url = "jdbc:mysql://" + IpAddress + ":3306/java";

    private String PasswordDatabase = null;
    private String LoginDatabase = null;

    public SQL()
    {

    }

    public int Show(String LoginUser, String PasswordUser)
    {
        Connection Connection = null;

        try
        {
            Connection = DriverManager.getConnection(Url, Login, Password);
            String SQLQuery = "select Login from users where Login = ?";
            PreparedStatement Statement = Connection.prepareStatement(SQLQuery);
            Statement.setObject(1, LoginUser);
            ResultSet ResultSet = Statement.executeQuery();

            while (ResultSet.next())
            {
                LoginDatabase = String.valueOf(ResultSet.getString("Login"));
            }

            if (LoginDatabase == null)
            {
                return 0;
            }
            else
            {
                SQLQuery = "select Password, Login from users where Login = ?";
                Statement = Connection.prepareStatement(SQLQuery);
                Statement.setObject(1, LoginUser);
                ResultSet = Statement.executeQuery();

                while (ResultSet.next())
                {
                    LoginDatabase = String.valueOf(ResultSet.getString("Login"));
                    PasswordDatabase = String.valueOf(ResultSet.getString("Password"));
                }

                if (LoginDatabase.equals(LoginUser) && PasswordDatabase.equals(PasswordUser))
                    return 1;
                else
                    return 2;
            }
        }
        catch (SQLException e)
        {
            return 3;
        }
    }

    public void Insert(String LoginUser, String PasswordUser)
    {
        Connection connection = null;

        try
        {
            connection = DriverManager.getConnection(Url, Login, Password);

            String sql = "insert into users(Login, Password) values(?, ?)";

            if (connection != null)
                System.out.println("Connection réussi");

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, LoginUser, Types.VARCHAR);
            statement.setObject(2, PasswordUser, Types.VARCHAR);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
