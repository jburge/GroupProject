package uptodate.logic;

import java.util.ArrayList;

import uptodate.saveload.LoadSaveHelper;

public class DataManager {
	private ArrayList<SaveEntry> saveList;
	
	public DataManager(){
		saveList = new ArrayList<SaveEntry>();
		LoadSaveHelper.loadData("uptodate.saveload/data.txt", saveList);
	}
	
	public void saveData(){
		LoadSaveHelper.saveData("uptodate.saveload/data.txt", saveList);
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
	
	public void saveGame(SaveEntry newElement){
		saveList.add(newElement);
	}
}
