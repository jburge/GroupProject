package datastorage;

import java.util.ArrayList;
import java.util.Collections;

import readwrite.LoadSaveHelper;

public class DataManager {
	private ArrayList<SaveEntry> saveList;
	private String fileName = "src/resources/data.txt";


;
	
	public DataManager(){
		saveList = new ArrayList<SaveEntry>();
		LoadSaveHelper.loadData(fileName, saveList);
	}
	
	public void saveData(){
		LoadSaveHelper.saveData(fileName, saveList);
	}
	
	public ArrayList<SaveEntry> getSet(int boardSize, int timeLimit){
		ArrayList<SaveEntry> temp = new ArrayList<SaveEntry>();
		for(SaveEntry e: saveList){
			if(e.getSize() == boardSize && e.getTimeLimit() == timeLimit){
				temp.add(e);
			}
		}
		return temp;
	}
	
	public ArrayList<SaveEntry> getSet(){
		return saveList;
	}
	
	public void saveGame(SaveEntry newElement){
		saveList.add(newElement);
		Collections.sort(saveList, SaveEntry.scoreComparator);
	}
}
