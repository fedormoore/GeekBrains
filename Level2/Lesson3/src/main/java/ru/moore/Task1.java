package ru.moore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Task1 {

    public static void main(String[] args) {
        List<String> listWord = new ArrayList<>();
        listWord = fillList(listWord);
        searchUniqueWord(listWord);
        countWord(listWord);
    }

    private static List fillList(List<String> listWord) {
        listWord.add("Создать");
        listWord.add("массив с");
        listWord.add("набором");
        listWord.add("слов");
        listWord.add("Найти");
        listWord.add("и вывести");
        listWord.add("массив с");
        listWord.add("набором");
        listWord.add("слов");
        listWord.add("список");
        listWord.add("набором");
        return listWord;
    }

    private static void searchUniqueWord(List<String> listWord) {
        Collections.sort(listWord);
        for (int i = 0; i < listWord.size(); i++) {
            if (!listWord.get(i).equals(listWord.get(nextStep(i, listWord.size()))) && !listWord.get(i).equals(listWord.get(previousStep(i, listWord.size()))))
                System.out.println(listWord.get(i));
        }
    }

    private static void countWord(List<String> listWord) {
        Collections.sort(listWord);
        int count=1;
        for (int i = 0; i < listWord.size(); i++) {
            if (!listWord.get(i).equals(listWord.get(nextStep(i, listWord.size())))){
                System.out.println(listWord.get(i)+" встречатеся " + count +" раз");
                count=1;
            }else{
                count++;
            }

        }
    }

    private static Integer nextStep(Integer i, Integer sizeArray) {
        int next;
        next = i + 1;
        if (next >= sizeArray)
            next = i;
        return next;
    }

    private static Integer previousStep(Integer i, Integer sizeArray) {
        int previous;
        previous = i - 1;
        if (i == 0)
            previous = i + 1;
        return previous;
    }

}
