import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Encryption {
	
	private static Map<String,String> encode;
	private static Map<String,String> decode;

	public static void main(String[] args) throws IOException {
		if(args.length!=3){
			System.out.println("Please provide the encryption/decryption key correctly1!");
			System.out.println("Usage: java -jar KrYpT.jar <e/d> <encryption/decryption key> <FILE TO ENCRYPT/DECRYPT>");
		}else if(args[0].length() != 1){
			System.out.println("Please provide the input <e/d> correctly2!");
			System.out.println("Usage: java -jar KrYpT.jar <e/d> <encryption/decryption key> <FILE TO ENCRYPT/DECRYPT>");
		}else if(!new File(args[2]).exists()){
			System.out.println("Please provide the the file path correctly3!");
			System.out.println("Usage: java -jar KrYpT.jar <e/d> <encryption/decryption key> <FILE TO ENCRYPT/DECRYPT>");
		}else{
			BufferedReader br = new BufferedReader(new FileReader(args[2]));
			BufferedWriter bw = null;
			if(args[0].equals("e")){
				bw = new BufferedWriter(new FileWriter(new File(args[2]).getName()+".encrypted"));
				Map<String, String> enc = getEncrption(args[1],true);
				String line = br.readLine();
				while(line != null){
					String[] letters = line.split("");
					for(String lt : letters){
						if(lt.equals(".")){
							bw.write("0000.");
						}else if(null != enc.get(lt)){
							bw.write(enc.get(lt)+".");
						}else{
							bw.write(lt+".");
						}
					}
					bw.write("\n");
					line = br.readLine();
				}
			}else if(args[0].equals("d")){
				bw = new BufferedWriter(new FileWriter(new File(args[2]).getName()+".decrypted"));
				Map<String, String> dec = getEncrption(args[1],false);
				String line = br.readLine();
				while(line != null){
					String[] letters = line.split("\\.");
					for(String lt : letters){
						if(lt.equals("0000")){
							bw.write(".");
						}else if(null != dec.get(lt)){
							bw.write(dec.get(lt));
						}else{
							bw.write(lt);
						}
					}
					bw.write("\n");
					line = br.readLine();
				}
				
			}else{
				System.out.println("Please provide the input <e/d> correctly4!");
				System.out.println("Usage: java -jar KrYpT.jar <e/d> <encryption/decryption key> <FILE TO ENCRYPT/DECRYPT>");
			}
			bw.close();
			br.close();
			System.out.println("\nIF YOU ARE NOT GETTING\n"+
							   "	THE MEANINGFUL O/P THAT MEANS\n"+
							   "		YOU ARE NOT THE INTENDED PERSON!!\n"+
							   "			TRY AGAIN USING THE 'RIGHT' KEY!!!!\n");
		}
	}

	public static Map<String, String> getEncrption(String key, boolean bool){
		key = encodeDecodePassword(key);
		encode = new HashMap<String, String>();
		decode = new HashMap<String, String>();
		String[] orig = new String[]{
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", 
				"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", 
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", 
				"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
		};
		int cnt=0;
		int i=17;
		String[] keyR = key.split("");
		for(String chr : orig){
			if(cnt < keyR.length){
				encode.put(chr, keyR[cnt]+""+i);
				decode.put(keyR[cnt]+""+i, chr);
			}else{
				cnt = 0;
				encode.put(chr, keyR[cnt]+""+i);
				decode.put(keyR[cnt]+""+i, chr);
			}
			cnt++;
			i+=12;
		}
		if(bool == true){
			return encode;
		}else{
			return decode;
		}
	}
	public static String encodeDecodePassword(String pwd){
		String[] orig = new String[]{
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", 
				"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", 
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", 
				"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
		};
		Map<String, Integer> encodeMap = new LinkedHashMap<String, Integer>();
		Map<Integer, String> decodeMap = new LinkedHashMap<Integer, String>();
		int i = 1;
		for(String letter : orig){
			encodeMap.put(letter, i);
			decodeMap.put(i++, letter);
		}

		String encodedPwd = "";
		for(i=0; i<pwd.length(); i++){
			int x = encodeMap.get(pwd.charAt(i)+"");
			x = (x * x) + 12 ;
			x = x % 52 ;
			encodedPwd+=decodeMap.get(x);
		}
		return encodedPwd;	

	}
}
