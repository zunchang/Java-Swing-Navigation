package user;

import java.util.HashMap;

//路程导航
public class journey {
    private final String FILENAME = "路标.txt";//坐标
    public String distance;
    public int[][] journey(String name1, String name2) {
        tool T=new tool();
        HashMap<Integer,String> navigate= T.read(FILENAME);
        int[] arr= T.Coordinate(name1,name2);

        if(arr==null)
            return null;
        int [][]att=new int [2][50];
        for (int i = 0; i <arr.length; i++) {
            if(arr[i]>500){
                break;
            }
            String[]str=navigate.get(arr[i]+1).split(",");
            att[0][i]=Integer.parseInt(str[0]);
            att[1][i]=Integer.parseInt(str[1]);
        }
        distance=T.distance;
        return att;
    }
}
