package jsamples;

public class Caculator {
    public int add (int x, int y){
        return Math.addExact(x, y);
    }

    public int subtract(int x, int y){ return Math.subtractExact(x, y);}

    public int multiply(int x, int y){
        return Math.multiplyExact(x, y);
    }

    public int divide(int x, int y){
        return Math.floorDiv(x, y);
    }
}
