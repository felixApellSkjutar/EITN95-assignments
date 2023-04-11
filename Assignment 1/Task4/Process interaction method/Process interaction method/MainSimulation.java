import java.util.*;
import java.io.*;

//Denna klass �rver Global s� att man kan anv�nda time och signalnamnen utan punktnotation
//It inherits Proc so that we can use time and the signal names without dot notation


public class MainSimulation extends Global{

    public static void main(String[] args) throws IOException {

    	//Signallistan startas och actSignal deklareras. actSignal �r den senast utplockade signalen i huvudloopen nedan.
    	// The signal list is started and actSignal is declaree. actSignal is the latest signal that has been fetched from the 
    	// signal list in the main loop below.

    	Signal actSignal;
    	new SignalList();

    	//H�r nedan skapas de processinstanser som beh�vs och parametrar i dem ges v�rden.
    	// Here process instances are created (two queues and one generator) and their parameters are given values. 

    	QS Q1 = new QS();
    	Q1.sendTo = null;

    	Gen Generator = new Gen();
    	Generator.lambda = 5; //Generator ska generera en kund var femte minut
    	Generator.sendTo = Q1; //De genererade kunderna ska skickas till k�systemet QS

		//Gen och QS ärver båda från Proc - process.
		//Gen genererar kunder 
		//metoden treatSignal() hanterar den generearde kunden
		//en switch sats som lägger in en Arrival vid tidpunkten den genereras samt en READY vid tidpunkten då
		//kunden blivit hanterad av en server.

		//QS
		//Själva kön - räknar hur många som står i kön, accumulated etc.
		//sendto - var kunden som hanterats ska skickas


    	//H�r nedan skickas de f�rsta signalerna f�r att simuleringen ska komma ig�ng.
    	//To start the simulation the first signals are put in the signal list

    	SignalList.SendSignal(READY, Generator, time);
    	SignalList.SendSignal(MEASURE, Q1, time);


    	// Detta �r simuleringsloopen:
    	// This is the main loop

    	while ((Q1.totalArrivedNormal + Q1.totalArrivedSpecial) < 10000){
    		actSignal = SignalList.FetchSignal();
    		time = actSignal.arrivalTime;
    		actSignal.destination.TreatSignal(actSignal);
    	}

    	//Slutligen skrivs resultatet av simuleringen ut nedan:
    	//Finally the result of the simulation is printed below:

//    	System.out.println("Mean number of customers in queuing system: " + 1.0*Q1.accumulated/Q1.nmbrMeasurements);

		System.out.println("Time:" + time);
		System.out.println("Mean number of customers in queuing system: " + 1.0*Q1.accumulated/Q1.nmbrMeasurements);
		System.out.println("Total handeled: " + (Q1.totalHandeledNormal + Q1.totalHandeledSpecial));
		System.out.println("------------------------------------------------------");
		System.out.println("Total number of normal customers arrived: " + Q1.totalArrivedNormal);
		System.out.println("Total number of normal customers left: " + Q1.totalHandeledNormal);
		System.out.println("------------------------------------------------------");
		System.out.println("Total number of special customers arrived: " + Q1.totalArrivedSpecial);
		System.out.println("Total number of special customers left: " + Q1.totalHandeledSpecial);
		System.out.println("------------------------------------------------------");
		System.out.println("Average queuing time normal: " + (1.0*Q1.totalArrivedNormal - 1.0*Q1.totalHandeledNormal)/time);
		System.out.println("Average queuing time special: " + (1.0*Q1.totalArrivedSpecial - 1.0*Q1.totalHandeledSpecial)/time);

    }
}