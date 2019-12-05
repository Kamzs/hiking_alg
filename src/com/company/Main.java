package com.company;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Main {

    static int wys;
    static int szer;
    static int[][] map;
    static int result;


    public static void main(String args[]) throws FileNotFoundException{

        long t1 = System.currentTimeMillis();

        System.setIn(new FileInputStream("src/com/company/input"));
        Scanner s = new Scanner(System.in);

        int NO_CASES = s.nextInt();

        //do zmiany na koneic
        for (int a = 0; a < NO_CASES; a++) {
            result=1000000;
            wys = s.nextInt();
            szer = s.nextInt();

            map = new int[wys][szer];
            Point start = null;
            Point end = null;
            for (int b = 0; b < wys; b++) {
                for (int c = 0; c < szer; c++) {
                    map[b][c] = s.nextInt();
                    if (map[b][c] == 2) {
                        start = new Point(b,c);
                    }
                    if (map[b][c] == 3) {
                        end = new Point(b,c);
                    }
                }
            }

            System.out.println(Arrays.deepToString(map));

            int maxSingleClimb =1;

            BFS(start,end,maxSingleClimb);
            System.out.println(result);

            long t2 = System.currentTimeMillis();
            System.out.println(" || runtime was : " + (t2 - t1) + " ms");

        }
    }




    static void BFS(Point start, Point end, int maxSingleClimb) {

        if(maxSingleClimb<=wys)
        {

            int [][] roadsToParticular = new int [wys][szer];
            boolean [][] visitedParticular = new boolean [wys][szer];

            ListFF listOfNodes = new ListFF();

            listOfNodes.add_back(start);
            //roadsToParticular[start.getWys()][start.getSzer()]=0;

            while(!(listOfNodes.length()==0)) {

                Point currPoint = (Point)listOfNodes.pop_front();

                if(visitedParticular[currPoint.getWys()][currPoint.getSzer()]==true)continue;

                visitedParticular[currPoint.getWys()][currPoint.getSzer()]=true;

                Point pointUpAndDown = new Point (currPoint.getWys(),currPoint.getSzer());
                Point pointToGoLeft = new Point (currPoint.getWys(),currPoint.getSzer()-1);
                Point pointToGoRight = new Point (currPoint.getWys(),currPoint.getSzer()+1);

                for(int z=1; z <= maxSingleClimb; z++) {

                    if(checkPoint(new Point(pointUpAndDown.getWys()-z,pointUpAndDown.getSzer()))) {

                        if(map[pointUpAndDown.getWys()-z][pointUpAndDown.getSzer()]==1) {

                            if(roadsToParticular[pointUpAndDown.getWys()-z][pointUpAndDown.getSzer()]==0) {
                                listOfNodes.add_back(new Point(pointUpAndDown.getWys()-z,pointUpAndDown.getSzer()));

                                roadsToParticular[pointUpAndDown.getWys()-z][pointUpAndDown.getSzer()]=
                                        roadsToParticular[currPoint.getWys()][currPoint.getSzer()]+1;
                            }
                        }
                        if (pointUpAndDown.getWys()-z==end.getWys()&&pointUpAndDown.getSzer()==end.getSzer()) {

                            result = maxSingleClimb;

                        }
                    }
                }
                for(int z=1; z <= maxSingleClimb; z++) {

                    if(checkPoint(new Point(pointUpAndDown.getWys()+z,pointUpAndDown.getSzer()))) {

                        if(map[pointUpAndDown.getWys()+z][pointUpAndDown.getSzer()]==1) {

                            if(roadsToParticular[pointUpAndDown.getWys()+z][pointUpAndDown.getSzer()]==0)
                            {

                                listOfNodes.add_back(new Point(pointUpAndDown.getWys()+z,pointUpAndDown.getSzer()));

                                roadsToParticular[pointUpAndDown.getWys()+z][pointUpAndDown.getSzer()]=
                                        roadsToParticular[currPoint.getWys()][currPoint.getSzer()]+1;
                            }
                        }
                        if (pointUpAndDown.getWys()+z==end.getWys()&&pointUpAndDown.getSzer()==end.getSzer()) {
                            result = maxSingleClimb;

                        }
                    }
                }
                if(checkPoint(pointToGoLeft)) {

                    if(map[pointToGoLeft.getWys()][pointToGoLeft.getSzer()]==1) {

                        if(roadsToParticular[pointToGoLeft.getWys()][pointToGoLeft.getSzer()]==0) {

                            listOfNodes.add_back(pointToGoLeft);

                            roadsToParticular[pointToGoLeft.getWys()][pointToGoLeft.getSzer()]=
                                    roadsToParticular[currPoint.getWys()][currPoint.getSzer()]+1;
                        }
                    }
                    if (pointToGoLeft.getWys()==end.getWys()&&pointToGoLeft.getSzer()==end.getSzer()) {

                        result = maxSingleClimb;
                    }

                }

                if(checkPoint(pointToGoRight)) {

                    if(map[pointToGoRight.getWys()][pointToGoRight.getSzer()]==1) {
                        if(roadsToParticular[pointToGoRight.getWys()][pointToGoRight.getSzer()]==0) {
                            listOfNodes.add_back(pointToGoRight);

                            roadsToParticular[pointToGoRight.getWys()][pointToGoRight.getSzer()]=
                                    roadsToParticular[currPoint.getWys()][currPoint.getSzer()]+1;
                        }
                    }
                    if (pointToGoRight.getWys()==end.getWys()&&pointToGoRight.getSzer()==end.getSzer()) {

                        result = maxSingleClimb;
                    }

                }
            }
            if(result == 1000000){
                BFS(start,end,maxSingleClimb+1);
            }


        }

    }


    static boolean checkPoint(Point point) {
        if(		point.getWys()<=wys-1
                &&
                point.getWys()>=0
                &&
                point.getSzer()<=szer-1
                &&
                point.getSzer()>=0)
            return true;
        else return false;
    }
}

class Point{
    int wys;
    int szer;
    Point (int y, int x){
        this.wys = y;
        this.szer =x;
    }
    public int getWys() {
        return wys;
    }
    public int getSzer() {
        return szer;
    }

}

class ListFF{

    int size = 1;
    Object [] table = new Object [size];
    int index_of_first =0;
    int index_for_new =0;

    void add_back (Object x) {
        if (index_for_new < size) {
            table[index_for_new]=x;
            index_for_new++;
        }
        else {
            makeBigger();
            add_back(x);
        }
    }

    int length () {return index_for_new - index_of_first;}

    Object pop_front() {
        if(length()>0) {
            index_of_first++;
            return table[index_of_first-1];
        }
        else return null;
    }


    void makeBigger() {
        size*=2;
        Object[] newTable = new Object[size];
        for (int i=0; i < table.length; i++) {
            newTable[i] = table[i];
        }
        table = newTable;
    }
}
