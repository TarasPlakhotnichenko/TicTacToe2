package ru.tarasplakhotnichenko.tictactoe2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Map;
import java.lang.Math;


public class Memory {
    private final int dim = 3; //arbitrary digit != 1 or 0; for initial grid filling
    private final HashMap<Integer, Integer> playField = new HashMap<>();
    private final List<Integer> btns; //list of buttons
    private int theWinner;


    public Memory(List<Integer> btns) {
            for (int i = 0; i < btns.size(); i++) {
                this.playField.put(btns.get(i), dim );
            }
        this.btns = btns;
    }

    //write 1 or 0 to map
    public void addAnswer(Integer id, int m) {
        for (Map.Entry<Integer, Integer> entry : playField.entrySet()) {
            if (entry.getKey().equals(id)) {
                this.playField.put(entry.getKey(), m);
            }
        }
    }

    //Computer's move
    public Integer rnd() {
        Integer button;
        Random rn = new Random();
        ArrayList<Integer>  btnPool = new ArrayList<>();
        //loop through map to find unoccupied buttons and add those buttons to list
        for (Map.Entry<Integer, Integer> entry : this.playField.entrySet()) {
            if (entry.getValue().equals(this.dim)) {
                btnPool.add(entry.getKey());
            }
        }

        //return a random button from a pool of available buttons
        button = btnPool.get(rn.nextInt(btnPool.size()));
        this.playField.put(button, 0);
        return button;
    }

    public ArrayList<Integer> saveCurPlayField() {
        ArrayList<Integer>  playFieldtoSave = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : this.playField.entrySet()) {
            playFieldtoSave.add(entry.getKey());
            playFieldtoSave.add(entry.getValue());
        }
        return playFieldtoSave;
    }

    public  HashMap<Integer, Integer> restoreCurPlayField(ArrayList<Integer> pf) {
        for (int i = 0; i < pf.size()-1; i++) {
           this.playField.put(pf.get(i),pf.get(i+1));
           i++;
        }
        return this.playField;
    }

    //We can not tap the same button twice
    public  boolean checkLegalMove (Integer id) {
        for (Map.Entry<Integer, Integer> entry : this.playField.entrySet()) {
            if (entry.getKey().equals(id) && (entry.getValue().equals(1) || entry.getValue().equals(0)) ) {
                 return false;
            }
        }
        return true;
    }

    //Here we define a winner by checking out X or O triples or whatever it could be called when lining up a character in one row,column or diag
    public  boolean isWinner() {
        List<Integer> triple = new ArrayList<>();
        int oneSideDimention = (int) Math.sqrt(btns.size());

        //rows----------------------------------------------------------vvv
        //for (int j = 0; j < oneSideDimention; j++) {
        //row-------------------vvv
        for (int i = 0; i < oneSideDimention; i++) {
            for (Map.Entry<Integer, Integer> entry : this.playField.entrySet()) {
                if ((entry.getKey().equals(btns.get(i))) && ((entry.getValue().equals(1)) || (entry.getValue().equals(0))) ) {
                    triple.add(entry.getValue());
                }
            }
        }

        if ( oneSideDimention  == triple.size()) {
            if ((triple.get(0).equals(triple.get(1))) && (triple.get(0).equals(triple.get(2))) && (triple.get(0).equals(1)) ) {
                this.theWinner = 1;
                return true;
            } else {
                this.theWinner = 0;
                return true;
            }
        }

        //row-------------------^^^
       // }
        //rows----------------------------------------------------------^^^
        //to be continued


        return false;
    }

    public String getTheWinner() {
        if (this.theWinner == 1) {
            return "X";
        } else {
            return "0";
        }

    }

    //Is it all the grid filled?
    public boolean isFinish() {
        int countMap=0;
        for (Map.Entry<Integer, Integer> entry : this.playField.entrySet()) {
            if (entry.getValue().equals(1) || entry.getValue().equals(0)) {
                countMap++;
            }
        }
        if (countMap == btns.size()) {
            return true;
        }
        else {
            return false;
        }
    }



    }



