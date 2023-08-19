package package1.package12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GoodsDAO {
    public static void addGoods(Goods goods) {
        String query = "INSERT INTO goods (id, name, price, stock) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, goods.getId());
            statement.setString(2, goods.getName());
            statement.setDouble(3, goods.getPrice());
            statement.setInt(4, goods.getStock());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add other methods for updating, deleting, querying goods data from the database
}
