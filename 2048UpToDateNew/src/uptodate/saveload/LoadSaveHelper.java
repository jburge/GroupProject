package uptodate.saveload;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import uptodate.logic.SaveEntry;

public class LoadSaveHelper {

	public static void saveData(String _fileName, ArrayList<SaveEntry> myList){
		String fileName = _fileName;
		File f = new File(fileName);
		
		try{
			BufferedWriter wrtr = new BufferedWriter(new FileWriter(f));
			for(SaveEntry e: myList){
				wrtr.write(e.save());
				wrtr.newLine();
			}
			wrtr.close();
			
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	
	public static void loadData(String _fileName, ArrayList<SaveEntry> myList){
		String fileName = _fileName;
		File f = new File(fileName);
		try{
			BufferedReader rdr = new BufferedReader(new FileReader(f));
			String line;
			while((line = rdr.readLine()) != null){
				String[] tokens = line.split("\\|");
				myList.add(new SaveEntry(tokens[0], Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]), Integer.valueOf(tokens[3]), Integer.valueOf(tokens[4])));
			}
			rdr.close();
		}catch(Exception ex){
			System.out.println("Read Error");
			System.out.println(ex.getMessage());
		}
	}
	
	
}

