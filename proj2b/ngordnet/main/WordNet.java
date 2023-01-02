package ngordnet.main;

import edu.princeton.cs.algs4.In;
import ngordnet.ngrams.NGramMap;

import java.util.*;

public class WordNet {
    Graph graph;
    NGramMap ngm;

    public WordNet(String synsetsFilename, String hyponymsFilename, NGramMap ngm) {
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
        this.ngm = ngm;
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

    public List<String> getHyponym(List<String> words, int k, int startYear, int endYear) {
        List<List<String>> listOfSet = new ArrayList<>();
        for (var word : words) {
            listOfSet.add(getHyponym(word.trim()));
        }
        var res = new ArrayList<>(listOfSet.get(0));
        for (var list : listOfSet) {
            res.retainAll(list);
        }

        var wordCountMap = new HashMap<String, Double>();
        var pq = new PriorityQueue<String>(Comparator.comparingDouble(wordCountMap::get));
        for (var word: res) {
            var wordCountSum = getWordCountSum(word, startYear, endYear);
            wordCountMap.put(word, wordCountSum);
            pq.add(word);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        return pq.stream().sorted().toList();
    }

    public List<String> getHyponym(String word) {
        var idList = graph.getIdList(word);
        var wordSet = new HashSet<String>();
        for (var id: idList) {
            dfs(id, wordSet);
        }

        return wordSet.parallelStream().sorted().toList();
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

    public List<String> getHyponym(String word, int k, int startYear, int endYear) {
        var idList = graph.getIdList(word);

        var wordCountSum = getWordCountSum(word, startYear, endYear);
        var wordCountMap = new HashMap<String, Double>();
        var pq = new PriorityQueue<String>(Comparator.comparingDouble(wordCountMap::get));
        pq.add(word);
        wordCountMap.put(word, wordCountSum);

        for (var id: idList) {
            dfs(id, wordCountMap, pq, k, startYear, endYear);
        }

        return pq.stream().sorted().toList();
    }


    private void dfs(Integer id, HashMap<String, Double> wordCountMap,
                     PriorityQueue<String> pq, int k, int startYear, int endYear) {
        var wordList = graph.getWordList(id);
        for (var word : wordList) {
            if (!wordCountMap.containsKey(word)) {
                var count = getWordCountSum(word, startYear, endYear);
                wordCountMap.put(word, count);
                pq.add(word);
            }
        }

        while (pq.size() > k) {
            pq.poll();
        }

        var neighborList = graph.getNeighbor(id);
        if (neighborList == null) {
            return;
        }

        for (var neighbor: neighborList) {
            dfs(neighbor, wordCountMap, pq, k, startYear, endYear);
        }
    }
    private double getWordCountSum(String word, int startYear, int endYear) {
        var ts = ngm.countHistory(word, startYear, endYear);
        return ts.data().parallelStream().mapToDouble(Double::doubleValue).sum();
    }

//    public static void main(String[] args) {
//        var w = new WordNet("./data/wordnet/synsets16.txt", "./data/wordnet/hyponyms16.txt");
//        System.out.println(w.graph.adjList);
//        System.out.println(w.graph.wordIdMap);
//        System.out.println(w.graph.wordIdMap.get("change"));;
//        System.out.println(w.graph.adjList.get(8));
//        System.out.println(w.graph.idWordMap.get(11));
//    }
}
