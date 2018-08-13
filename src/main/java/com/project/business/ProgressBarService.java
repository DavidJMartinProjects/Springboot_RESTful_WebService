package com.project.business;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ProgressBarService {
	
	private static Map<Integer, String> levelBoundaries = new HashMap<>();
	
	private static int levelPlusOne;
	private static String boundaryStrHigh;		
	private static long boundaryIntLow;
	private static long boundaryIntHigh;
	private static long xpBoundaryDiff;		
	private static long boundaryInt;
	private static long currentXpInt;
	private static long difference;
	private static float percentage;
	private static DecimalFormat df;
	private static String boundaryStrLow;
	
	static {
		levelBoundaries.put(1, "0");
		levelBoundaries.put(2, "525");
		levelBoundaries.put(3, "1760");
		levelBoundaries.put(4, "3781");
		levelBoundaries.put(5, "7184");
		levelBoundaries.put(6, "12186");
		levelBoundaries.put(7, "19324");
		levelBoundaries.put(8, "29377");
		levelBoundaries.put(9, "43181");
		levelBoundaries.put(10, "61693");
		levelBoundaries.put(11, "85990");
		levelBoundaries.put(12, "117506");
		levelBoundaries.put(13, "157384");
		levelBoundaries.put(14, "207736");
		levelBoundaries.put(15, "269997");
		levelBoundaries.put(16, "346462");
		levelBoundaries.put(17, "439268");
		levelBoundaries.put(18, "551295");
		levelBoundaries.put(19, "685171");
		levelBoundaries.put(20, "843709");
		levelBoundaries.put(21, "1030734");
		levelBoundaries.put(22, "1249629");
		levelBoundaries.put(23, "1504995");
		levelBoundaries.put(24, "1800847");
		levelBoundaries.put(25, "2142652");
		levelBoundaries.put(26, "2535122");
		levelBoundaries.put(27, "2984677");
		levelBoundaries.put(28, "3496798");
		levelBoundaries.put(29, "4080655");
		levelBoundaries.put(30, "4742836");
		levelBoundaries.put(31, "5490247");
		levelBoundaries.put(32, "6334393");
		levelBoundaries.put(33, "7283446");
		levelBoundaries.put(34, "8384398");
		levelBoundaries.put(35, "9541110");
		levelBoundaries.put(36, "10874351");
		levelBoundaries.put(37, "12361842");
		levelBoundaries.put(38, "14018289");
		levelBoundaries.put(39, "15859432");
		levelBoundaries.put(40, "17905634");
		levelBoundaries.put(41, "20171471");
		levelBoundaries.put(42, "22679999");
		levelBoundaries.put(43, "25456123");
		levelBoundaries.put(44, "28517857");
		levelBoundaries.put(45, "31897771");
		levelBoundaries.put(46, "35621447");
		levelBoundaries.put(47, "39721017");
		levelBoundaries.put(48, "44225461");
		levelBoundaries.put(49, "49176560");
		levelBoundaries.put(50, "54607467");
		levelBoundaries.put(51, "60565335");
		levelBoundaries.put(52, "67094245");
		levelBoundaries.put(53, "74247659");
		levelBoundaries.put(54, "82075627");
		levelBoundaries.put(55, "90631041");
		levelBoundaries.put(56, "99984974");
		levelBoundaries.put(57, "110197515");
		levelBoundaries.put(58, "121340161");
		levelBoundaries.put(59, "133497202");
		levelBoundaries.put(60, "146749362");
		levelBoundaries.put(61, "161191120");
		levelBoundaries.put(62, "176922628");
		levelBoundaries.put(63, "194049893");
		levelBoundaries.put(64, "212684946");
		levelBoundaries.put(65, "232956711");
		levelBoundaries.put(66, "255001620");
		levelBoundaries.put(67, "278952403");
		levelBoundaries.put(68, "304972236");
		levelBoundaries.put(69, "333233648");
		levelBoundaries.put(70, "363906163");
		levelBoundaries.put(71, "397194041");
		levelBoundaries.put(72, "433312945");
		levelBoundaries.put(73, "472476370");
		levelBoundaries.put(74, "514937180");
		levelBoundaries.put(75, "560961898");
		levelBoundaries.put(76, "610815862");
		levelBoundaries.put(77, "664824416");
		levelBoundaries.put(78, "723298169");
		levelBoundaries.put(79, "786612664");
		levelBoundaries.put(80, "855129128");
		levelBoundaries.put(81, "929261318");
		levelBoundaries.put(82, "1009443795");
		levelBoundaries.put(83, "1096169525");
		levelBoundaries.put(84, "1189918242");
		levelBoundaries.put(85, "1291270350");
		levelBoundaries.put(86, "1400795257");
		levelBoundaries.put(87, "1519130326");
		levelBoundaries.put(88, "1646943474");
		levelBoundaries.put(89, "1784977296");
		levelBoundaries.put(90, "1934009687");
		levelBoundaries.put(91, "2094900291");
		levelBoundaries.put(92, "2268549086");
		levelBoundaries.put(93, "2455921256");
		levelBoundaries.put(94, "2658074992");
		levelBoundaries.put(95, "2876116901");
		levelBoundaries.put(96, "3111280300");
		levelBoundaries.put(97, "3364828162");
		levelBoundaries.put(98, "3638186694");
		levelBoundaries.put(99, "3932818530");
		levelBoundaries.put(100, "4250334444");	
	}
	
	public ProgressBarService() {		
	
	}
	
	public static String getProgressPercentage(int level, String currentXp) {
		boundaryStrLow = ProgressBarService.levelBoundaries.get(level);
		if(level != 100) {
			levelPlusOne = level+1;			
			boundaryStrHigh = ProgressBarService.levelBoundaries.get(levelPlusOne);			
			boundaryIntLow = Long.parseLong(boundaryStrLow);
			boundaryIntHigh = Long.parseLong(boundaryStrHigh);
			xpBoundaryDiff = boundaryIntHigh - boundaryIntLow;					
			boundaryInt = Long.parseLong(boundaryStrLow);
			currentXpInt = Long.parseLong(currentXp);
			difference = currentXpInt - boundaryInt;			
			percentage = ((difference*100)/xpBoundaryDiff);			
			df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			return df.format(percentage).toString();	
		}		
		return "100";
	}

}
