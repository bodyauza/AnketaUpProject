import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseHandler extends Configs {
    Connection dbConnection;

    public DatabaseHandler() {
    }

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + this.dbHost + ":" + this.dbPort + "/" + this.dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(connectionString, this.dbUser, this.dbPass);
        return this.dbConnection;
    }

    public void signUpUser(String name, String number) {
        String insert = "INSERT INTO users(name,number)VALUES(?,?)";

        try {
            PreparedStatement preparedStatement = this.getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, number);
            preparedStatement.executeUpdate();
        } catch (SQLException var5) {
            throw new RuntimeException(var5);
        } catch (ClassNotFoundException var6) {
            throw new RuntimeException(var6);
        }
    }
}
