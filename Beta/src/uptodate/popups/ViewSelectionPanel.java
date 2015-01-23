package uptodate.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import uptodate.logic.SaveEntry;

public class ViewSelectionPanel extends PopupPanel{

	private JLabel lblMenu;
	private JLabel lblBoardSize;
	private JLabel lblTimeLimit;
	private JComboBox selectSize;
	private JComboBox selectTime;
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
		
		lblMenu = new JLabel("Menu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblMenu);
				
		lblBoardSize = new JLabel("Board Size:");
		this.add(lblBoardSize);
		
		selectSize = new JComboBox();
		selectSize.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				selectedSize = Integer.valueOf((String) selectSize.getSelectedItem());
			}
		});
		selectSize = ComboBoxSeed.seedSize(selectSize);
		this.add(selectSize);
		
		lblTimeLimit = new JLabel("Time Limit:");
		selectTime = new JComboBox();
		selectTime.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if ((String) selectTime.getSelectedItem() == "No Limit"){
					selectedTime = 0;
					return;
			}
				selectedTime = Integer.valueOf((String) selectTime.getSelectedItem());
			}
		});
		selectTime = ComboBoxSeed.seedTime(selectTime);
		this.add(lblTimeLimit);
		
		selectTime = new JComboBox();
		selectTime.addItem("No Limit");
		this.add(selectTime);
		
		btnReturn = new JButton("Return to Menu");
		this.add(btnReturn);
	
		getElementsToDisplay();
	}
	
	private void getElementsToDisplay(){
		selectedElements.clear();
		for(SaveEntry e: elements){
			if(e.getSize() == selectedSize && e.getTimeLimit() == selectedTime){
				selectedElements.add(e);
			}
		}
	}
	
	public void setArray(ArrayList<SaveEntry> dataArray){
		elements = dataArray;		
	}
	

}
