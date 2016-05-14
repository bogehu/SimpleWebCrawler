package cn.WebCrawler.bogehu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by bogehu on 16/5/14.
 */
public class SimpleWebCrawler {
    public static void main(String[] args) {
        java.util.Scanner input=new java.util.Scanner(System.in);
        System.out.println("Enter a URL:");
        String url=input.nextLine();
        crawler(url);
    }
    public static void crawler(String startingURL){
        List<String> listOfPendingURLs=new ArrayList<String>();
        List<String> listOfTraversedURLs=new ArrayList<String>();
        listOfPendingURLs.add(startingURL);
        //遍历5个Web页面后退出
        while (!listOfPendingURLs.isEmpty()&&listOfPendingURLs.size()<=5){
            String urlString=listOfPendingURLs.remove(0);
            if (!listOfTraversedURLs.contains(urlString)){
                listOfPendingURLs.add(urlString);
                System.out.println("Crawl"+urlString);
                //foreach遍历
                for (String s:getSubURLs(urlString)){
                    if (!listOfTraversedURLs.contains(s)){
                        listOfPendingURLs.add(s);
                    }
                }
            }
        }
    }
    public static ArrayList<String> getSubURLs(String urlString){
        ArrayList<String> list=new ArrayList<String>();
        try {
            java.net.URL url=new java.net.URL(urlString);
            Scanner input=new Scanner(url.openStream());
            int current=0;
            while (input.hasNext()){
                String line=input.nextLine();
                current=line.indexOf("http:",current);
                while (current>0){
                    int endIndex=line.indexOf("\"",current);
                    if (endIndex>0){
                        list.add(line.substring(current,endIndex));
                        current=line.indexOf("http:",endIndex);
                    }else {
                        current=-1;
                    }
                }
            }
        }catch (Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
        return list;
    }
}
