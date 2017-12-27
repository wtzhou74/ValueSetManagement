package valueset.service;

public class RegTest {

	public static void main (String[] args) {
		String a = "bupropion HCl SRsr 150 mg";
		String b = "bupropion HCl cap 150 mg";
		String c = "bupropion HCl Cap, 150 mg, capsular";
		String d = " bupropion dd";
		System.out.println(d.replaceAll(" ", ""));
		System.out.println(a.split(" ")[0]);
		System.out.println(a.replaceAll("SR | ER|Cap[ ,]*", ""));
		System.out.println(b.replaceAll("SR | ER|Cap[ ,]*", ""));
		System.out.println(c.replaceAll("SR | ER|Cap[ ,]*", ""));
	}
}
