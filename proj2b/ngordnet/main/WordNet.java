package ngordnet.main;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    Graph graph;

    public WordNet(String synsetsFilename, String hyponymsFilename) {
        graph = new Graph();
        var in = new In(hyponymsFilename);
        while (in.hasNextLine()) {
            var line = in.readLine().split(",");
            var id = Integer.valueOf(line[0]);
            var idList = new ArrayList<Integer>();
            for (var i = 1; i < line.length; i++) {
                idList.add(Integer.valueOf(line[i]));
            }
            graph.addHyponym(id, idList);
        }

        in = new In(synsetsFilename);
        while (in.hasNextLine()) {
            var line = in.readLine().split(",");
            var id = Integer.valueOf(line[0]);
            var wordList = line[1].split("\\s+");
            graph.addWordIdRelation(id, wordList);
        }
    }

    public List<String> getHyponym(List<String> words) {
        List<List<String>> listOfSet = new ArrayList<>();
        for (var word : words) {
            listOfSet.add(getHyponym(word.trim()));
        }
        var res = new ArrayList<>(listOfSet.get(0));
        for (var list : listOfSet) {
            res.retainAll(list);
        }
        return res;
    }

    public List<String> getHyponym(String word) {
        var idList = graph.getIdList(word);
        var wordSet = new HashSet<String>();
        for (var id: idList) {
            dfs(id, wordSet);
        }

        return wordSet.stream().toList().stream().sorted().toList();
    }

    private void dfs(Integer id, HashSet<String> wordSet) {
        wordSet.addAll(graph.getWordList(id));
        var neighborList = graph.getNeighbor(id);
        if (neighborList == null) {
            return;
        }
        for (var neighbor: neighborList) {
            dfs(neighbor, wordSet);
        }
    }

    public static void main(String[] args) {
        var w = new WordNet("./data/wordnet/synsets16.txt", "./data/wordnet/hyponyms16.txt");
        System.out.println(w.graph.adjList);
        System.out.println(w.graph.wordIdMap);
        System.out.println(w.graph.wordIdMap.get("change"));;
        System.out.println(w.graph.adjList.get(8));
        System.out.println(w.graph.idWordMap.get(11));
    }
}
