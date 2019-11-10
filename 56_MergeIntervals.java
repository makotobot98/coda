/*
sort interval by its start, and then merge interval two at a time, stop mergting two when i1.end < i2.start.
time: O(nlogn), n = # of intervals
space: O(n)
*/
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2) -> i1[0] - i2[0]);
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            int[] curi = intervals[i];
            if (res.isEmpty() || res.get(res.size() - 1).get(1) < curi[0]) {
                //if no overlap: if res[len - 1].end < curInterval.start
                res.add(Arrays.asList(curi[0], curi[1]));
            } else {
                //if overlap
                List<Integer> prev = res.get(res.size() - 1);
                //update the prev.end
                prev.set(1, Math.max(prev.get(1), curi[1]));
            }
        }
        
        int[][] resArr = new int[res.size()][2];
        for (int i = 0; i < res.size(); i++) {
            resArr[i][0] = res.get(i).get(0);
            resArr[i][1] = res.get(i).get(1);
        }
        return resArr;
    }
}