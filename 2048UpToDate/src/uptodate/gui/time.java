package uptodate.gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Transparency;

import javax.swing.JPanel;

import uptodate.gui.*;

public class time extends JPanel implements Runnable{
        static int seconds = 10;
        private Graphics2D g;
        time(Graphics2D g) {
            this.g = g;
        }
//        public void paint (Graphics g){
//            super.paint(g);
//            GUI tmp=new GUI();
//    		g.setFont(new Font("Arial", Font.PLAIN, 18));
//    		g.drawString("Time: " + seconds, (tmp.windowWidth / 2)-100 ,tmp.windowHeight - 35);
//        }
        @Override
        public void run() {
            int seconds = time.seconds;
            System.out.println("time: "+seconds);
            while (seconds-- > 0) {
                System.out.println("time: "+seconds);
//                GUI tmp=new GUI();
//                super.paint(g);
//        		g.setFont(new Font("Arial", Font.PLAIN, 18));
//        		g.drawString("Time: " + seconds, (tmp.windowWidth / 2)-100 ,tmp.windowHeight - 35);
                
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.repaint();

            }
        }
    }