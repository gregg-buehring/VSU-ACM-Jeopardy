package application;

public class qa {
	private static String[] categories = new String[5];
	private static String[][] questions = new String[5][5];
	private static String[][] answers = new String[5][5];
	
	public qa(){}
	
	public static boolean addCategory(int num, String c) {
		if((num < 1)||(num > 5)) {
			return false;
		}
		categories[num-1] = c;
		return true;
	}
	
	public static boolean addQuestion(int row, int col, String q) {
		if((row < 1)||(row > 5)||(col < 1)||(col >5)) {
			return false;
		}
		questions[row-1][col-1] = q;
		return true;
	}
	
	public static boolean addAnswer(int row, int col, String a) {
		if((row < 1)||(row > 5)||(col < 1)||(col >5)) {
			return false;
		}
		answers[row-1][col-1] = a;
		return true;
	}
	
	public static String getCategory(int num) {
		if((num < 1)||(num > 5)) {
			return "Invalid Number";
		}
		if(categories[num-1]==null) {
			return "Null";
		}
		return categories[num-1];
	}
	
	public static String getQuestion(int row, int col) {
		if((row < 1)||(row > 5)||(col < 1)||(col >5)) {
			return "Invalid Number";
		}
		if(questions[row-1][col-1]==null) {
			return "Null";
		}
		return questions[row-1][col-1];
	}
	
	public static String getAnswer(int row, int col) {
		if((row < 1)||(row > 5)||(col < 1)||(col >5)) {
			return "Invalid Number";
		}
		if(answers[row-1][col-1]==null) {
			return "Null";
		}
		return answers[row-1][col-1];
	}
}
