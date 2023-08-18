package package2;

//空格替换
public class SpaceReplace {
    public String spaceReplace(String s) {
        if (s == null) {
            return s;
        }
        return s.replaceAll(" ", "");
    }

    public static void main(String[] args) {
        SpaceReplace so = new SpaceReplace();
        String sdf = so.spaceReplace("Hello   World");
        System.out.println(sdf);
    }

}
