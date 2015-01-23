package uptodate.popups;

import javax.swing.JComboBox;

public class ComboBoxSeed {

	public static JComboBox seedSize(JComboBox myBox){
		myBox.addItem("3");
		myBox.addItem("4");
		myBox.addItem("5");
		return myBox;
	}
	public static JComboBox seedColor(JComboBox myBox){
		myBox.addItem("Default");
		myBox.addItem("Blue");
		myBox.addItem("---");
		return myBox;
	}
	public static JComboBox seedTime(JComboBox myBox){
		myBox.addItem("No Limit");
		return myBox;
	}
}
