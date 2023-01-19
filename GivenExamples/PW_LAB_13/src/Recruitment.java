import java.util.Random;
import java.util.concurrent.Phaser;

class Candidate extends Thread{
	
	private int id;
	private Recruitment recruitment;
	private Random random = new Random();
	
	Candidate(int id, Recruitment recruitment) {
		this.id = id;
		this.recruitment = recruitment;
	}
	
	void applyForNextStep(){
		recruitment.applyNextStep();
	}
	
	@Override
	public void run(){
		recruitment.applyNextStep();
	}
}

public class Recruitment {

	Phaser selection = new Phaser();

	void applyNextStep(){
		System.out.println(Thread.currentThread().getId() + " " + selection.getArrivedParties() + " (+) ");
		System.out.println(Thread.currentThread().getId() + " " + selection.getUnarrivedParties() + " (-) ");
		selection.arriveAndAwaitAdvance();
		System.out.println(Thread.currentThread().getId() + " " + selection.getArrivedParties() + " (+) ");
		System.out.println(Thread.currentThread().getId() + " " + selection.getUnarrivedParties() + " (-) ");
	}
	
	void startNextStep(int candidatesNumber){
		selection.register();
		selection.bulkRegister(candidatesNumber);
		selection.arriveAndAwaitAdvance();
		System.out.println(Thread.currentThread().getId() + " " + selection.getArrivedParties() + " (+) ");
		System.out.println(Thread.currentThread().getId() + " " + selection.getUnarrivedParties() + " (-) ");
	}
	
	public static void main(String[] args) {
		Recruitment recruitment = new Recruitment();
		int candidatesNumber = 5;
		Candidate [] candidates = new Candidate[candidatesNumber];
		for(int i = 0; i < candidates.length; i++){
			candidates[i] = new Candidate(i, recruitment);
			candidates[i].start();
		}
		
		recruitment.startNextStep(candidatesNumber);
	}
}
