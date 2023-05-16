import java.util.Arrays;
import java.util.Random;

public class Student {
	public static final int NORTHWEST = 1, NORTH = 2, NORTHEAST = 3, EAST = 4, SOUTHEAST = 5, SOUTH = 6, SOUTHWEST = 7, WEST = 8;

    private double coordX;
    private double coordY;
    private int[] relationships;
    private boolean engaged;
    private Random slump = new Random();
    public int speed = 4*60; // changes in tasks, 2, 4, slump.nextInt(7) + 1;
    private double walkTimeStraight = 1.0/speed;
    private double walkTimeDiagonal = 1.0*Math.sqrt(2)/speed;
    private int id;
    private int direction;
    private int tilesTilDirectionChange;


    public Student(int id){
        coordX = 0.5 + slump.nextInt(20); //centering mid in square
        coordY = 0.5 + slump.nextInt(20);
        this.id = id;
        direction = slump.nextInt(8) + 1;
        tilesTilDirectionChange = slump.nextInt(10) + 1;
        relationships = new int[20];
        engaged = false;

    }

    public void walk(){
        //engaged = false;
        switch (direction){
            case NORTHWEST:
                if(checkOutOfBounds(coordX - 1, coordY + 1)){
                    coordX--;
                    coordY++;
                    direction();
                } else {
                    direction = slump.nextInt(8) + 1;
                }
                break;
            case NORTH:
                if(checkOutOfBounds(coordX, coordY + 1)){
                    coordY++;
                    direction();
                } else {
                    direction = slump.nextInt(8) + 1;
                }
                break;

            case NORTHEAST:
                if(checkOutOfBounds(coordX + 1, coordY + 1)){
                    coordX++;
                    coordY++;
                    direction();
                } else {
                    direction = slump.nextInt(8) + 1;
                }
                break;

            case EAST:
                if(checkOutOfBounds(coordX + 1, coordY)){
                    coordX++;
                    direction();
                } else {
                    direction = slump.nextInt(8) + 1;
                }
                break;

            case SOUTHEAST:
                if(checkOutOfBounds(coordX + 1, coordY - 1)){
                    coordX++;
                    coordY--;
                    direction();
                } else {
                    direction = slump.nextInt(8) + 1;
                }
                break;

            case SOUTH:
                if(checkOutOfBounds(coordX, coordY - 1)){
                    coordY--;
                    direction();
                } else {
                    direction = slump.nextInt(8) + 1;
                }
                break;
            
            case SOUTHWEST:
                if(checkOutOfBounds(coordX - 1, coordY - 1)){
                    coordX++;
                    coordY--;
                    direction();
                } else {
                    direction = slump.nextInt(8) + 1;
                }
                break;

            case WEST:
                if(checkOutOfBounds(coordX, coordY - 1)){
                    coordY--;
                    direction();
                } else {
                    direction = slump.nextInt(8) + 1;
                }
                break;
        }
        //direction();
        //direction = slump.nextInt(8) + 1;
    }

    public void socialize(Student otherStudent){
        //System.out.println("student" + id);
        //System.out.println("otherstudent" + otherStudent.getID());
        engaged = true;
        otherStudent.engaged = true;
        relationships[otherStudent.getID()]++;
        //System.out.println("relation till other " + relationships[otherStudent.getID()]);

        otherStudent.relationships[id]++;
    }

    public double getWalktime(){
        if(direction == NORTHWEST || direction == NORTHEAST || direction == SOUTHEAST || direction == SOUTHWEST) {
            return walkTimeDiagonal;
        }
        return walkTimeStraight;
    }

    public int getID(){
        return id;
    }

    public boolean isEngaged(){
        return engaged;
    }

    public void setEngagedStatus(boolean status){
        engaged = status;
    }

    public boolean onSameSquare(Student otherStudent){
        return coordX == otherStudent.coordX && coordY == otherStudent.coordY && otherStudent.getID() != id;
    }

    private boolean checkOutOfBounds(double x, double y){
        return (x > -1 && y > -1) && (x < 40 && y < 40);
    }

    private void direction(){
        tilesTilDirectionChange--;
        if(tilesTilDirectionChange == 0){
            tilesTilDirectionChange = slump.nextInt(10) + 1;
            direction = slump.nextInt(8) + 1;
        }
    }

    public boolean finished() {
        //System.out.println(id + "s relation");
        //System.out.println(Arrays.toString(relationships));
        //System.out.println(engaged);
        
        for(int i = 0; i < relationships.length; i++){
            if(relationships[i] == 0 && i != id){
                return false;
            }
        }
        System.out.println(id + " är klar");
        return true;
    }
}
