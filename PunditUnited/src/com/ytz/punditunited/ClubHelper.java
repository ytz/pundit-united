package com.ytz.punditunited;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.style.ImageSpan;

public class ClubHelper {
	
	private static final Factory spannableFactory = Spannable.Factory
	        .getInstance();

	private static final Map<Pattern, Integer> club_emote = new HashMap<Pattern, Integer>();

	static {
	    addPattern(club_emote, "[Newcastle]", R.drawable.emote_newcastle);
	    addPattern(club_emote, "[Liverpool]", R.drawable.emote_liverpool);
	    addPattern(club_emote, "[Stoke]", R.drawable.emote_stoke);
	    addPattern(club_emote, "[Arsenal]", R.drawable.emote_arsenal);
	    addPattern(club_emote, "[Aston Villa]", R.drawable.emote_villa);
	    addPattern(club_emote, "[Norwich]", R.drawable.emote_norwich);
	    addPattern(club_emote, "[Everton]", R.drawable.emote_everton);
	    addPattern(club_emote, "[Sunderland]", R.drawable.emote_sunderland);
	    addPattern(club_emote, "[Fulham]", R.drawable.emote_fulham);
	    addPattern(club_emote, "[West Brom]", R.drawable.emote_brom);
	    addPattern(club_emote, "[Southampton]", R.drawable.emote_south);
	    addPattern(club_emote, "[West Ham]", R.drawable.emote_ham);
	    addPattern(club_emote, "[Cardiff]", R.drawable.emote_cardiff);
	    addPattern(club_emote, "[Swansea]", R.drawable.emote_swansea);
	    addPattern(club_emote, "[Man United]", R.drawable.emote_united);
	    addPattern(club_emote, "[Crystal Palace]", R.drawable.emote_crystal);
	    addPattern(club_emote, "[Tottenham]", R.drawable.emote_spurs);
	    addPattern(club_emote, "[Chelsea]", R.drawable.emote_chelsea);
	    addPattern(club_emote, "[Hull]", R.drawable.emote_hull);
	    addPattern(club_emote, "[Man City]", R.drawable.emote_city);
	}

	private static void addPattern(Map<Pattern, Integer> map, String smile,
	        int resource) {
	    map.put(Pattern.compile(Pattern.quote(smile)), resource);
	}
	
	public static boolean addClubEmote(Context context, Spannable spannable) {
	    boolean hasChanges = false;
	    for (Entry<Pattern, Integer> entry : club_emote.entrySet()) {
	        Matcher matcher = entry.getKey().matcher(spannable);
	        while (matcher.find()) {
	            boolean set = true;
	            for (ImageSpan span : spannable.getSpans(matcher.start(),
	                    matcher.end(), ImageSpan.class))
	                if (spannable.getSpanStart(span) >= matcher.start()
	                        && spannable.getSpanEnd(span) <= matcher.end())
	                    spannable.removeSpan(span);
	                else {
	                    set = false;
	                    break;
	                }
	            if (set) {
	                hasChanges = true;
	                spannable.setSpan(new ImageSpan(context, entry.getValue()),
	                        matcher.start(), matcher.end(),
	                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	            }
	        }
	    }
	    return hasChanges;
	}

	public static Spannable getClubEmoteText(Context context, CharSequence text) {
		text = Html.fromHtml(text.toString());
	    Spannable spannable = spannableFactory.newSpannable(text);
	    addClubEmote(context, spannable);
	    return spannable;
	}

	/**
	 * Given full name, return short name
	 * @param team
	 * @return
	 */
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
	
	/**
	 * Returns R.id of Club's logo image
	 * @param team
	 * @return
	 */
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
