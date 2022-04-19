package application;

public class ReadQATest {
	public static void main(String[]args) {
		ReadQA read = new ReadQA("src/application/QA.txt");
		qa questions = read.createQA();
		System.out.println(questions.getCategory(1) + "\n" + questions.getQuestion(1, 1) + "\n" + questions.getAnswer(1, 1));
	}

}
