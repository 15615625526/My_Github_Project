package module1.src.package2;

//字符串替换
public class StringReplace {
    public String stringReplace(String A, int n, char[] arg, int m) {
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (A.charAt(i) == '%') {
                sb.append(arg[j++]);
                i++;
            } else {
                sb.append(A.charAt(i));
            }
        }
        while (j < m) {
            sb.append(arg[j++]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        StringReplace sc = new StringReplace();
        String aaa = sc.stringReplace("A%sC%sE", 7, new char[]{'B', 'D', 'F'}, 3);
        System.out.println(aaa);
    }
}