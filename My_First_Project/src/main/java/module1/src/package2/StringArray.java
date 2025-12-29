package module1.src.package2;//字符集和

import java.util.ArrayList;

public class StringArray {
    public String stringArray(String ln) {
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < ln.length(); i++) {
            if (list.contains(ln.charAt(i))) {
            } else {
                list.add(ln.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        StringArray sm = new StringArray();
        String aaa = sm.stringArray("aaabbaaa");
        System.out.println(aaa);
    }
}
