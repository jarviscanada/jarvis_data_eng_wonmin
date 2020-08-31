package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Fibonacci-Number-Climbing-Stairs-da541b9293694a6990683aba215e91a7
 */

public class Fibonacci {

    /**
     * Big-O: O(2^n)
     * Justification: Using the recursive tree, the number of nodes is equal to 2^n
     * Calculation of time complexity: https://medium.com/@syedtousifahmed/fibonacci-iterative-vs-recursive-5182d7783055#:~:text=Hence%20it's%20space%20complexity%20is%20O(1)%20or%20constant.&text=As%20you%20can%20see%20the,check%20out%20my%20other%20content.
     */
    public int useRecursion(int N) {
        if (N <= 1) {
            return N;
        }
        return useRecursion(N-1) + useRecursion(N-2);
    }

    /**
     * Big-O: O(n)
     * Justification: From n=2, each number is visited and stored
     */
    public int useDP(int N) {
        if (N <= 1) {
            return N;
        }
        return memoize(N);
    }
    public int memoize(int N) {
        int[] memo = new int[N+1];
        memo[1] = 1;

        for (int i = 2; i <= N; i++) {
            memo[i] = memo[i-1] + memo[i-2];
        }
        return memo[N];
    }

    /**
     * Big-O: O(2^n)
     * Justification: Using the recursive tree, the number of nodes is equal to 2^n
     * Eg. N=1 -> 1, N=2 -> 2
     * N=3 -> 1+1+1 / 1+2 / 2+1 -> 3
     * N=4 -> 1+1+1+1 / 2+2 / 1+2+1 / 1+1+2 / 2+1+1 -> 5
     */
    public int climbingStair(int N) {
        if (N == 0) {
            return 1;
        }
        if (N < 0) {
            return 0;
        }
        return climbingStair(N-1) + climbingStair(N-2);
    }
}
