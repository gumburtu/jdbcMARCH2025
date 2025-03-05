public class Transaction02 {
}
import java.sql.*;

public class Transaction02 {

    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jdbc_db",
                "techpro",
                "password");
        Statement st =connection.createStatement();

        st.execute("CREATE TABLE IF NOT EXISTS hesaplar3 (hesap_no INT UNIQUE, isim VARCHAR(50), bakiye REAL)");

        String sql1 = "INSERT INTO hesaplar3 VALUES (?,?,?) ";
        PreparedStatement prst1 = connection.prepareStatement(sql1);
        prst1.setInt(1, 1234);
        prst1.setString(2,"Fred");
        prst1.setDouble(3,9000);
        prst1.executeUpdate();

        prst1.setInt(1, 5678);
        prst1.setString(2,"Barnie");
        prst1.setDouble(3,6000);
        prst1.executeUpdate();

        //rollback bu satıra geri döndürür

        //TASK: hesap no:1234 ten hesap no:5678 e 1000$ para transferi olsun.
        //ayrı iki hesabın update işlemlerini birbiri ile bağımlı old için
        //tek bir transaction olarak değerlendirelim

        try{
            //bu satıra kadar otomatik commitleniyor
            connection.setAutoCommit(false);
            //transaction yönetimi artık bizde, yani yeni bir transaction başlattık

            String sql="UPDATE hesaplar3 SET bakiye=bakiye+? WHERE hesap_no=?";
            PreparedStatement prst2=connection.prepareStatement(sql);
            //1.işlem-Fred'in bakiyesi 1000$ azalmalı
            prst2.setDouble(1,-1000);
            prst2.setInt(2,1234);
            prst2.executeUpdate();

//            Savepoint sp=connection.setSavepoint();
//            connection.rollback(sp);
//            //işlemler arasında kayıt nok. belirleyip
//            //belirli durumlarda rollback ile bu noktaya dönülebilir.

            //sistemde hata oluştu kabul edelim
            if (false){
                throw new RuntimeException();
            }

            //2.işlem-Barnie'in bakiyesi 1000$ artmalı
            prst2.setDouble(1,1000);
            prst2.setInt(2,5678);
            prst2.executeUpdate();

            prst2.close();

            //tüm işlemler başarılı ise
            System.out.println("İşlem başarılı...");
            connection.commit();

        }catch (Exception e){
            System.out.println("Sistemde hata oluştu...");
            e.printStackTrace();
            connection.rollback();
            //işlemlerden en az biri başarısız
            //o halde tüm işlemleri geri al
            //en son commit yapılan duruma geri döner

        }


        st.close();
        prst1.close();
        connection.close();


    }
}