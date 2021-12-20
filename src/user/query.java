package user;

import java.util.HashMap;

import java.util.Scanner;

//用户查询
public class query {
    public String introduce(String name) {
        //介绍
        String FileName = "介绍.txt";
        tool tl = new tool();
        String str = tl.matching(name);
        String[] strings = str.split(";");
        int key = Integer.parseInt(strings[2]);
        HashMap<Integer, String> QUERY = tl.read(FileName);
        String que=QUERY.get(key);
        char[] str1=que.toCharArray();
        String str2="";
        for (int i = 0,j=0; i < str1.length; j++,i++) {
            if(j>=30){
                str2=str2+"\n";
                j=0;
            }

            str2=str2+str1[i];
        }
        return str2;


    }

    //距离
    public int distance() {


        return 0;
    }

    //时间估计
    public int time() {

        return 0;
    }

    public static void main(String[] args) {
        query Qu = new query();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String INTRODUC = Qu.introduce(str);
        System.out.println(INTRODUC);
    }
}
