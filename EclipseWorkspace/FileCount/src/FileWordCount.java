import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileWordCount {

	public static void main(String[] args) {
		Map<String, Integer> words = new HashMap<String, Integer>();
		words = countMaxCount("//Users//hoshangbhansali//Desktop//EclipseWorkspace//Files//alice_in_wonderland.txt","//Users//hoshangbhansali//Desktop//EclipseWorkspace//Files//1-1000.txt",5);
		System.out.println("words : "+words);
	}
	
	static Map<String, Integer> countMaxCount(String wordFile, String commonFile, int count) {
		boolean DESC = false;
		Map<String, Integer> fileWords = new HashMap<String, Integer>();
		Map<String, Integer> commonWords = new HashMap<String, Integer>();
		
		try {
			Scanner commonTxtFile = new Scanner(new File(commonFile)) ;
			while(commonTxtFile.hasNext()) {
				String word = commonTxtFile.next();
				Integer wordCount = commonWords.get(word);
				if (wordCount != null) {
					wordCount++;
				} else {
					commonWords.put(word, wordCount);
				}
			}
			
			Scanner wordTxtFile = new Scanner(new File(wordFile)) ;
			while(wordTxtFile.hasNext()) {
				String word = wordTxtFile.next();
				Integer wordCount = fileWords.get(word);
				if (wordCount != null) {
					wordCount++;
				} else if (!commonWords.containsKey(word)){
					fileWords.put(word, wordCount);
				}
			}
			
			fileWords = sortByValue(fileWords, DESC, count);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileWords;
	}
	
	private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order,  int count)
    {
        List<Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().limit(count).collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

}
