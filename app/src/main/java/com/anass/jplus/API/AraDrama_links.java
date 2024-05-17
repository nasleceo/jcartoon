package com.anass.jplus.API;

public class AraDrama_links {
	
	
	static public String Baseurl = "https://aradrmatv.com/";
	
	static public String NextPageSearch = Baseurl+"page/2/?s="+"searchqeary";
	
	static public String Searchurl = Baseurl+"?s="+"searchqeary";
	
	// New Episode Section
	static public String NewEpisodes = Baseurl+"category/episodes/new/";
	static public String NewEpisodesNextPage(String url) {return Baseurl+url;}
	static public String NewEpisodesOfOtherAsia = Baseurl+"category/episodes/new/other/";
	
	
	
	//Home Page
	static public String bramijkoria = Baseurl+"category/k-shows/page/";

	static public String UpcomingDrama = Baseurl+"category/serie/";
	static public String AiringDramaNow = Baseurl+"stat/airing/";
	static public String CompletDramarecent = Baseurl+"category/recent-completed/"; 
	static public String UpcomingDramarecent = Baseurl+"category/drama-soon/";
	static public String CompletDramarecentPage = Baseurl+"category/recent-completed/page/";
	static public String Dramarwabitmotajadida = Baseurl+"category/%D8%AF%D8%B1%D8%A7%D9%85%D8%A7-%D8%AA%D9%85-%D8%A7%D8%B9%D8%A7%D8%AF%D8%A9-%D8%B1%D9%81%D8%B9%D9%87%D8%A7/page/";
	static public String KoreanDrama = Baseurl+"category/serie/korea/page/";
	static public String japaneseDrama = Baseurl+"category/serie/japanese/page/";
	static public String chinesetaiwanDrama = Baseurl+"category/serie/chinese-taiwan/page/";
	static public String tailandDrama = Baseurl+"category/serie/tailand/page/";
	
	// Movies Section
	static public String Movies = Baseurl+"category/الافلام-الآسيوية/";
	static public String MoviesNextPage = Baseurl+"category/%D8%A7%D9%84%D8%A7%D9%81%D9%84%D8%A7%D9%85-%D8%A7%D9%84%D8%A2%D8%B3%D9%8A%D9%88%D9%8A%D8%A9/page/";

	
	// Drama Section
	static public String ListOfDrama = Baseurl+"category/serie/";
	static public String ListOfDramaPage = Baseurl+"category/serie/page/";




	static public String GetSearchFilter(String gener,String type,String status,String year,int page) {
		if (gener == null){
			return Baseurl+"tag/"+"/page/"+page+"/&stat="+status+"&yr="+year;

		}else if (status == null){
			return Baseurl+"tag/"+gener+"/page/"+page+"/&stat="+"&yr="+year;

		}else if (year == null){
			return Baseurl+"tag/"+gener+"/page/"+page+"/&stat="+status+"&yr=";

		}else {
			return Baseurl+"tag/"+gener+"/page/"+page+"/&stat="+status+"&yr="+year;
		}

	}
	
	static public String GetSearchByGeners(String gener,int page) {
		
		return Baseurl+"tag/"+gener+"/page/"+page;

	}
	static public String SearchLink(String search,int page) {

		return Baseurl+"page/"+page+"/?s="+search;

	}
	static public String SearchMovie = Baseurl+"tag/رومانسي/?type=k-drama&stat=فيلم";
	
	
	

}
