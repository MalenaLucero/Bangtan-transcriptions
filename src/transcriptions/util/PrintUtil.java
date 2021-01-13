package transcriptions.util;

public class PrintUtil {
	public static void actionMessage(int res) {
		if(res == 1) {
			System.out.println("Se realizo la modificacion");
		} else{
			System.err.println("No se realizo la modificacion");
		}
	}
	
	public static void numberOfModifiedElements(int res) {
		if(res == 0) {
			System.err.println("No se realizaron cambios");
		} else if (res == 1) {
			System.out.println("Se modifico 1 elemento");
		} else {
			System.out.println("Se modificaron " + res + " elementos");
		}
	}
}
