/**
 * public class TreeNode {
 *   public int key;
 *   public TreeNode left;
 *   public TreeNode right;
 *   public TreeNode(int key) {
 *     this.key = key;
 *   }
 * }
 */
 /*
 alg: bfs and coordinate
 bfs rule:
    initial condition: queue with root entry
    expansion/generation rule:
      expand: expand all the nodes of same y level, add to the list of y-id (use a map<Integer, List<Node>>)
      generate:
        generate children of cur node, with (cur.left, cur.x - 1, cur.y + 1) for left and (cur.right, cur.x + 1, cur.y + 1)
  since above generation pricile are from top to down, left to right, so each list in map<x-id, List<Node> of same x>> gives
  the result list with y in ascending order

  time: O(n)
  space: O(n)
 */
public class Solution {
  public List<Integer> verticalOrder(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) {
      return res;
    }
    Map<Integer, List<Integer>> map = new TreeMap<>();
    Queue<Tuple> queue = new LinkedList<>();
    queue.offer(new Tuple(0, 0, root));
    while (!queue.isEmpty()) {
      Tuple cur_p = queue.poll();
      int cur_x = cur_p.x;
      int cur_y = cur_p.y;
      List<Integer> ls = map.getOrDefault(cur_x, new ArrayList<>());
      ls.add(cur_p.node.key);
      map.put(cur_x, ls);
      if (cur_p.node.left != null) {
        queue.offer(new Tuple(cur_x - 1, cur_y + 1, cur_p.node.left));
      }
      if (cur_p.node.right != null) {
        queue.offer(new Tuple(cur_x + 1, cur_y + 1, cur_p.node.right));
      }
    }
    for (List<Integer> ls : map.values()) {
      res.addAll(ls);
    }
    return res;
  }
  public class Tuple {
    int x;
    int y;
    TreeNode node;
    public Tuple(int x, int y, TreeNode node) {
      this.x = x;
      this.y = y;
      this.node = node;
    }
  }
}
