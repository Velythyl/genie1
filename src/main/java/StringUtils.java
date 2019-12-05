public class StringUtils {
    public static String pad(String num, int length) {
        int len = num.length();

        String pad = "";
        while(pad.length() < (length - len)) pad += "0";

        return pad + num;
    }

    public static String pad(int nb, int length) {
        return pad(Integer.toString(nb), length);
    }
}
