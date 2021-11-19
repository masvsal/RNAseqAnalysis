package ui.display.components;

import ui.display.Graph;

import javax.swing.*;
import java.awt.*;

//histogram graphics object.
public class Bar extends JComponent {
    int width; //width of individual buckets in histogram.
    int[] dataArray;    //data to be rendered
    float standardDev;  //standard deviation in data.
    int panesSize;      //size of area in which graph is rendered.
    private float lowestFC; //lowest fold change in data array
    private float highestFC;    //highest fold change in data array


    //MODIFIES: this
    //EFFECTS: sets passed values to appropriate fields
    public Bar(int[] dataArray, int widthOfBucket, float standardDev, float lowestFC, float highestFC, int paneWidth) {
        width = widthOfBucket;
        this.dataArray = dataArray;
        this.standardDev = standardDev;
        panesSize = paneWidth;
        this.lowestFC = lowestFC;
        this.highestFC = highestFC;
        System.out.println(panesSize);
        System.out.println(widthOfBucket);

    }
    //modifies: this
    //effects: renders graphics object. Bars are rendered on top of panel, pointing downward. Bar representing no
    //fold change rendered in red. Standard deviation bars rendered  in gray. Maximum and minimum fold change values
    //rendered in right and left corners of frame.

    public void paint(Graphics g) {
        ((Graphics2D) g).setPaint(Color.BLUE);
        int x  = 0;
        int counter = 0;
        int numberOfBuckets = (int) Math.ceil(Math.abs(lowestFC) / 10);
        System.out.println(numberOfBuckets);
        for (int i: dataArray) {
            if (counter == numberOfBuckets) {
                ((Graphics2D) g).setPaint(Color.red);
            }
            if (i <= 3 && i > 0) {
                g.drawString(String.valueOf(i), x, 50);
            }
            g.drawRect(x,30,width,i);
            g.fillRect(x,30,width, i);
            x  = x + width;
            counter = counter + 1;
            ((Graphics2D) g).setPaint(Color.blue);

        }
        ((Graphics2D) g).setPaint(Color.GRAY);

        drawStandardDev(g);

        g.drawString(String.valueOf(Math.floor(lowestFC)),10,20);
        g.drawString(String.valueOf(Math.floor(highestFC)), panesSize - 50, 20);


        setSize(new Dimension(panesSize, panesSize));
    }

    //modifies: this
    //creates renders deviation lines in gray on histogram object.
    private void drawStandardDev(Graphics g) {
        int x = panesSize / 2;
        int stDevRight = x;
        int stDevLeft = x;
        int roundedStDev = (int) Math.floor(standardDev);
        for (int i = x; i <= panesSize; i = i + roundedStDev) {
            g.drawRect(stDevLeft,30,1,400);
            g.fillRect(stDevLeft,30,1, 400);
            g.drawRect(stDevRight,30,1,400);
            g.fillRect(stDevRight,30,1, 400);
            stDevLeft = stDevLeft - roundedStDev;
            stDevRight = stDevRight + roundedStDev;
        }
    }
}
