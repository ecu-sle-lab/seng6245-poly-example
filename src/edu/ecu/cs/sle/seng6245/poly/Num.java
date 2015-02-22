package edu.ecu.cs.sle.seng6245.poly;

import java.util.Iterator;
import java.util.Vector;

public class Num {
	public static Iterator<Integer> allPrimes() {
		// EFFECTS: Returns a generator that will produce all primes
		// (as Integers), each exactly once, in increasing order.
		return new PrimesGen();
	}
	
	// inner class
	private static class PrimesGen implements Iterator<Integer> {
		private Vector<Integer> ps; // primes yielded
		private int p; // next candidate to try
		
		PrimesGen() {
			p = 2;
			ps = new Vector<Integer>();
		}
		
		public boolean hasNext() {
			return true;
		}
		
		public Integer next() {
			if (p == 2) {
				p = 3;
				return 2;
			}
			for (int n = p; true; n = n + 2) {
				for (int i = 0; i < ps.size(); i++) {
					int el = ps.get(i).intValue();
					if (n%el == 0) break; // not a prime
					if (el*el > n) { // have a prime
						ps.add(n);
						p = n + 2;
						return n;
					}
				}
			}
		}
	} // end PrimesGen
}
