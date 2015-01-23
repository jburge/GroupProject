package uptodate.logic;

import java.util.ArrayList;

import uptodate.saveload.LoadSaveHelper;

public class DataManager {
	private ArrayList<SaveEntry> saveList;
	//private String fileName = "B:\\Documents\\SourceTree\\Java\\2048UpToDate\\src\\resources\\data.txt";
	private String fileName = "\\resources\\data.txt";

			//"2048UpToDate/src/resources/data.txt";

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
	
	public void saveGame(SaveEntry newElement){
		saveList.add(newElement);
	}
}
