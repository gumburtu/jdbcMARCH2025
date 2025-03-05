import java.sql.*;

/*
PreparedStatement; önceden derlenmiş tekrar tekrar kullanılabilen
parametreli sorgular oluşturmamızı ve çalıştırmamızı sağlar.
SQL injectionı engeller

PreparedStatement Statement ı extend eder(statementın gelişmiş halidir.)

 */
public class PreparedStatement01 {
    public static void main(String[] args) throws SQLException {

        //n02-ADIM:bağlantıyı oluşturma: db url,kullanıcı adı,password
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jdbc_db",
                "techpro",
                "password");

        //n03-ADIM:Statement oluşturma:SQL ifadelerini veritabanına iletir ve çalıştırır
        Statement st =connection.createStatement();

        System.out.println("başarılı");

        int puan=475;

        //n04-ADIM
        //ÖRNEK1: bolumler tablosunda Matematik bölümünün taban_puanı'nı 475 olarak güncelleyiniz.
        String sql1="UPDATE bolumler SET taban_puani="+puan+" WHERE bolum ILIKE 'MATEMATIK' ";
        int updated=st.executeUpdate(sql1);
        System.out.println(updated+" tane satır güncellendi.");

        //TASK1 :PreparedStatement kullanarak bolumler tablosunda Matematik bölümünün
        // taban_puanı'nı 411 olarak güncelleyiniz.

        //parametreli sorguyu hazırlayalım
        String sql2="UPDATE bolumler SET taban_puani=? WHERE bolum ILIKE ?";
        PreparedStatement prst =connection.prepareStatement(sql2);
        //parametrede verilen String ifadeden önceden derlenmiş bir sorgu oluşturur


        prst.setInt(1,411);
        prst.setString(2,"Matematik");
        //parametrenin indexi sorgunun içindeki bulunuşluk sırasına göre seçilir
        // taban_puanı 1.index bölüm tarafı 2.index

        int updated2=prst.executeUpdate();//derlenmiş olan sorguyu çalıştırır.


        //TASK 2 :Prepared Statement kullanarak bolumler tablosunda
        // Edebiyat bölümünün taban_puanı'nı 450 olarak güncelleyiniz.
        prst.setString(2,"Edebiyat");
        prst.setInt(1,450);
        //update bolumler set taban_puani=450 where bolum ilike 'Edebiyat'
        prst.executeUpdate();

        //SIRA SİZDE:) :bolumler tablosunda İşletme bölümünün
        // taban_puanı'nı 444 olarak güncelleyiniz.
        prst.setString(2,"İşletme");
        prst.setInt(1,444);
        prst.executeUpdate();

        //TASK 3 : bolumler tablosuna
        //Yazılım Mühendisliği(id=5006,taban_puanı=475, kampus='Merkez') bölümünü ekleyiniz.
        String sql3="INSERT INTO bolumler VALUES(?,?,?,?)";
        PreparedStatement prst2=connection.prepareStatement(sql3);
        prst2.setInt(1,5006);
        prst2.setString(2,"Yazılım Müh.");
        prst2.setInt(3,475);
        prst2.setString(4,"Merkez");
        prst2.executeUpdate();

        //kaynakları kapatalım
        st.close();
        prst.close();
        prst2.close();
        connection.close();


    }
}