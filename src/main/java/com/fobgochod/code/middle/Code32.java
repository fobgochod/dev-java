package com.fobgochod.code.middle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Code32 {

    // 12.23
    // qvnkodoirnqvogzowkzocxgfelvjwsesugvrcpzmczedrqhkukzuyreogzueyfxbccwhqtgvjndsisxhqegjjgbrgozclldytitzjiudqxxxbwvuvkoqoyhpuzzpvczwblvmosegthjwvlwobcmemlqbjnidlqnfbrdoqijgnjtoqldndembjxzsujqccwvjyofyzcodbkiptjmbhngslflpjczyxocqdtulbnvwgchzosccjulotscfefyrupoflitkwgdcvrwnxjikejbofgssvjcidbpcxvqtfzdxscecywpkxmcyjcprjennbgtdfoitwrdceiqqpyboutznifqkzmdbvrbmtkxikfmssqrggcqmxbjcxiwllrzwwluxcqovnerjctprqoduzgxqdoviryvuujcrjbgmvbzglbsqrohlwfxogyekfzwznendxwiwvpqjmqjzyzwjgjkeolmufcxgltbgiyllyeunvdsdrmbgrqssmmsbhqsfjtmiwfkmqhomqxfdxhcnvuftdijlxycbfkjuusonzlyntmvhhilgsypseoqbjusguoffuvpmjmieosqvntrtuvkyszrpwxrrnnpyuvutpyyblzxiocbchezguqpmfhymtmsvgrqnqedwznjinrfhsxxhtfrgwssismwziegfhectdpildqyfjfbjdzbbmjvtkwkwtrtjfbtezsbsizbcdjcgzmedssrbelwceecjfntpfbcuydmtuecjxybkeduknhzcikeqxqiriyefpyykjhvfuxkfxqxolpoiwvkriuhnfzwutpupxzkzrophysktubuvgpluylkeomubgkqbxwyddwdsnziflmqhhbdkrjnpbmvvggphwbxqkuhsmtdqgdypmvgnsmteyjuklxptjkisxcbjesgjpgihfseqhnutgbiqbfiegfnbjthesuiuwezthkmbgfuihntejclyuzlxnzzqwkkweprydbmmumlbbntvnehzvzicisrwfurxmpglxfjilweyprbfmibulgfgmbuzcorucrnewjmvqycrjfppyznswcxjiorqzxnoepwzxfnxvxbogfvkhoxqegbmtrbtdwptsmdhfhckbijoqdijqrwevmqgilsqwdsnwissckmroobkmexeqgbxbtcjgpkczxhwczgbzdukokqkniogznvhhhmnileqbffbohrlvjkgrysbrczeeffclxxjfsovybmjujeoivoxtqsbpppslpwdtulzxnnm
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String str;
        //中心扩散法
        while ((str = bf.readLine()) != null) {
            int max = 0;
            for (int i = 0; i < str.length() - 1; i++) {
                //ABA型
                int len1 = longest(str, i, i);
                //ABBA型
                int len2 = longest(str, i, i + 1);
                max = Math.max(max, Math.max(len1, len2));
            }
            System.out.println(max);
        }
    }

    private static int longest(String str, int i, int j) {
        while (j < str.length() && i >= 0 && str.charAt(i) == str.charAt(j)) {
            i--;
            j++;
        }
        return j - i - 1;
    }
}
