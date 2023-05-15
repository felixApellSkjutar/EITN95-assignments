import java.util.Random;

public class Student {
	public static final int NORTHWEST = 1, NORTH = 2, NORTHEAST = 3, EAST = 4, SOUTHEAST = 5, SOUTH = 6, SOUTHWEST = 7, WEST = 8;

    private double coordX;
    private double coordY;
    private int[] relationships = new int[20];
    private boolean engaged;
    private Random slump = new Random();
    public int speed = 2; // changes in tasks, 2, 4, slump.nextInt(7) + 1;
    private double walkTimeStraight = 1.0/speed;
    private double walkTimeDiagonal = 1.0*Math.sqrt(2)/speed;
    private int id;
    private int direction = slump.nextInt(8) + 1;


    public Student(int id){
        coordX = 0.5 + slump.nextInt(20); //centering mid in square
        coordY = 0.5 + slump.nextInt(20);
        this.id = id;
    }

    public void walk(){
        // något med speed också, matchas med om de möts. Löser nog detta i QS:s TreatSignal.
        
        engaged = true;
        switch (direction){
            case NORTHWEST:
                if(checkOutOfBounds(coordX - 1, coordY + 1)){
                    coordX--;
                    coordY++;
                }
                break;
            case NORTH:
                if(checkOutOfBounds(coordX, coordY + 1)){
                    coordY++;
                }
                break;

            case NORTHEAST:
                if(checkOutOfBounds(coordX + 1, coordY + 1)){
                    coordX++;
                    coordY++;
                }
                break;

            case EAST:
                if(checkOutOfBounds(coordX + 1, coordY)){
                    coordX++;
                }
                break;

            case SOUTHEAST:
                if(checkOutOfBounds(coordX + 1, coordY - 1)){
                    coordX++;
                    coordY--;
                }
                break;

            case SOUTH:
                if(checkOutOfBounds(coordX, coordY - 1)){
                    coordY--;
                }
                break;
            
            case SOUTHWEST:
                if(checkOutOfBounds(coordX - 1, coordY - 1)){
                    coordX++;
                    coordY--;
                }
                break;

            case WEST:
                if(checkOutOfBounds(coordX, coordY - 1)){
                    coordY--;
                }
                break;
        }
        direction = slump.nextInt(8) + 1;
    }

    public void socialize(Student otherStudent){
        engaged = true;
        otherStudent.engaged = true;
        relationships[otherStudent.getID()]++;
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

    public boolean onSameSquare(Student otherStudent){
        return coordX == otherStudent.coordX && coordY == otherStudent.coordY;
    }

    private boolean checkOutOfBounds(double x, double y){
        return (x > -1 && y > -1) && (x < 40 && y < 40);
    }
}
