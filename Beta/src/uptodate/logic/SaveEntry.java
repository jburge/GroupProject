package uptodate.logic;

public class SaveEntry {
	
	private String user;
	private int boardSize;
	private int highestTile;
	private int score;
	private int timeLimit;
	
	public SaveEntry(String _user, int _boardSize, int _highestTile, int _score, int _timeLimit){
		user = _user;
		boardSize = _boardSize;
		highestTile = _highestTile;
		score = _score;
		timeLimit = _timeLimit;
	}
	
	@Override
	public String toString(){
		return user + ":     " + highestTile  + "     " + score;
	}
	
	public int getSize(){
		return boardSize;
	}
	
	public int getTimeLimit(){
		return timeLimit;
	}
	
	public String save(){
		return user + "|" + boardSize + "|" + highestTile + "|" + score + "|" + timeLimit;
	}
}
