import java.util.*;
import java.io.*;

// This class defines a simple queuing system with one server. It inherits Proc so that we can use time and the
// signal names without dot notation
class QS extends Proc{
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
						SignalList.SendSignal(MEET, this, time, student);
					} 
					else {
						SignalList.SendSignal(WALK,this, time + student.getWalktime(), student);
					}
				 } 
			} break;

			case STOP_ENGAGE:{
				student.setEngagedStatus(false);
				SignalList.SendSignal(WALK, this, time + student.getWalktime(), student);
			} break;
			
			case MEET:{
				Student otherStudent = null;
				for(Student s : students){
					if(student.onSameSquare(s) && s.getID() != student.getID()){
						otherStudent = s;
						break;
					}
				}
				student.socialize(otherStudent);
				otherStudent.socialize(student);
				SignalList.SendSignal(STOP_ENGAGE, this, time + 60, otherStudent);
				SignalList.SendSignal(STOP_ENGAGE, this, time + 60, student);
			} break;

		}
	}

	public boolean finished(){
		for (Student s : students){
			if(!s.finished()){
				return false;
			}
		}
		return true;
	}
}