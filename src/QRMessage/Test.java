package QRMessage;

public class Test {
	
	public Test(String s) {
		System.out.println("Running Tests for "+s);
		display("adding", encode(), 80);
		
	}
	
	private void display(String name, String result, int length) {
		String msg = name;
		while (msg.length() + result.length() < length) msg+=".";
		System.out.println(msg+result);
	}
	
	private String encode() {
		int a = 1; int b = 2;
		if (a + b == 3) return "Passed";
		else return "Failed";
	}

}
