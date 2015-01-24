package uptodate.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import uptodate.logic.SaveEntry;

public class ViewSelectionPanel extends PopupPanel{

	private JLabel lblTitle;
	private JLabel lblBoardSize;
	private JLabel lblTimeLimit;
	private JComboBox selectSize;
	private JComboBox selectTime;
	private JTextArea textDisplay;
	public JButton btnReturn; 

	private ArrayList<SaveEntry> elements;
	private ArrayList<SaveEntry> selectedElements;
	private int selectedSize;
	private int selectedTime;
	
	
	public ViewSelectionPanel(int windowWidth, int windowHeight, int size, int time) {
		super(windowWidth, windowHeight);
		elements = new ArrayList<SaveEntry>();
		selectedElements = new ArrayList<SaveEntry>();
		selectedSize = size;
		selectedTime = time;
		
		this.setLayout(null);
		
		lblTitle = new JLabel("2048 Standings");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblTitle);
				
		lblBoardSize = new JLabel("Board Size:");
		this.add(lblBoardSize);
		
		selectSize = new JComboBox();
		selectSize.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				selectedSize = Integer.valueOf((String) selectSize.getSelectedItem());
			//	getElementsToDisplay();
			}
		});
		selectSize = ComboBoxSeed.seedSize(selectSize);
		this.add(selectSize);
		
		lblTimeLimit = new JLabel("Time Limit:");
		this.add(lblTimeLimit);
		selectTime = new JComboBox();
		selectTime.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if ((String) selectTime.getSelectedItem() == "No Limit"){
					selectedTime = 0;
					return;
			}
				selectedTime = Integer.valueOf((String) selectTime.getSelectedItem());
				getElementsToDisplay();
			}
		});
		selectTime = ComboBoxSeed.seedTime(selectTime);
		this.add(selectTime);
		
		textDisplay = new JTextArea();
		this.add(textDisplay);
		
		
		btnReturn = new JButton("Return to Menu");
		this.add(btnReturn);
	
		resetBounds(windowWidth, windowHeight);
		this.setVisible(false);
	}
	
	public void resetBounds(int windowWidth, int windowHeight){
		int menuWidthBuffer = windowWidth / 9;
		int menuHeightBuffer = windowHeight / 9;
		int menuWidth = windowWidth - menuWidthBuffer * 2;
		int menuHeight = windowHeight - menuHeightBuffer * 2;
		
		
		int heightUnit = menuHeight / 7;
		int buffer = menuHeight / 24;
		int fieldSize = heightUnit  - buffer;
		
		int sizeHeight = heightUnit;
		int timeHeight = sizeHeight + heightUnit;

		int scrollViewHeight = timeHeight + heightUnit;

		int returnHeight = scrollViewHeight + heightUnit * 3;
		
		int txtWidth = menuWidth;
		
		this.setBounds(menuWidthBuffer, menuHeightBuffer -25, menuWidth, menuHeight);

		lblTitle.setBounds(0, 0, menuWidth, heightUnit);

		lblBoardSize.setBounds(40, sizeHeight, txtWidth, fieldSize);
		selectSize.setBounds(menuWidth / 2 + 10, sizeHeight, menuWidth / 2 - 30, fieldSize);

		lblTimeLimit.setBounds(40, timeHeight,txtWidth, 25);
		selectTime.setBounds(menuWidth / 2 + 10, timeHeight, menuWidth / 2 - 30, fieldSize);
		
		textDisplay.setBounds(buffer, scrollViewHeight,menuWidth - buffer * 2, heightUnit * 3 - buffer);
		
		btnReturn.setBounds(menuWidth / 4, returnHeight, menuWidth / 2 , fieldSize);
	}
	
	private void getElementsToDisplay(){
		selectedElements.clear();
		String displayString = "";
		for(SaveEntry e: elements){
			if(e.getSize() == selectedSize && e.getTimeLimit() == selectedTime){
				selectedElements.add(e);
				displayString += e.toString() + "\n";
			}
		}
		
		// update field
		textDisplay.setText(displayString);
	}
	
	public void setArray(ArrayList<SaveEntry> dataArray){
		elements = dataArray;		
	}
	
	public void makeVisible(){
		this.setVisible(true);
		getElementsToDisplay();
	}
	

}
