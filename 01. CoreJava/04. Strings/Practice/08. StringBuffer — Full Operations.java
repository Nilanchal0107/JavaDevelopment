class FullOperations {
    public static void main (String A[]) {
        StringBuffer sb = new StringBuffer("Java");
        sb.append("Programming");
        System.out.println(sb);
        sb.insert(4, " ");
        System.out.println(sb);
        sb.deleteCharAt(0);
        System.out.println(sb);
        String str = sb.toString();
        System.out.println(str);
    }
}