package ngordnet.main;

import java.util.*;

public class Graph {
    // Store the map of ID to its hyponyms ID List
    final Map<Integer, List<Integer>> adjList;

    // Store the map of word to its ID List
    final Map<String, List<Integer>> wordIdMap;

    // Give an Id, get words list
    final Map<Integer, List<String>> idWordMap;

    Graph() {
        adjList = new HashMap<>();
        wordIdMap = new HashMap<>();
        idWordMap = new HashMap<>();
    }

    /**
     * synset
     * @param id: the id number of the word list
     * @param words: the word String list
     * Add the edges between ID and Word list
     */
    public void addWordIdRelation(Integer id, String[] words) {
        for (var word : words) {
            wordIdMap.computeIfAbsent(word, (x) -> new ArrayList<>()).add(id);
            idWordMap.computeIfAbsent(id, (x) -> new ArrayList<>()).add(word);
        }
    }

    public void addHyponym(Integer id, List<Integer> idList) {
        adjList.computeIfAbsent(id, (x) -> new ArrayList<>()).addAll(idList);
    }

    public List<Integer> getIdList(String word) {
        return wordIdMap.get(word);
    }

    public List<Integer> getNeighbor(Integer id) {
        return adjList.get(id);
    }

    public List<String> getWordList(Integer id) {
        return idWordMap.get(id);
    }
}
