import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
	//public int accumulated, noMeasurements;
	public Proc sendTo;
	public ArrayList<Student> students = new ArrayList<>();

	Random slump = new Random();

	public void TreatSignal(Signal x){
		Student student = x.student;
		switch (x.signalType){

			case WALK:{
				if (!student.isEngaged()){
					student.walk();
					
					// Check number of students on that square
					int numberOfStudents = (int) students.stream().filter(s -> student.onSameSquare(s) && s.getID() != student.getID()).count();
					if(numberOfStudents == 1){
						SignalList.SendSignal(MEET,this, time, student);
					} else if(numberOfStudents > 1){
						SignalList.SendSignal(WALK,this, time, student);
					}

					SignalList.SendSignal(WALK,this, time + student.getWalktime(), student);
				}
			} break;

			// Ej klar
			case MEET:{
				Student otherStudent = null;
				for(Student s : students){
					if(student.onSameSquare(s) && s.getID() != student.getID()){
						otherStudent = s;
						break;
					}
				}
				student.socialize(otherStudent);
				// Hur fungerar det? Är mötenas status ömsesidiga eller räknas de bara från ena sidan?
				// I så fall ändra i socialize så detta reflekteras.
				SignalList.SendSignal(WALK,this, time + student.getWalktime() + 1, student);

				
			} break;

			/* case MEASURE:{
				noMeasurements++;
				accumulated = accumulated + numberInQueue;
				SignalList.SendSignal(MEASURE, this, time + 2*slump.nextDouble());
			} break; */
		}
	}
}