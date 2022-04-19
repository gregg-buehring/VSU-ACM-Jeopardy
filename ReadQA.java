package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadQA {
	File file;
	
	public ReadQA(String file) {
		this.file = new File(file);
	}
	
	public qa createQA() {
		qa questions = new qa();
		try {
			Scanner scan = new Scanner(file);
			while(scan.hasNext()) {
				String line = scan.nextLine();
				String[] token = line.split(":");
				if(token[0].equals("c")) {
					qa.addCategory(Integer.parseInt(token[1]), token[2]);
					System.out.println(token[0]);
				}
				else {
					System.out.println(token[0] + " " + token[1] + " " + token[2] + " " + token[3]);
					qa.addQuestion(Integer.parseInt(token[0]), Integer.parseInt(token[1]), token[2]);
					qa.addAnswer(Integer.parseInt(token[0]), Integer.parseInt(token[1]), token[3]);
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Does Not Exist:(");
			e.printStackTrace();
		}
		return questions;
	}

}
