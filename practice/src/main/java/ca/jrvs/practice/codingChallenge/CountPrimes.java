package ca.jrvs.practice.codingChallenge;

/**
 * Ticket: https://www.notion.so/Count-Primes-7ac2e3b765ea4132b3c75c4efcbb45c9
 */

public class CountPrimes {
    /**
     * Big-O: O(n log(log(n)))
     * Justification: https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
     * Note to Self: The code is an adaptation of Sieve of Eratosthenes
     * The code is under assumption that the input is not inclusive
     * https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes
     * Credit: https://leetcode.com/problems/count-primes/discuss/452628/SIEVE-OF-ERATOSTHENES-%2B-EXPLANATION-%2B-OPTIMIZATIONS
     */
    public int countPrimes(int input) {
        //primeCount: each true element is a prime number
        boolean[] primeCount = new boolean[input];

        //For init, mark all numbers greater than 2 are marked as true
        for (int i = 2; i < input; i++) {
            primeCount[i] = true;
        }
        //Optimization: We only need to loop to sqrt(input)
        int sqrt = (int) Math.sqrt(input);

        for (int p = 2; p <= sqrt; p++) {
            //If the number is marked as a prime
            if (primeCount[p]) {
                //All multiples of p are marked as false, We can start at p*p
                // because all numbers below are terminated from the previous iteration
                for (int i = p*p; i < input; i += p) {
                    primeCount[i] = false;
                }
            }
        }
        int count = 0;
        //For each element marked true in primeCount, increment count of prime number
        for (int i = 2; i < input; i++) {
            if (primeCount[i]) {
                count++;
            }
        }
        return count;
    }
}
