package insights;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.watson.developer_cloud.personality_insights.v3.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile;
import com.ibm.watson.developer_cloud.personality_insights.v3.model.ProfileOptions;

public class Insights_lib {
		private PersonalityInsights service;
		private List<Double> bigPersonality = new ArrayList<Double>();
		private List<List<Double>> bigList = new ArrayList<List<Double>>();

		public Insights_lib(){
		service = new PersonalityInsights("2016-10-19");
	    service.setUsernameAndPassword("93a97e74-5ea9-48e3-8a5a-18643083d11e", "SyZxAMIIkNYw");

		}

		public void getProfile(String text){

	    ProfileOptions options = new ProfileOptions.Builder()
		        .text(text)
		        .build();

	   // ProfileOptions options = new ProfileOptions.Builder()
	    		//.contentLanguage(Language.JA) //日本語入力
	    		//.acceptLanguage(Language.JA)  //日本語出力
	    		  //      .text(comment)
	    		    //    .build();

		    Profile profile = service.profile(options).execute();

		    System.out.println(profile);

		    this.getJson(profile.toString(),text );

		}

		private void getJson(String json, String text) {
			// TODO 自動生成されたメソッド・スタブ

			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = null;
			try {
				node = mapper.readTree(json.toString());
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			//大きいの

			for(JsonNode jnode : node.get("personality")){
				//作って入れるよ
				bigPersonality.add(jnode.get("percentile").asDouble());
				}

			//小さいの
			for(JsonNode jnode : node.get("personality")){

				List<Double> test = new ArrayList<Double>();
				for(JsonNode jn : jnode.get("children")){
					test.add(jn.get("percentile").asDouble());
				}

				bigList.add(test);
			}

			MySQL mysql = new MySQL();
			mysql.insertScreens(bigPersonality,bigList, text);
			this.print(bigPersonality,bigList, text);
		}

		public void print(List<Double>bigList,List<List<Double>> detail, String text){

			int i = 0;
			for (double d : bigList){
				System.out.println(d);
			}
		}

}
