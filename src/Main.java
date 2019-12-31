import model.EntryModel;
import model.TopicModel;
import model.TopicPageModel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import repository.HttpRequest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        List<TopicModel> allTopicPopularList = new ArrayList<>();
        List<TopicModel> wantTopicPopularList = new ArrayList<>();
        List<TopicPageModel> topicPageModels = new ArrayList<>();
        HttpRequest request = new HttpRequest();
        TopicPageModel pageModel;
        Scanner scanner = new Scanner(System.in);
        Document innerDocument,document;
        String input;
        int timeout = 200;
        try {
            document = request.DefaultPage();
            Elements li = document.getElementsByClass("topic-list partial").first().getElementsByTag("a");
            System.out.println("Entry Başlıkları Çekiliyor");
            for (Element ahref : li) {
               allTopicPopularList.add(new TopicModel(ahref.ownText(),ahref.attr("href")));
            }
            while (true){
                System.out.println("Aramak İstenen Anahtar Kelime ? Tüm Başlıklar için 0'a basın\n");
                input = scanner.nextLine();
                if (input.equals("0")){
                    System.out.println("Tüm Başlıklar Çekiliyor\n");
                    wantTopicPopularList = allTopicPopularList;
                    break;
                }{
                    for (TopicModel model :
                            allTopicPopularList) {
                        if (model.getTitle().contains(input)){
                            wantTopicPopularList.add(model);
                        }
                    }
                    if (wantTopicPopularList.isEmpty()){
                        System.out.println("Aranan Başlık Gündemde bulunmamaktadır\n");
                    }else{
                        System.out.println(input+" içeriğinden "+wantTopicPopularList.size()+" Kadar bulundu\n");
                        break;
                    }
                }
            }
            System.out.println("************************************"+" Entryler Çekiliyor "+"************************************\n");
            for (TopicModel topicModel:
                 wantTopicPopularList) {
                document = request.GetPage(topicModel.getUrl());
                System.out.println(topicModel.getUrl());
                String pageCountS = document.getElementsByClass("pager").tagName("select").attr("data-pagecount");
                int pageCount = 0;
                if (!pageCountS.equals("")){
                    pageCount = Integer.parseInt(pageCountS);
                }
                pageModel = new TopicPageModel();
                pageModel.setPageCount(pageCount);
                pageModel.setTopicHeader(topicModel.getTitle());
                System.out.println("************************************"+topicModel.getTitle()+" Çekiliyor ***************************** \n");
                for (int page = 1;page<=pageCount;page++){
                    System.out.println(topicModel.getUrl()+"&p="+page+"\n");
                    innerDocument = request.GetPage(topicModel.getUrl()+"&p="+page);
                    TimeUnit.MILLISECONDS.sleep(timeout);
                    Elements entrys = innerDocument.getElementById("entry-item-list").getElementsByTag("li");
                    for (Element entry:
                         entrys) {
                        Elements content = entry.getElementsByClass("content");
                        Elements info = entry.children().tagName("footer").get(1).children().get(1).children();
                        pageModel.getEntry().add(new EntryModel(info.get(1).text(),content.get(0).ownText(),info.get(0).ownText()));
                    }
                }
                topicPageModels.add(pageModel);
            }

            File file = new File("EksiTopic.txt");
            if (!file.exists()) {
                file.createNewFile();
            }else{
                file.delete();
                file.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bWriter = new BufferedWriter(fileWriter);
            for (TopicPageModel writePageModel :
                    topicPageModels) {
                bWriter.write(" \n********************************* Başlık : "+ writePageModel.getTopicHeader()+"\t  Sayfa Sayısı : "+writePageModel.getPageCount() + " **********************\n");
                for (EntryModel entryModel :
                        writePageModel.getEntry()) {
                    bWriter.write("\n"+entryModel.getEntry()+"\n");
                    bWriter.write("\n"+entryModel.getAuthor() + " \t "+entryModel.getEntryDate()+"\n");
                }
            }
            bWriter.close();


            System.out.println("bitti");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}