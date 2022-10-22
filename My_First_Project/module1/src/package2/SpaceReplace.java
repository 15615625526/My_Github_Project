package package2;

//空格替换
public class SpaceReplace {
    public String spaceReplace(String s) {
        if (s == null) {
            return s;
        }
        return s.replaceAll(" ", "%20");
    }

    public static void main(String[] args) {
        SpaceReplace so = new SpaceReplace();
        String sdf = so.spaceReplace(" ");
        System.out.println(sdf);
    }

}
