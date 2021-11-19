package ui.display.components;

import javax.swing.*;
import java.awt.*;

public class Bar extends JComponent {
    int width;
    int[] dataArray;
    float standardDev;
    int panesSize;

    public Bar(int[] dataArray, int widthOfBucket, float standardDev) {
        width = widthOfBucket;
        this.dataArray = dataArray;
        this.standardDev = standardDev;
        panesSize = width * dataArray.length;

    }

    public void paint(Graphics g) {
        ((Graphics2D) g).setPaint(Color.BLUE);
        int x  = 0;
        for (int i: dataArray) {
            g.drawRect(x,10,width,i);
            g.fillRect(x,10,width, i);
            x  = x + width;
        }
        ((Graphics2D) g).setPaint(Color.BLACK);
        x = 400;
        int stDevRight = x;
        int stDevLeft = x;
        int roundedStDev = (int) Math.floor(standardDev);
        for (int i = x; i <= panesSize; i = i + roundedStDev) {
            g.drawRect(stDevLeft,10,1,400);
            g.fillRect(stDevLeft,10,1, 400);
            g.drawRect(stDevRight,10,1,400);
            g.fillRect(stDevRight,10,1, 400);
            stDevLeft = stDevLeft - roundedStDev;
            stDevRight = stDevRight + roundedStDev;
        }

        setSize(new Dimension(800,800));
    }
}
