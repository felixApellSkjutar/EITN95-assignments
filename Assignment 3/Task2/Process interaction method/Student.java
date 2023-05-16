import java.util.Arrays;
import java.util.Random;

public class Student {
	public static final int NORTHWEST = 1, NORTH = 2, NORTHEAST = 3, EAST = 4, SOUTHEAST = 5, SOUTH = 6, SOUTHWEST = 7, WEST = 8;

    public double coordX;
    public double coordY;
    public int[] relationships;
    private boolean engaged;
    private Random slump = new Random();
    public int speed = 4;//slump.nextInt(7) + 1; //4; // changes in tasks, 2, 4, slump.nextInt(7) + 1;
    private double walkTimeStraight = 1.0/speed;
    private double walkTimeDiagonal = 1.0*Math.sqrt(2)/speed;
    private int id;
    private int direction;
    private int tilesTilDirectionChange;


    public Student(int id){
        this.coordX = 0.5 + slump.nextInt(20); //centering mid in square
        this.coordY = 0.5 + slump.nextInt(20);
        this.id = id;
        this.direction = slump.nextInt(8) + 1;
        this.tilesTilDirectionChange = slump.nextInt(10) + 1;
        this.relationships = new int[20];
        this.engaged = false;

    }

    private void setNewDirection(){
        this.direction = slump.nextInt(8) + 1;
    }
    private int[] getStep(int dir) {
        switch(dir) {
            case NORTHWEST:
                return new int[]{-1, 1};
            case NORTH:
                return new int[]{0, 1};
            case NORTHEAST:
                return new int[]{1, 1};
            case EAST:
                return new int[]{1, 0};
            case SOUTHEAST:
                return new int[]{1, -1};
            case SOUTH:
                return new int[]{0, -1};
            case SOUTHWEST:
                return new int[]{-1, -1};
            case WEST:
                return new int[]{-1, 0};
            default:
                return new int[]{0, 0};
        }
    }
    private boolean outOfBounds(int dir) {
        int[] nextStep = getStep(dir);
        return checkOutOfBounds(coordX + nextStep[0], coordY + nextStep[1]);
    }


    public void walk(){
        this.tilesTilDirectionChange--;
        if(outOfBounds(this.direction) || this.tilesTilDirectionChange == 0) {
            setNewDirection();
            this.tilesTilDirectionChange = slump.nextInt(10) + 1;
        } else {
            int[] nextStep = getStep(this.direction);
            this.coordX += nextStep[0];
            this.coordY += nextStep[1];
        }
    }


    public void socialize(Student otherStudent){
        //System.out.println("student" + id);
        //System.out.println("otherstudent" + otherStudent.getID());
        engaged = true;
        //otherStudent.engaged = true;
        relationships[otherStudent.getID()]++;
        //System.out.println("relation till other " + relationships[otherStudent.getID()]);

        //otherStudent.relationships[id]++;
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
        return x < 0 || x > 20 || y < 0 || y > 20;
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
//        System.out.println(id + " är klar");
        return true;
    }
}
