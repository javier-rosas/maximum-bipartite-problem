//
//package com.algo;
/**
 * CS4102 Spring 2020 -- Homework 8 ******************************** Collaboration Policy: You are
 * encouraged to collaborate with up to 4 other students, but all work submitted must be your own
 * independently written solution. List the computing ids of all of your collaborators in the
 * comment at the top of your java or python file. Do not seek published or online solutions for any
 * assignments. If you use any published or online resources (which may not include solutions) when
 * completing this assignment, be sure to cite them. Do not submit a solution that you are unable to
 * explain orally to a member of the course staff. ******************************** Your Computing
 * ID: Collaborators: Sources: Introduction to Algorithms, Cormen
 **************************************/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javafx.util.Pair;

public class TilingDino {

  private static final int[] dx = {-1, 1, 0, 0};
  private static final int[] dy = {0, 0, 1, -1};

  SortedMap<Node, Set<Node>> graph;
  Map<Node, Boolean> nodeColor;
  Set<Node> leftNodeSet, rightNodeSet;
  Map<Node, Node> matches;


  public TilingDino() {
    this.graph = new TreeMap<>();
    this.nodeColor = new HashMap<>();
    this.leftNodeSet = new HashSet<>();
    this.rightNodeSet = new HashSet<>();
    this.matches = new HashMap<>();
  }

  /**
   * This is the method that should set off the computation of tiling dino.  It takes as input a
   * list lines of input as strings.  You should parse that input, find a tiling, and return a list
   * of strings representing the tiling
   *
   * @return the list of strings representing the tiling
   */
  
  public List<String> compute(List<String> fileLines) {
    buildGraph(fileLines);
    if (graph.isEmpty()) {
      return Collections.emptyList();
    }
    boolean isGraphBipartite = true;
    for (Node node : graph.keySet()) {
      if (!nodeColor.containsKey(node)) {
        isGraphBipartite &= colorNode(node, true);
      }
    }
    if (!isGraphBipartite) {
      return Collections.singletonList("impossibleGB");
    }
    Map<Node, Node> matches = maxBPM(graph);
    if (matches == null) {
      return Collections.singletonList("impossibleMT");
    }

    List<Pair<Node, Node>> matchList = new ArrayList<>();
    for (Map.Entry<Node, Node> entries : matches.entrySet()) {
      Node left = entries.getKey(), right = entries.getValue();
      if (left.compareTo(right) > 0) {
        left = entries.getValue();
        right = entries.getKey();
      }
      matchList.add(new Pair<>(left, right));
    }

    Collections.sort(matchList, (o1, o2) ->  {
        int keyCompareValue = o1.getKey().compareTo(o2.getKey());
        if (keyCompareValue == 0) {
          return o1.getValue().compareTo(o2.getValue());
        }
        return keyCompareValue;
    });

    return matchList.stream()
        .map(match -> match.getKey().toString() + " " + match.getValue().toString())
        .collect(Collectors.toList());
  }

  private boolean bpm(
      Node currentNode,
      Set<Node> exploredNodes,
      Map<Node, Node> matches
  ) {
    for (Node node : rightNodeSet) {
      if (!graph.get(currentNode).contains(node) || exploredNodes.contains(node)) {
        continue;
      }
      exploredNodes.add(node);
      if (!matches.containsKey(node) || bpm(matches.get(node), exploredNodes, matches)) {
        matches.put(node, currentNode);
        return true;
      }
    }
    return false;
  }


  private Map<Node, Node> maxBPM(Map<Node, Set<Node>> bpGraph) {
    Map<Node, Node> matchRight = new HashMap<>();
    for (Node node : leftNodeSet) {
      if (!bpm(node, new HashSet<>(), matchRight)) {
        System.out.println("Failed at " + node.toString());
        return null;
      }
    }
    return matchRight;
  }

  private SortedMap<Node, Set<Node>> buildGraph(List<String> fileLines) {
    final int rows = fileLines.size();
    final int columns = fileLines.get(0).length();

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        if (fileLines.get(row).charAt(column) != '#') {
          continue;
        }
        Node currentNode = new Node(column, row);
        graph.putIfAbsent(currentNode, new HashSet<>());

        for (int k = 0; k < 4; k++) {
          int nextRow = row + dx[k];
          int nextCol = column + dy[k];
          if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= columns) {
            continue;
          } else if (fileLines.get(nextRow).charAt(nextCol) != '#') {
            continue;
          }
          graph.get(currentNode).add(new Node(nextCol, nextRow));
        }
      }
    }
    return graph;
  }

  private boolean colorNode(Node currentNode, boolean color) {
    if (nodeColor.containsKey(currentNode) && nodeColor.get(currentNode) != color) {
      return false;
    } else if (nodeColor.containsKey(currentNode)) {
      return true;
    }
    nodeColor.put(currentNode, color);
    if (color) {
      leftNodeSet.add(currentNode);
    } else {
      rightNodeSet.add(currentNode);
    }

    boolean canColor = true;
    for (Node neighborNode : graph.get(currentNode)) {
      canColor &= colorNode(neighborNode, !color);
    }
    return canColor;
  }


  private static class Node implements Comparable<Node> {

    private final Integer column;
    private final Integer row;

    public Node(Integer first, Integer second) {
      this.column = first;
      this.row = second;
    }

    public Integer getColumn() {
      return this.column;
    }

    public Integer getRow() {
      return this.row;
    }

    @Override
    public int compareTo(Node other) {
      int compareValue = this.getColumn().compareTo(other.getColumn());
      if (compareValue == 0) {
        return this.getRow().compareTo(other.getRow());
      } else {
        return compareValue;
      }
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      Node node = (Node) o;
      return Objects.equals(column, node.column) &&
          Objects.equals(row, node.row);
    }

    @Override
    public int hashCode() {
      return Objects.hash(column, row);
    }

    @Override
    public String toString() {
      return column + " " + row;
    }
  }
}
