public class Main {
	public static void main(String[] args) {
		new ESServer(5000, new ExpertSystem() {
			@Override
			public String getAnswer(String question) {
				return question;
			}
		});
	}
}
