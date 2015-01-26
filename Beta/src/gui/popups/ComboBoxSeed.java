package gui.popups;

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
		myBox.addItem("Modern");
		myBox.addItem("---");
		return myBox;
	}
	public static JComboBox seedTime(JComboBox myBox){
		myBox.addItem("No Limit");
		myBox.addItem("2");
		myBox.addItem("5");
		myBox.addItem("15");
		myBox.addItem("30");
		return myBox;
	}
}
