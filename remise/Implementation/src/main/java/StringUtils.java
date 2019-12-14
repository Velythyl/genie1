/**
 * StringUtils contains helpful string fonctions
 */
public class StringUtils {
    /**
     * Pads a string with 0s before it until it is of size length
     *
     * @param num the string to pad
     * @param length the length
     * @return the padded string
     */
    public static String pad(String num, int length) {
        int len = num.length();

        String pad = "";
        while(pad.length() < (length - len)) pad += "0";

        return pad + num;
    }

    /**
     * Pads an int with 0s before it until it is of size length
     *
     * @param num the string to pad
     * @param length the length
     * @return the padded string
     */
    public static String pad(int num, int length) {
        return pad(Integer.toString(num), length);
    }
}
