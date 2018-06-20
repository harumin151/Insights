package insights;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQL {
	String driver;// JDBCドライバの登録
    String server, dbname, url, user, password;// データベースの指定
    Connection con;
    Statement stmt;
    Map<String, Object> lng = new HashMap<>();
    private String id;

	public MySQL() {
		this.driver = "org.gjt.mm.mysql.Driver";
        this.server = "sangi2018.sist.ac.jp";
        this.dbname = "sangi2018";
        this.url = "jdbc:mysql://" + server + "/" + dbname + "?useUnicode=true&characterEncoding=UTF-8";
        this.user = "sangi2018";
        this.password = "J16015";
        this.id="J16015";
        try {
            this.con = DriverManager.getConnection(url, user, password);
            this.stmt = con.createStatement ();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Class.forName (driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	public void insertScreens(List<Double>bigList,List<List<Double>> detail,String text){
		//sqlインジェクション対策

		text=text.replace("'", "''");
		StringBuffer buf = new StringBuffer();
		buf.append("INSERT INTO  `screens` (`user_id`,`openness` ,`conscientiousness`,extraversion,agreeableness,neuroticism,adventurousness,artistic,emotionality,imagination,intellect,challenging,striving,cautiousness,dutifulness,orderliness,discipline,efficacy,activity,assertiveness,cheerfulness,seeking,outgoing,gregariousness,altruism,cooperation,modesty,uncompromising,sympathy,trust,fiery,worry,melancholy,immoderation,consciousness,susceptible,text) VALUES ('"+id+"'");
		for(double d : bigList){
			buf.append(","+d);
		}
		for(List<Double> list : detail){
			for(double d : list){
				buf.append(","+d);
			}
		}
		buf.append(",'"+text+"' );");
		String sql = buf.toString();
		System.out.println(sql);
		try {
			stmt.execute (sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}