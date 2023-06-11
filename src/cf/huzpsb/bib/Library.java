package cf.huzpsb.bib;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Library {
    private static final Random random = new Random();
    public static boolean isInit = false;
    private static String[] cn;
    private static String[] en;
    private static String cache = "";
    private static long lastTime = 0;

    public static void init(File dir) {
        if (isInit)
            return;
        isInit = true;
        try {
            Scanner scanner = new Scanner(new File(dir, "cn.txt"), "UTF-8");
            cn = new String[200];
            for (int i = 0; i < 200; i++) {
                cn[i] = scanner.nextLine();
            }
            scanner = new Scanner(new File(dir, "en.txt"));
            en = new String[200];
            for (int i = 0; i < 200; i++) {
                en[i] = scanner.nextLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String gen() {
        if (System.currentTimeMillis() - lastTime < 5000)
            return cache;
        lastTime = System.currentTimeMillis();
        String[] arr = new String[20];
        int[] select = new int[200];
        int j = 0;
        for (int i = 0; i < 100; i++)
            select[i] = 0;
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(200);
            while (select[x] == 1)
                x = random.nextInt(200);
            select[x] = 1;
            arr[i] = cn[x].replace("name" + (x + 1), "name" + (j++));
        }
        for (int i = 0; i < 100; i++)
            select[i] = 0;
        for (int i = 10; i < 20; i++) {
            int x = random.nextInt(200);
            while (select[x] == 1)
                x = random.nextInt(200);
            select[x] = 1;
            arr[i] = en[x].replace("name" + (x + 1), "name" + (j++));
        }
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(20);
            int y = random.nextInt(20);
            String tmp = arr[x];
            arr[x] = arr[y].replace("name" + y, "name" + x);
            arr[y] = tmp.replace("name" + x, "name" + y);
        }
        StringBuilder sb = new StringBuilder("% Generated by huzpsb's bib generator\n\n");
        for (int i = 0; i < 20; i++) {
            sb.append(arr[i]);
            sb.append("\n");
        }
        cache = sb.toString();
        return cache;
    }
}
