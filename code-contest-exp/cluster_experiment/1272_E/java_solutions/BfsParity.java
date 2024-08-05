import java.util.*;

/**
 * Written by @applly to make the code as readable as possible.
 * Please contact me with suggestions to make it more readable.
 */
public class BfsParity {
    private static final int ZERO_DEPTH = 0;
    private static boolean isValidIndex(int[] arr, int index) {
        return index >= 0 && index < arr.length;
    }

    // Only valid indexes come
    private static boolean isSameParity(int n1, int n2, int[] arr) {
        return ((arr[n1]^arr[n2])&1) == 0;
    }


    public static int[] findMinimumMoves(int[] arr) {
        int moves[] = new int[arr.length];
        Map<Integer, List<Integer>> childToParents = new HashMap<Integer, List<Integer>>();
        for(int i=0; i<arr.length; i++) {
            moves[i] = -1;
        }

        List<Integer> leaves = new ArrayList<Integer>();
        for(int i=0; i<arr.length; i++) {
            int left = i-arr[i];
            int right = i+arr[i];

            // Find ones
            if(isValidIndex(arr, left) && !isSameParity(left, i, arr)
                    || isValidIndex(arr, right) && !isSameParity(right, i, arr)) {
                moves[i] = 1;
                leaves.add(i);
            }
            // Update parents
            if(isValidIndex(arr, left)) {
                if(childToParents.get(left) != null){
                    childToParents.get(left).add(i);
                } else {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    childToParents.put(left, list);
                }
            }
            if(isValidIndex(arr, right)) {
                if(childToParents.get(right) != null){
                    childToParents.get(right).add(i);
                } else {
                    List<Integer> list = new ArrayList<Integer>();
                    list.add(i);
                    childToParents.put(right, list);
                }
            }
        }

        Set<Integer> children = new HashSet<Integer>(leaves);
        Set<Integer> processed = new HashSet<Integer>(leaves);
        int depth = 2;
        while(!children.isEmpty()) {
            Set<Integer> newChildren = new HashSet<Integer>();
            for (Integer i : children) {
                List<Integer> parents = childToParents.get(i);
                if(parents == null){
                    continue;
                }
                for (Integer p : parents) {
                    if(!processed.contains(p)) {
                        processed.add(p);
                        newChildren.add(p);
                        moves[p]= depth;
                    }
                }
            }
            depth++;
            children = newChildren;
        }
        return moves;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        int[] arr = new int[count];
        for (int i=0; i<count; i++) {
            arr[i] = scanner.nextInt();
        }
        int[] moves = findMinimumMoves(arr);
        for(int i=0; i<count; i++){
            System.out.print(moves[i] + " ");
        }
        System.out.println("");
    }

}
