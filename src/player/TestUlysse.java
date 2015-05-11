package player;

public class TestUlysse {

	public static void main(String[] args) {
		BoardUlysse B = new BoardUlysse();
		Player D = new Dumb();
		
		for(int i=1;i<=1000;i++){
			System.out.println(D.makeChoice(B));
		}
		
		
	}

}
