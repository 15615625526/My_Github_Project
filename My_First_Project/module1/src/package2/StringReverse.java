package package2;

//字符逆序
public class StringReverse {
    public String stringReverse(String sb) {
        StringBuilder aaa = new StringBuilder(sb);
        return String.valueOf(aaa.reverse());
    }

    public static void main(String[] args) {
        StringReverse sr = new StringReverse();
        String bbb = sr.stringReverse("afsf");
        System.out.println(bbb);
    }
}
