package transcriptions.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Common {
	public static Map<String, String> importStringData(String fileName) throws IOException{
		BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
		String line = bufferedReader.readLine();
		Map<String, String> map = new HashMap<String, String>();
		while (line != null) {
			String key = line.substring(0, line.indexOf(':'));
			String value = line.substring(line.indexOf(':') + 1);
			String mapValue = value.length() == 0 ? null : value;
			map.put(key, mapValue);
			line = bufferedReader.readLine();
		}
		bufferedReader.close();
		return map;
	}
}
