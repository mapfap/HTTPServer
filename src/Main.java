public class Main {
	public static void main(String[] args) {
		new Server(5000, new Process() {

			@Override
			public String run(String input) {
				return "Thank you.";
			}
		
		});
	}
}
