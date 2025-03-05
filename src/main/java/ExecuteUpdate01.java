import java.sql.*;

//executeUpdate():DML için kullanılır; INSERT INTO,UPDATE,DELETE
//return:(int) sorgunun sonucundan etkilenen kayıt sayısını verir
public class ExecuteUpdate01 {
    public static void main(String[] args) throws SQLException {


        //n02-ADIM:bağlantıyı oluşturma: db url,kullanıcı adı,password

        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jdbc_db",
                "techpro",
                "password");

        //n03-ADIM:Statement oluşturma:SQL ifadelerini veritabanına iletir ve çalıştırır
        Statement st =connection.createStatement();

        System.out.println("başarılı");

        //n04-ADIM
        //ÖRNEK1: bolumler tablosunda Matematik bölümünün taban_puanı'nı 475 olarak güncelleyiniz.
        String sql1="UPDATE bolumler SET taban_puani=475 WHERE bolum ILIKE 'MATEMATIK' ";
        int updated=st.executeUpdate(sql1);
        System.out.println(updated+" tane satır güncellendi.");

        //ÖRNEK2:developers tablosuna yeni bir developer ekleyiniz.
        String sql2="INSERT INTO developers(name,salary,prog_lang) " +
                "VALUES('Jack Sparrow',5000,'Java')";
        int inserted=st.executeUpdate(sql2);
        System.out.println(inserted+" tane satır eklendi.");

        //tüm kayıtları görelim
        ResultSet rs =st.executeQuery("SELECT * FROM developers");
        while (rs.next()){
            Integer id=rs.getInt("id");
            String isim=rs.getString("name");
            Double maas=rs.getDouble("salary");
            String lang=rs.getString("prog_lang");
            System.out.println("id : "+id+" isim : "+isim+" maaş : "+maas+" prog. dili : "+lang );

        }

        //ÖRNEK3:developers tablosundan id'si 14 den büyük olanları siliniz.
        int deleted=st.executeUpdate("DELETE FROM developers WHERE id>14");
        System.out.println(deleted+" tane satır silindi.");

        //tüm kayıtları görelim
        ResultSet rs2 =st.executeQuery("SELECT * FROM developers");
        while (rs2.next()){
            Integer id=rs2.getInt("id");
            String isim=rs2.getString("name");
            Double maas=rs2.getDouble("salary");
            String lang=rs2.getString("prog_lang");
            System.out.println("id : "+id+" isim : "+isim+" maaş : "+maas+" prog. dili : "+lang );

        }

        //SIRA SİZDE:)developers tablosundan id'si 9 olanı siliniz.
        int deleted2 = st.executeUpdate("DELETE FROM developers WHERE id=9");
        System.out.println(deleted2 + " tane satır silindi.");

        //tüm kayıtları görelim
        ResultSet rs3 = st.executeQuery("SELECT * FROM developers");
        while (rs3.next()){
            Integer id = rs3.getInt("id");
            String isim = rs3.getString("name");
            Double maas = rs3.getDouble("salary");
            String lang = rs3.getString("prog_lang");

            System.out.println("id : "+ id +" İsim : " + isim + " Maaş : " + maas + " Programlama Dili : " + lang);
        }

        // ÖDEV: developers tablosunda maaşı ortalama maaştan az olanların maaşını
        //ortalama maaş ile güncelleyiniz.






    }

}