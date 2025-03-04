import java.sql.*;

public class ExecuteQuery01 {
    public static void main(String[] args) throws SQLException {
        //n02-ADIM:bağlantıyı oluşturma: db url,kullanıcı adı,password

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jdbc_db",
                "techpro",
                "password");

        //n03-ADIM:Statement oluşturma:SQL ifadelerini veritabanına iletir ve çalıştırır
        Statement st = connection.createStatement();

        System.out.println("Başarılı");

        //n04-ADIM

        //ÖRNEK 1:id'si 5 ile 10 arasında olan ülkelerin "country_name" bilgisini listeleyiniz.
        String sql1 = "SELECT country_name FROM countries WHERE id BETWEEN 5 AND 10";

        boolean query1 = st.execute(sql1);
        System.out.println("query1 = " + query1);//true
                /*
        verileri alabilmek için:
         JDBC kullanarak veri çekme işlemi sonrasında
        veri listelemek için ResultSet sınıfı kullanılır.

        SQL sorgusu çalıştırıldıktan sonra veritabanından alınan
        verileri saklar. Verilerin arasında gitmemizi sağlar.
        Adv NOT: Veriler üzerinde dolaşmak için next, first, last, previous,
        absolute gibi metotlara sahiptir. Bunun için ayarlama gereklidir.
         */
        ResultSet rs1 = st.executeQuery(sql1);
        while (rs1.next()) {
            System.out.println("Ulke adi : " + rs1.getString("country_name"));
            System.out.println("Ulke adu : " + rs1.getString(1));
        }
        //n05
        st.close();
        connection.close();
    }
}
