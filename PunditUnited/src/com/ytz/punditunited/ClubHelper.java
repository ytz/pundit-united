package com.ytz.punditunited;

public class ClubHelper {

	public static String getShortName(String team){
		if (team.equals("Newcastle"))
			return "NEW";
		else if (team.equals("Liverpool"))
			return "LIV";
		else if (team.equals("Stoke"))
			return "STK";
		else if (team.equals("Arsenal"))
			return "ARS";
		else if (team.equals("Aston Villa"))
			return "AVL";
		else if (team.equals("Norwich"))
			return "NOR";
		else if (team.equals("Everton"))
			return "EVE";
		else if (team.equals("Sunderland"))
			return "SUN";
		else if (team.equals("Fulham"))
			return "FUL";
		else if (team.equals("West Brom"))
			return "WBA";
		else if (team.equals("Southampton"))
			return "SOU";
		else if (team.equals("West Ham"))
			return "WHU";
		else if (team.equals("Cardiff"))
			return "CAR";
		else if (team.equals("Swansea"))
			return "SWA";
		else if (team.equals("Man United"))
			return "MUN";
		else if (team.equals("Crystal Palace"))
			return "CRY";
		else if (team.equals("Tottenham"))
			return "TOT";
		else if (team.equals("Chelsea"))
			return "CHE";
		else if (team.equals("Hull"))
			return "HUL";
		else if (team.equals("Man City"))
			return "MCI";		
		return "";
	}
	
	public static int getImageResource(String team){
		if (team.equals("Newcastle"))
			return R.drawable.icon_newcastle;
		else if (team.equals("Liverpool"))
			return R.drawable.icon_liverpool;
		else if (team.equals("Stoke"))
			return R.drawable.icon_stoke;
		else if (team.equals("Arsenal"))
			return R.drawable.icon_arsenal;
		else if (team.equals("Aston Villa"))
			return R.drawable.icon_villa;
		else if (team.equals("Norwich"))
			return R.drawable.icon_norwich;
		else if (team.equals("Everton"))
			return R.drawable.icon_everton;
		else if (team.equals("Sunderland"))
			return R.drawable.icon_sunderland;
		else if (team.equals("Fulham"))
			return R.drawable.icon_fulham;
		else if (team.equals("West Brom"))
			return R.drawable.icon_brom;
		else if (team.equals("Southampton"))
			return R.drawable.icon_south;
		else if (team.equals("West Ham"))
			return R.drawable.icon_ham;
		else if (team.equals("Cardiff"))
			return R.drawable.icon_cardiff;
		else if (team.equals("Swansea"))
			return R.drawable.icon_swansea;
		else if (team.equals("Man United"))
			return R.drawable.icon_united;
		else if (team.equals("Crystal Palace"))
			return R.drawable.icon_crystal;
		else if (team.equals("Tottenham"))
			return R.drawable.icon_spurs;
		else if (team.equals("Chelsea"))
			return R.drawable.icon_chelsea;
		else if (team.equals("Hull"))
			return R.drawable.icon_hull;
		else if (team.equals("Man City"))
			return R.drawable.icon_city;
		return 0;
	}
}
