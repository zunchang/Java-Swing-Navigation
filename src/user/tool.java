package user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class tool extends daposit {
    private final String FILENAME1 = "名称.txt";//名称
    private final String FILENAME2 = "路标.txt";//坐标
    public String distance;
    //子串匹配，找到name
    public String matching(String sub) {
        int[] n = new int[100];//匹配度标注
        boolean priority = false;//优先度标识
        HashMap<Integer, String> name = new HashMap<Integer, String>();
        HashMap<Integer, String> namekey = new HashMap<Integer, String>();
        name = read(FILENAME1);
        //遍历
        for (int i = 1; i <= name.size(); i++) {
            String str = name.get(i);
            char[] a = str.toCharArray();
            char[] b = sub.toCharArray();
            if (a[0] == b[0])
                priority = true;
            for (int j = 0; j < sub.length(); j++) {
                for (int j1 = 0; j1 < str.length(); j1++)
                    if (a[j1] == b[j]) {
                        n[i]++;
                        break;
                    }

            }
        }
        int max = n[1];
        int key = 1;
        //最高匹配度的，且优先级高的，入选
        for (int i = 1; i < n.length; i++) {
            if (n[i] > max) {
                max = n[i];
                key = i;
            }
            if (n[i] == max) {
                if (priority == true) {
                    max = n[i];
                    key = i;
                }
            }
        }
        namekey.put(key, name.get(key));
        return name.get(key);
    }

    //按行读取文件
    public HashMap<Integer, String> read(String filename) {
        File file = new File(filename);
        //文件存在时的异常处理
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        HashMap<Integer, String> name = new HashMap<Integer, String>();
        String linestr = "";
        int n = 1;
        //将名称文件中的数据读入name中
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            //按行读取
            while ((linestr = br.readLine()) != null) {
                name.put(n++, linestr);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    //传入用户输入的起点和终点
    public int[] Coordinate(String name1, String name2) {

        HashMap<Integer, String> location = new HashMap<Integer, String>();
        //读取坐标文件
        location = read(FILENAME2);
        //获取起点坐标
        String[] KEY1 = matching(name1).split(";");
        //获取终点坐标
        String[] KEY2 = matching(name2).split(";");

        //起点
        int I = Integer.parseInt(KEY1[1].trim()) -1;
        //终点
        int J = Integer.parseInt(KEY2[1].trim()) -1 ;
        //邻接矩阵
        int all[][] = dep();
        int[][] cost = new int[103][103];
        //整体赋值
        for (int i = 0; i < 103; i++) {
            Arrays.fill(cost[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < 103; i++) {
            for (int j = 0; j < 5; j++) {
                if (all[i][j] != 0) {
                    int x = i;
                    int y = all[i][j];
                    String[] str1 = location.get(x + 1).split(",");
                    int XX1 = Integer.parseInt(str1[0].trim());
                    int YY1 = Integer.parseInt(str1[1].trim());
                    String[] str2 = location.get(y + 1).split(",");
                    int XX2 = Integer.parseInt(str2[0].trim());
                    int YY2 = Integer.parseInt(str2[1].trim());
                    int p = (int) Math.round(Math.sqrt(Math.pow(Math.abs(XX1 - XX2), 2) + Math.pow(Math.abs(YY1 - YY2), 2)));
                    cost[x][y] = p;
                }
            }
        }
        //路径获取
        int arr[] = dijkstral(cost, I, 103, J);
        return arr;
    }

    public int[] dijkstral(int[][] cost, int v, int n, int J) {
        String str1;
        int arr[] =new int [50];
        for (int i=0;i<50;i++) {
            arr[i]=1000;
        }
        int[] dist = new int[n];  //保存v到其他点的最短距离
        int[] s = new int[n];  //保存已计算过的顶点
        int[] rear = new int[n];  //rear[i]保存v到顶点i的最短路径顶点数
        int[][] q = new int[n][n];  //q[v][i]保存v到i的最短路径

        //初始化s和rear
        for (int i = 1; i < n; i++) {
            s[i] = 0;
            rear[i] = -1;
        }
        //初始化dist和q
        for (int i = 1; i < n; i++) {
            dist[i] = cost[v][i];
            if (dist[i] < Integer.MAX_VALUE) {
                q[i][++rear[i]] = v;
                q[i][++rear[i]] = i;
            }
        }
        s[v] = 1;
        int j, min, m;
        for (int i = 0, p = 0; i < n - 1; i++) {
            min = Integer.MAX_VALUE;
            m = v;
            //寻找和v相邻的最短距离的点
            for (j = 1; j < n; j++) {
                if (dist[j] < min && s[j] == 0) {
                    min = dist[j];
                    m = j;
                }
            }
            if (m != j) {

                s[m] = 1;
                if (m == J) {
                    if (v == m) {
                        System.out.println("您就在此处");
                        return null;
                    } else {
                        str1 = (v + "到" + m + "的最短距离为" + dist[m]+"米");
                        distance=str1;
                        System.out.println(str1);
                        System.out.print("最短路径为： ");
                        for (int k = 0; k <= rear[m]; k++) {
                            if (k == rear[m]) {
                                 System.out.print(q[m][k]);
                                arr[p++] = q[m][k];
                                break;
                            }
                            System.out.print(q[m][k] + "->");
                            arr[p++] = q[m][k];
                        }
                          System.out.println();
                    }
                }
                //更新dist，就是上面所说的从v2出发
                for (int k = 1; k < n; k++) {
                    if (dist[k] > (cost[m][k] + dist[m]) && s[k] == 0 && cost[m][k] != Integer.MAX_VALUE) {
                        dist[k] = cost[m][k] + dist[m];
                        for (int k1 = 0; k1 <= rear[m]; k1++) {  //更新路径
                            q[k][k1] = q[m][k1];
                        }
                        rear[k] = rear[m];
                        q[k][++rear[k]] = k;
                    }
                }
            }
        }
        return arr;
    }
/*
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name1 = scanner.nextLine();
        Scanner scanner1 = new Scanner(System.in);
        String name2 = scanner1.nextLine();
        tool too=new tool();
        int[]arr=too.Coordinate(name1,name2);
        for (int i=0;i<arr.length;i++)
            System.out.println(arr[i]);
        journey journey=new journey();
        int[][] a=journey.journey(name1,name2);
        for (int i=0;i<a[0].length;i++)
        {
            System.out.print (a[0][i]+",");
            System.out.println(a[1][i]);
        }
    }*/
}
